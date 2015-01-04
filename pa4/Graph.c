// Brad Cardello
// bcardell
// pa4

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
   List *L; //array of Lists whose ith element contains the neighbors of vertex i.
   
   int *color; // array of ints (or chars, or strings) whose ith 
               // element is the color (white, gray, black) of vertex i.
               // white vertices are those that are as yet undiscovered, 
               // black vertices are discovered, and the gray vertices 
               // form the frontier between discovered and undiscovered vertices
   
   int *parent; // array of ints whose ith element is the parent of vertex i.
   
   int *dist; // array of ints whose ith element is the distance 
              // from the (most recent) source to vertex i.
               
   int order; // number of vertices
   int size;  // number of edges
   int src;    // label of the vertex most recently used as source for BFS
} GraphObj;

/* Constructors-Destructors *********************************************/

// Returns a Graph pointing to a newly created GraphObj 
// representing a graph having n vertices and no edges
Graph newGraph(int n){
   Graph G = malloc(sizeof(struct GraphObj));
   G->L = calloc(n + 1, sizeof(List));
   G->color = calloc(n + 1, sizeof(int));
   G->parent = calloc(n + 1, sizeof(int));
   G->dist = calloc(n + 1, sizeof(int));
   for (int i = 1; i < n + 1; i++){
      G->L[i] = newList();
      G->color[i] = WHITE;
      G->parent[i] = NIL;
      G->dist[i] = INF;
   }
   G->order = n;
   G->size = 0;
   G->src = NIL;
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
   free(toFree->dist);
   free(*pG);
   *pG = NULL;
}



/* Access functions *****************************************************/

// Returns the order of G
int getOrder(Graph G){
   return G->order;
}

// Returns the size of G
int getSize(Graph G){
   return G->size;
}

// Returns the source vertex most recently used in 
// function BFS(), or NIL if BFS() has not yet been called
int getSource(Graph G){
   return G->src;
}

// Returns the parent of vertex u in the Breadth-First tree
// created by BFS(), or NIL if BFS() has not yet been called.
// Precondition: 1 <= u <= getOrder(G)
int getParent(Graph G, int u){
   if (!(1 <= u && u <= getOrder(G))){
      printf("Graph Error: getParent() called on an invalid vertex number\n");
      exit(1);
   }
   return G->parent[u];
}

// Returns the distance from the most recent BFS source
// to vertex u, or INF if BFS()has not yet been called.
// Precondition: 1 <= u <= getOrder(G)
int getDist(Graph G, int u){
   if (!(1 <= u && u <= getOrder(G))){
      printf("Graph Error: getDist() called on an invalid vertex number\n");
      exit(1);
   }
   if (getSource(G) == NIL){
      return INF;
   }
   return G->dist[u];
}

// Appends to the List L the vertices of a shortest path in G from 
// source to u, or appends to L the value NIL if no such path exists.
// getPath() has the precondition getSource(G)!=NIL, so BFS() must be
// called before getPath().
// Precondition: 1 <= u <= getOrder(G)
void getPath(List L, Graph G, int u){
   int source = getSource(G);
   int p = G->parent[u];
   if (!(1 <= u && u <= getOrder(G))){
      printf("Graph Error: getPath() called on an invalid vertex number\n");
      exit(1);
   }
   if (source == NIL){
      printf("Graph Error: getPath() called on a Graph with an invalid source\n");
      exit(1);
   }
   if (u == source){
      append(L, source);
   }
   else if (p == NIL){
      append(L, NIL);
   }
   else{
      getPath(L, G, p);
      append(L, u);
   }
}



/* Manipulation procedures **********************************************/

// Deletes all edges of G, restoring 
// it to its original (no edge) state
void makeNull(Graph G){
   for (int i = 1; i <= getOrder(G); i++){
      clear(G->L[i]);
   }
   G->size = 0;
}

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
// adjacency List of v, and v to the adjacency List of u. Your
// program is required to maintain these lists in sorted order
// by increasing vertex labels
// Precondition: int arguments must lie in the range 1 to getOrder(G).
void addEdge(Graph G, int u, int v){
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
// Precondition: int arguments must lie in the range 1 to getOrder(G).
void addArc(Graph G, int u, int v){
   if (!(1 <= u && u <= getOrder(G))){
      printf("Graph Error: addArc() called on an invalid vertex number, %d\n", u);
      exit(1);
   }
   if (!(1 <= v && v <= getOrder(G))){
      printf("Graph Error: addArc() called on an invalid vertex number, %d\n", v);
      exit(1);
   }
   IS(G->L[u], v);
   G->size++;
}

// Runs the BFS algorithm on the Graph G with source s, setting the 
// color, distance, parent, and source fields of G accordingly
void BFS(Graph G, int s){
   for (int x = 1; x <= getOrder(G); x++){
      G->color[x] = WHITE;
      G->dist[x] = INF;
      G->parent[x] = NIL;
   }
   G->src = s;
   G->color[s] = GRAY;
   G->dist[s] = 0;
   G->parent[s] = NIL;
   List fifo = newList();
   append(fifo, s);
   while (length(fifo) > 0){
      int x = front(fifo);
      deleteFront(fifo);
      List adj = G->L[x];
      for (moveTo(adj, 0); getIndex(adj) != -1; moveNext(adj)){
         int y = getElement(adj);
         if (G->color[y] == WHITE){
            G->color[y] = GRAY;
            G->dist[y] = G->dist[x] + 1;
            G->parent[y] = x;
            append(fifo, y);
         }
      }
      G->color[x] = BLACK;
   }
   freeList(&fifo);
}


/* Other operations *****************************************************/

// Prints the adjacency list representation of G to the file pointed
// to by out. The format of this representation should match the examples
// in the assignment description, so all that is required by the client is
// a single call to printGraph()
void printGraph(FILE* out, Graph G){
   for (int i = 1; i <= getOrder(G); i++){
      List list = G->L[i];
      fprintf(out, "%d:", i);
      for (moveTo(list, 0); getIndex(list) != -1; moveNext(list)){
         fprintf(out, " %d", getElement(list));
      }
      fprintf(out, "\n");
   }
}
