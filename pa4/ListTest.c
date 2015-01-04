#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"List.h"

#define MAX_LEN 160

int main(int argc, char* argv[]){
   int i, j;
   i = j = 0;
   FILE *out;

   out = fopen(argv[1], "w");
   
   List L = newList();
   
   // Testing if delete() works when
   // cursor is at front/back(?)
   prepend(L, 1);
   moveTo(L, 0);
   delete(L);
   /* Should be empty at this point */
   
   
   // Testing if append() works
   for (i = 0; i < 5; i++){
      append(L, i);
   }
   fprintf(out, "List L: ");
   printList(out, L);
   fprintf(out, "\n");
   /********** 0 1 2 3 4 **********/
   
   
   // Testing if copyList() works
   List M = copyList(L);
   fprintf(out, "List M: ");
   printList(out, M);
   fprintf(out, "\n");
   /********** 0 1 2 3 4 **********/
   
   
   
   // Testing insertAfter(), basically an append each time
   for (j = 5; j < 10; j++){
      moveTo(M, length(M) - 1);
      insertAfter(M, j);
   }
   fprintf(out, "List M now: ");
   printList(out, M);
   fprintf(out, "\n");
   /********* 0 1 2 3 4 5 5 6 6 7 7 8 8 9 9 ***********/
   
   
   
   // Testing if L was changed at all after last test
   fprintf(out, "List L after copyList(): ");
   printList(out, L);
   fprintf(out, "\n");
   /****************** 0 1 2 3 4 *******************/
   
   
   // Testing deleteFront() and deleteBack()
   deleteFront(L);
   deleteBack(L);
   fprintf(out, "List L after deleting front and back: ");
   printList(out, L);
   fprintf(out, "\n");
   /************** 1 2 3 *****************/
   
   
   // Testing delete() on middle element
   moveTo(L, 1);
   delete(L);
   fprintf(out, "List L after deleting index 1: ");
   printList(out, L);
   fprintf(out, "\n");
   /************** 1 3 ****************/
   
   // Testing if insertBefore works in middle and back
   moveTo(L, 0);
   insertAfter(L, 69);    // dummy data to make it have a middle element
   moveTo(L, 1);
   insertBefore(L, 68);
   moveTo(L, length(L) - 1);
   insertBefore(L, 70);
   fprintf(out, "List L after inserting 68 before middle and 70 before the back node: ");
   printList(out, L);
   fprintf(out, "\n");
   
   freeList(&L);
   
   fclose(out);
   
   return(0);
}

/***************************** Correct Output ************************************
List L: 0 1 2 3 4
List M: 0 1 2 3 4
List M now: 0 1 2 3 4 5 5 6 6 7 7 8 8 9 9
List L after copyList(): 0 1 2 3 4
List L after deleting front and back: 1 2 3
List L after deleting index 1: 1 3
List L after inserting 68 before middle and 70 before the back node: 1 68 69 70 3
*********************************************************************************/