// Brad Cardello
// bcardell
// pa5

#ifndef _GRAPH_H_INCLUDE_
#define _GRAPH_H_INCLUDE_
#define UNDEF -1
#define NIL 0

#include <stdio.h>
#include "List.h"

/* Exported Type *****************************************************/
typedef struct GraphObj* Graph;


/* Constructors-Destructors ******************************************/

// Returns a Graph pointing to a newly created GraphObj 
// representing a graph having n vertices and no edges
Graph newGraph(int n);

// Frees all dynamic memory associated with the
// Graph *pG, then sets the handle *pG to NULL
void freeGraph(Graph* pG);


/* Access functions **************************************************/

// Returns the number of vertices in G
int getOrder(Graph G);

// Returns the number of edges in G
int getSize(Graph G);

// Returns the parent of vertex u in the DFS Forest created 
// by DFS(), or NIL if DFS() has not yet been called.
// Precondition: 1 <= u <= getOrder(G)
int getParent(Graph G, int u);

// Returns the discover time of vertex u as determined 
// by the call to DFS upon Graph G
// Precondition: 1 <= u <= n = getOrder(G)
int getDiscover(Graph G, int u);

// Returns the finish time of vertex u as determined 
// by the call to DFS upon Graph G
// Precondition: 1 <= u <= n = getOrder(G)
int getFinish(Graph G, int u);


/* Manipulation procedures *******************************************/

// Performs the Insertion Sort algorithm upon list with vertex x
void IS(List list, int x);

// Inserts a new edge joining u to v.
// Precondition: 1 <= u <= n = getOrder(G)
void addEdge(Graph G, int u, int v);

// Inserts a new directed edge from u to v.
// Precondition: 1 <= u <= n = getOrder(G)
void addArc(Graph G, int u, int v);

// Performs the depth first search algorithm on G.
void DFS(Graph G, List S);


/* Other Functions ***************************************************/

// Returns the transpose of Graph G
Graph transpose(Graph G);

// Returns a copy of Graph G
Graph copyGraph(Graph G);

// Prints the adjacency list representation 
// of G to the file pointed to by out.
void printGraph(FILE* out , Graph G);

#endif
