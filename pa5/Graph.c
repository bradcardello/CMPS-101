// Brad Cardello
// bcardell
// pa5

#include<assert.h>
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"Graph.h"

#define WHITE 0
#define BLACK 1
#define GRAY  2

/* Structs **************************************************************/

// private GraphObj type
typedef struct GraphObj{
   List *L; // L of Lists whose ith element 
            // contains the neighbors of vertex i
   
   int *color; // L of ints (or chars, or strings) whose ith 
               // element is the color (white, gray, black) of vertex i.
               // white vertices are those that are as yet undiscovered, 
               // black vertices are discovered, and the gray vertices 
               // form the frontier between discovered and undiscovered 
               // vertices
   
   int *parent; // L of ints whose ith element is the parent of vertex i
   
   int *disc; // L of ints whose ith element is the discovery time of 
              // vertex i
              
   int *fin;  // L of ints whose ith element is the finish time of 
              // vertex i
               
   int order; // number of vertices
   int size;  // number of edges
} GraphObj;

/* Constructors-Destructors *********************************************/

// Returns a Graph pointing to a newly created GraphObj 
// representing a graph having n vertices and no edges
Graph newGraph(int n){
   Graph G = malloc(sizeof(struct GraphObj));
   G->L = calloc(n + 1, sizeof(List));
   G->color = calloc(n + 1, sizeof(int));
   G->parent = calloc(n + 1, sizeof(int));
   G->disc = calloc(n + 1, sizeof(int));
   G->fin = calloc(n + 1, sizeof(int));
   for (int i = 1; i <= n; i++){
      G->L[i] = newList();
      G->color[i] = WHITE;
      G->parent[i] = NIL;
      G->disc[i] = UNDEF;
      G->fin[i] = UNDEF;
   }
   G->order = n;
   G->size = 0;
   return G;
}

// Frees all dynamic memory associated with the
// Graph *pG, then sets the handle *pG to NULL
void freeGraph(Graph* pG){
   Graph toFree = *pG;
   for (int i = 1; i <= getOrder(toFree); i++){
      freeList(&(toFree->L[i]));
   }
   free(toFree->L);
   free(toFree->color);
   free(toFree->parent);
   free(toFree->disc);
   free(toFree->fin);
   free(*pG);
   *pG = NULL;
}



/* Access functions *****************************************************/

// Returns the number of vertices in G
int getOrder(Graph G){
   assert(G != NULL && "Graph Error: getOrder() cannot be called for a \t"
                       "NULL Graph G\n");
   return G->order;
}

// Returns the number of edges in G
int getSize(Graph G){
   assert(G != NULL && "Graph Error: getSize() cannot be called for a \t"
                       "NULL Graph G\n");
   return G->size;
}

// Returns the parent of vertex u in the DFS Forest created 
// by DFS(), or NIL if DFS() has not yet been called.
// Precondition: 1 <= u <= getOrder(G)
int getParent(Graph G, int u){
   assert(G != NULL && "Graph Error: getParent() cannot be called for a \t"
                       "NULL Graph G\n");
   if (!(1 <= u && u <= getOrder(G))){
      printf("Graph Error: getParent() called on an invalid vertex number\n");
      exit(1);
   }
   return G->parent[u];
}

// Returns the discover time of vertex u as determined 
// by the call to DFS upon Graph G
// Precondition: 1 <= u <= n = getOrder(G)
int getDiscover(Graph G, int u){
   assert(G != NULL && "Graph Error: getDiscover() cannot be called for a \t"
                       "NULL Graph G\n");
   if (!(1 <= u && u <= getOrder(G))){
      printf("Graph Error: getDiscover() called on an invalid vertex number\n");
      exit(1);
   }
   return G->disc[u];
}

// Returns the finish time of vertex u as determined 
// by the call to DFS upon Graph G
// Precondition: 1 <= u <= n = getOrder(G)
int getFinish(Graph G, int u){
   assert(G != NULL && "Graph Error: getFinish() cannot be called for a \t"
                       "NULL Graph G\n");
   if (!(1 <= u && u <= getOrder(G))){
      printf("Graph Error: getFinish() called on an invalid vertex number\n");
      exit(1);
   }
   return G->fin[u];
}


/* Manipulation procedures **********************************************/

// Performs the Insertion Sort algorithm upon list with vertex x
void IS(List list, int x){
   if (length(list) == 0){
      append(list, x);
      return;
   }
   for (moveTo(list, 0); getIndex(list) != -1; moveNext(list)){
      if(x < getElement(list)){
         insertBefore(list, x);
         break;
      }
   }
   if(getIndex(list) == -1){
      append(list, x);
   }
}

// Inserts a new edge joining u to v, i.e. u is added to the
// adjacency List of v, and v to the adjacency List of u in sorted order.
// Precondition: 1 <= u <= n = getOrder(G)
void addEdge(Graph G, int u, int v){
   assert(G != NULL && "Graph Error: addEdge() cannot manipulate a\t"
                       " NULL Graph G\n");
   if (!(1 <= u && u <= getOrder(G))){
      printf("Graph Error: addEdge() called on an invalid vertex number, %d\n", u);
      exit(1);
   }
   if (!(1 <= v && v <= getOrder(G))){
      printf("Graph Error: addEdge() called on an invalid vertex number, %d\n", v);
      exit(1);
   }
   List A = G->L[u];
   List B = G->L[v];
   IS(A, v);
   IS(B, u);
   G->size++;
}

