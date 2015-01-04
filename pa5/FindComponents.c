// Brad Cardello
// bcardell
// pa5
 
/* Main Program: FindComponent.c */

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"List.h"
#include"Graph.h"

int main(int argc, char* argv[]){
   int n = 0;
   int v1, v2;
   Graph G = NULL;
   FILE *in, *out;

   // check command line for correct number of arguments
   if( argc != 3 ){
      printf("Usage: %s <input file> <output file>\n", argv[0]);
      exit(1);
   }

   // open files for reading and writing 
   in = fopen(argv[1], "r");
   out = fopen(argv[2], "w");
   if(in == NULL){
      printf("Unable to open file %s for reading\n", argv[1]);
      exit(1);
   }
   if(out == NULL){
      printf("Unable to open file %s for writing\n", argv[2]);
      exit(1);
   }
   
   fscanf(in, "%d", &n);
   G = newGraph(n);
   List S = newList();
   
   /* Stores vertices into Graph */
   while (fgetc(in) != EOF){
      fscanf(in, "%d", &v1);
      fscanf(in, "%d", &v2);
      if (v1 == 0 && v2 == 0){
         break;
      }
      addArc(G, v1, v2);
   }
   
   /* Fills List S with vertex numbers in increasing order */
   for (int i = 1; i <= n; i++){
      append(S, i);
   }
   
   /* Prints the adjacency list representation of G to the output file. */
   fprintf(out, "Adjacency list representation of G:\n");
   printGraph(out, G);
   fprintf(out, "\n");
   
   /* Runs DFS on Graph G */
   DFS(G, S);
   
   /* Runs DFS on Transpose(G), processing the vertices in the
      second call by decreasing finish times from the first call */
   Graph T = transpose(G);
   DFS(T, S);
   
   /* Finds number of Strongly Connected Components */
   int numSCC = 0;
   for (int i = 1; i <= getOrder(T); i++){
      if (getParent(T, i) == NIL){
         numSCC++;
      }
   }
   fprintf(out, "G contains %d strongly connected components:\n", numSCC);
   
   /* Allocates memory on the heap for array of lists containing
      the Strongly Connected Components. SCC[i] will contain the
      vertices for Component i (to be printed out later) */
   List *SCC = calloc(numSCC + 1, sizeof(List));
   for (int i = 1; i <= numSCC; i++){
      SCC[i] = newList();
   }
   
   /* Finds the Strongly Connected Components. Moves backwards 
      through list S, popping off the back of the list and prepending
      it to the correct Component list. Each time it finds a NIL parent,
      it moves on to the next SCC. This creates a topological sort of the
      SCCs */
   int i = 1;
   int len = length(S);
   int lastElement = front(S);
   for (int j = 1; j <= len - 1; j++){
      int x = back(S);
      deleteBack(S);       // pop back off
      prepend(SCC[i], x);  // push back onto front of current component list
      
      // if back has no parent, it's time to move onto the next SCC
      if (getParent(T, x) == NIL){
         i++;
      }
   }
   /* Takes care of last SCC */
   prepend(SCC[i], lastElement);
   
   /* Print the strong components of G to the
      output file in topologically sorted order */
   for (i = 1; i <= numSCC; i++){
      fprintf(out, "Component %d: ", i);
      printList(out, SCC[i]);
      fprintf(out, "\n");
   }
   
   /* free memory */
   freeGraph(&G);
   freeGraph(&T);
   
   for (i = 0; i <= numSCC; i++){
      freeList(&SCC[i]);
   }
   freeList(&S);
   free(SCC);
   
   /* close files */
   fclose(in);
   fclose(out);
   
   return(0);
}
