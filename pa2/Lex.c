// Brad Cardello
// bcardell
// pa2

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"List.h"

#define MAX_LEN 160

int main(int argc, char* argv[]){
   int i, j, lineNo = 0;
   FILE *in1, *in2, *out;
   char line[MAX_LEN];
   char tokenlist[MAX_LEN];
   char* token;

   // check command line for correct number of arguments
   if( argc != 3 ){
      printf("Usage: %s <input file> <output file>\n", argv[0]);
      exit(1);
   }

   // open files for reading and writing 
   in1 = fopen(argv[1], "r");
   out = fopen(argv[2], "w");
   if(in1 == NULL){
      printf("Unable to open file %s for reading\n", argv[1]);
      exit(1);
   }
   if(out == NULL){
      printf("Unable to open file %s for writing\n", argv[2]);
      exit(1);
   }

   /* read each line of input file, then count and print tokens */
   while(fgets(line, MAX_LEN, in1) != NULL){
      lineNo++;
      token = strtok(line, " \n");
      tokenlist[0] = '\0';
      while( token != NULL ){
         strcat(tokenlist, "   ");
         strcat(tokenlist, token);
         strcat(tokenlist, "\n");
         token = strtok(NULL, " \n");
      }
   }
   
   /* allocates memory for a pointer to an array of char's, 
      initialized in the next for-loop */
   char** list = calloc(MAX_LEN, sizeof(char**) * lineNo);
   
   /* allocates memory for a buffer of size 160 */
   char* buf = calloc(MAX_LEN, sizeof(char*));
   int len = NULL;
   
   in2 = fopen(argv[1], "r");  // re-reads infile from the start
   
   /* stores char* into list */
   for (int t = 0; t < lineNo; t++){
      fgets(buf, MAX_LEN, in2);
      len = strlen(buf);
      if (strchr(buf,'\n') != NULL)   // check if new-line character is in buffer
         buf[len - 1] = '\0';        // replace new-line character with null plug
      list[t] = calloc (len + 1, sizeof(char*));
      strcpy(list[t], buf);
   }
   
   List L = newList();
   append(L, 0);
   /* Insertion Sort Variation */
   for (j = 1; j < lineNo; j++){
      char* temp = list[j];
      i = j - 1;
      moveTo(L, i);
      while (i > -1 && strcmp(temp, list[getElement(L)]) < 0){
         movePrev(L);   // go backwards through unsorted part of List L
         i--;           // to the left of index j
      }
      
      /* if cursor runs off front of L, set 
         front->data to j, leave cursor alone */
      if(getIndex(L) == -1){
         prepend(L, j);
      }
      
      /* else set cursor->next->data to be j,
         leave cursor alone */
      else{
         insertAfter(L,j);
      }
   }
   
   for (moveTo(L, 0); getIndex(L) <= lineNo-1; moveNext(L)){
      fprintf(out, "%s\n", list[getElement(L)]);
   }
   
   /* free memory */
   free(buf);
   for (int p = 0; p < lineNo; p++) free(list[p]);
   free(list);
   freeList(&L);
   
   /* close files */
   fclose(in1);
   fclose(in2);
   fclose(out);
   
   return(0);
}