// Inserts a new directed edge from u to v, i.e. v is added to the
// adjacency List of u (but not u to the adjacency List of v)
// Precondition: 1 <= u <= n = getOrder(G)
void addArc(Graph G, int u, int v){
   assert(G != NULL && "Graph Error: addArc() cannot manipulate a NULL Graph G\n");
   if (!(1 <= u && u <= getOrder(G))){
      printf("Graph Error: addArc() called on an invalid vertex number, %d\n", u);
      exit(1);
   }
   if (!(1 <= v && v <= getOrder(G))){
      printf("Graph Error: addArc() called on an invalid vertex number, %d\n", v);
      exit(1);
   }
   List A = G->L[u];
   IS(A, v);
   G->size++;
}

// Performs the depth first search algorithm on G. The argument List S
// has two purposes in this function. First it defines the order in which 
// vertices will be processed in the main loop (5-7) of DFS. Second, when 
// DFS is complete, it will store the vertices in order of decreasing finish
// times i.e. it functions as a stack. The List S can therefore be classified
// as both an input and an output parameter to function DFS().
// Let time be a local variable in DFS(), then pass the address of time to
// Visit(), making it an input-output variable to Visit(). 
// Precondition: length(S) == getOrder(G)
void DFS(Graph G, List S){
   assert(G != NULL && "Graph Error: DFS() cannot be performed upon a NULL Graph G\n");
   if (length(S) != getOrder(G)){
      printf("Graph Error: DFS() cannot be performed if the length of List S");
      printf(" does not equal the number of vertices in Graph G\n");
      exit(1);
   }
   
   int time = 0;
   // Recursive function called and defined within DFS() that visits vertex x
   void visit(int x){
      G->color[x] = GRAY;
      G->disc[x] = (++time);
      List adj = G->L[x];
      for (moveTo(adj, 0); getIndex(adj) != -1; moveNext(adj)){
         int y = getElement(adj);
         if (G->color[y] == WHITE){
            G->parent[y] = x;
            visit(y);
         }
      }
      G->color[x] = BLACK;
      G->fin[x] = (++time);
      prepend(S, x);
   }
   
   for (moveTo(S, 0); getIndex(S) != -1; moveNext(S)){
      int x = getElement(S);
      G->color[x] = WHITE;
      G->parent[x] = NIL;
   }
   
   for (moveTo(S, 0); getIndex(S) != -1; moveNext(S)){
      int x = getElement(S);
      if (G->color[x] == WHITE){
         visit(x);
      }
   }
   
   for (int i = 0; i < getOrder(G); i++){
      deleteBack(S);
   }
}

   

/* Other operations *****************************************************/

// Returns the transpose of Graph G,
// i.e.: the direction of the edges are flipped
Graph transpose(Graph G){
   assert(G != NULL && "Graph Error: Transpose() cannot create the \t"
                       "transpose of a NULL Graph G\n");
   Graph T = newGraph(getOrder(G));
   for(int i = 1; i <= getOrder(G); i++){
      List curr = G->L[i];
      moveTo(curr, 0);
      if (length(curr) == 0){
         continue;
      }
      else{   
         while(getIndex(curr) != -1){
            addArc(T, getElement(curr), i);
            moveNext(curr);
         }
      }
   }
   return T;
}

// Returns a copy of Graph G
Graph copyGraph(Graph G){
   assert(G != NULL && "Graph Error: copyGraph() cannot create the \t"
                       "copy of a NULL Graph G\n");
   int n = G->order;
   Graph cpy = malloc(sizeof(struct GraphObj));
   cpy->L = calloc(n + 1, sizeof(List));
   cpy->color = calloc(n + 1, sizeof(int));
   cpy->parent = calloc(n + 1, sizeof(int));
   cpy->disc = calloc(n + 1, sizeof(int));
   cpy->fin = calloc(n + 1, sizeof(int));
   for (int i = 1; i <= n; i++){
      cpy->L[i] = newList();
      List L2 = G->L[i];
      // Copies entries from G to cpy for Adjacency List i
      for(moveTo(L2, 0); getIndex(L2) >= 0; moveNext(L2)){
         append(cpy->L[i], getElement(L2));
      }
      cpy->color[i] = WHITE;
      cpy->parent[i] = NIL;
      cpy->disc[i] = UNDEF;
      cpy->fin[i] = UNDEF;
   }
   cpy->order = G->order;
   cpy->size = G->size;
   return cpy;
}

// Prints the adjacency list representation 
// of G to the file pointed to by out.
void printGraph(FILE* out, Graph G){
   assert(G != NULL && "Graph Error: printGraph() cannot print a NULL Graph G\n");
   for (int i = 1; i <= getOrder(G); i++){
      List list = G->L[i];
      fprintf(out, "%d:", i);
      for (moveTo(list, 0); getIndex(list) != -1; moveNext(list)){
         fprintf(out, " %d", getElement(list));
      }
      fprintf(out, "\n");
   }
}
