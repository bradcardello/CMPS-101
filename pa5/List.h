// Brad Cardello
// bcardell
// pa5

#ifndef _LIST_H_INCLUDE_
#define _LIST_H_INCLUDE_

#include <stdio.h>

// Exported Type --------------------------------------------------------------
typedef struct ListObj* List;

int cursorIndex;

// Constructors-Destructors ---------------------------------------------------

// Returns reference to new empty List object.
List newList(void);

// Frees all heap memory associated with List *pL, and sets *pL to NULL.
void freeList(List* pL);


// Access functions -----------------------------------------------------------

// Returns number of elements in this list.
int length(List L);

// Returns the index of the cursor element in this list, or
// returns -1 if the cursor element is undefined.
int getIndex(List L);

// Returns front element in this list
// Pre: length()>0
int front(List L);

// Returns back element in this List
// Pre: length()>0
int back(List L);

// Returns cursor element in this list.
// Pre: length()>0, getIndex()>=0
int getElement(List L);

// Returns 1 if this List and L are the same integer
// sequence. Returns 0 in any other case.  The cursor
// is ignored in both lists.
int equals(List A, List B);


// Manipulation procedures ----------------------------------------------------

// Re-sets this List to the empty state.
void clear(List L);

// If 0<=i<=length()-1, moves the cursor to the element
// at index i in list L, otherwise the cursor becomes 
// undefined.
void moveTo(List L, int i);

// If 0<getIndex()<=length()-1, moves the cursor one step toward the
// front of the list. If getIndex()==0, cursor becomes undefined.
// If getIndex()==-1, cursor remains undefined. This operation is
// equivalent to moveTo(getIndex()-1).
void movePrev(List L);

// If 0<=getIndex()<length()-1, moves the cursor one step toward the
// back of the list. If getIndex()==length()-1, cursor becomes
// undefined. If index==-1, cursor remains undefined. This
// operation is equivalent to moveTo(getIndex()+1).
void moveNext(List L);

// Inserts new element before front element in this List.
void prepend(List L, int data);

// Inserts new element after back element in this List.
void append(List L, int data);

// Inserts new element before cursor element in this
// List. Pre: length()>0, getIndex()>=0
void insertBefore(List L, int data);

// Inserts new element after cursor element in this
// List. Pre: length()>0, getIndex()>=0
void insertAfter(List L, int data);

// Deletes the front element in this List. Pre: length()>0
void deleteFront(List L);

// Deletes the back element in this List. Pre: length()>0
void deleteBack(List L);

// Deletes cursor element in this List. Cursor is undefined after this
// operation. Pre: length()>0, getIndex()>=0
void delete(List L);


// Other operations -----------------------------------------------------------

// Prints data elements in L on a single line to stdout.
void printList(FILE* out, List L);

// Returns a new list representing the same integer sequence as this
// list. The cursor in the new list is undefined, regardless of the
// state of the cursor in this List. This List is unchanged.
List copyList(List L);

#endif