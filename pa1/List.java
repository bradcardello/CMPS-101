// Brad Cardello
// bcardell
// pa1

class List { 

   private class Node {
      int data;
      Node prev = null;
      Node next = null;
      
      Node(int data){ 
         this.data = data;
         prev = next = null; 
      }
      
       public String toString(){ return String.valueOf(data); }
   }
   
   // Fields
   private Node front;
   private Node back;
   private Node cursor;
   int cursorIndex;
   private int length;

   // Constructor
   // Creates a new empty list.
   List(){
      front = back = cursor = null;
      length = 0;
      cursorIndex = -1;
   }
   
   /***********************************************************
   // Access functions
   ***********************************************************/
   
   // Returns number of elements in this list.
   int length(){
      return length;
   }
   
   // Returns the index of the cursor element in this list, or
   // returns -1 if the cursor element is undefined.
   int getIndex(){
      int i = 0;
      if (cursor == null) return -1;
      else {
         for (Node N = front; N != null; N = N.next){
            if (N == cursor) return i;
            i++;
         }
      }
      cursorIndex = i;
      return cursorIndex;
   }
   
   // Returns front element in this list
   // Pre: length()>0
   int front(){
      if (length <= 0)
         throw new RuntimeException ("List Error: front() called on empty List");
      return front.data;
   }
   
   // Returns back element in this List
   // Pre: length()>0
   int back(){
      if (length <= 0)
         throw new RuntimeException ("List Error: back() called on empty List");
      return back.data;
   }
   
   // Returns cursor element in this list.
   // Pre: length()>0, getIndex()>=0
   int getElement(){
      if (length <= 0)
         throw new RuntimeException ("List Error: getElement() called on empty List");
      if (getIndex() < 0)
         throw new RuntimeException ("List Error: getElement() called on undefined cursor");
      return cursor.data;
   }

   // Returns true if this List and L are the same integer
   // sequence. The cursor is ignored in both lists.
   boolean equals (List L){
      boolean flag  = true;
      Node N = this.front;
      Node M = L.front;

      if( this.length == L.length ){
         while( flag && N != null){
            flag = (N.data == M.data);
            N = N.next;
            M = M.next;
         }
         return flag;
      }else{
         return false;
      }
   }

   
   /***********************************************************
   // Manipulation procedures
   ***********************************************************/
   
   // Re-sets this List to the empty state.
   void clear(){
      front = back = cursor = null;
      length = 0;
      cursorIndex = -1;
   }
   
   // If 0<=i<=length()-1, moves the cursor to the element
   // at index i, otherwise the cursor becomes undefined.
   void moveTo (int i){
      cursorIndex = 0;
      if (i >=0 && i <= this.length() - 1){
         for (Node N = front; cursorIndex <= i; N = N.next){
            if (cursorIndex == i){
               cursor = N;
               break;
            }
            else cursorIndex++;
         }
      }
      else{
         cursor = null;
         cursorIndex = -1;
      }
   }
   
   // If 0<getIndex()<=length()-1, moves the cursor one step toward the
   // front of the list. If getIndex()==0, cursor becomes undefined.
   // If getIndex()==-1, cursor remains undefined. This operation is
   // equivalent to moveTo(getIndex()-1).
   void movePrev(){
      if (0 < getIndex() && getIndex() <= length() - 1){
         moveTo(getIndex() - 1);
      }
      else if (getIndex() == 0){
         cursorIndex = -1;
         cursor = null;
      }
   }
   
   // If 0<=getIndex()<length()-1, moves the cursor one step toward the
   // back of the list. If getIndex()==length()-1, cursor becomes
   // undefined. If index==-1, cursor remains undefined. This
   // operation is equivalent to moveTo(getIndex()+1).
   void moveNext(){
      if (0 <= getIndex() && getIndex() < length() - 1){
         moveTo(getIndex() + 1);
      }
      else if (getIndex() == length() - 1){
         cursorIndex = -1;
         cursor = null;
      }
      else if (cursorIndex == -1){
         cursorIndex = -1;
         cursor = null;
      }
   }

   // Inserts new element before front element in this List.
   void prepend (int data){
      Node N = new Node (data);
      if (this.length() <= 0) {
         this.front = N;
         this.back = N;
         length++;
      }
      else{
         N.next = front;
         this.front.prev = N;
         N.prev = null;
         front = N;
         length++;
      }
      if (cursorIndex != -1) cursorIndex++;
   }
   
   // Inserts new element after back element in this List.
   void append (int data){
      Node N = new Node (data);
      if (this.length() <= 0) {
         this.front = N;
         this.back = N;
         length++;
      }
      else{
         this.back.next = N;
         N.prev = this.back;
         this.back = N;
         length++;
      }
   }

   // Inserts new element before cursor element in this
   // List. Pre: length()>0, getIndex()>=0
   void insertBefore (int data){
      Node N = new Node (data);
      if (length() <= 0)
         throw new RuntimeException ("List Error: insertBefore() called on empty List");
      if (getIndex() < 0)
         throw new RuntimeException ("List Error: insertBefore() called on undefined cursor");
      if (cursor == front) prepend(data);
      
      N.prev = cursor.prev;
      N.next = cursor;
      if (cursor.next != null)
         cursor.prev.next = N;
      cursor.prev = N;
      length++;
   }
   
   // Inserts new element after cursor element in this
   // List. Pre: length()>0, getIndex()>=0
   void insertAfter (int data){
      if (this.length() <= 0){
         throw new RuntimeException ("List Error: insertAfter() called on empty List");
      }
      if (this.getIndex() < 0){
         throw new RuntimeException ("List Error: insertAfter() called on undefined cursor");
      }
      if (cursor == back) append(data);
      
      Node N = new Node (data);
      N.prev = cursor;
      N.next = cursor.next;
      if (cursor.next != null)
         cursor.next.prev = N;
      cursor.next = N;
      length++;
   }
   
   // Deletes the front element in this List. Pre: length()>0
   void deleteFront(){
      if(this.length() <= 0){
         throw new RuntimeException("List Error: deleteFront() called on empty List");
      }
      if(this.length>1) {this.front = this.front.next;}
      else {this.front = this.back = null;}
      length--;
   }
   
   // Deletes the back element in this List. Pre: length()>0
   void deleteBack(){
      if(length() <= 0){
         throw new RuntimeException("List Error: deleteBack() called on empty List");
      }
      back = back.prev;
      back.next = null;
      length--;
   }
   
   // Deletes cursor element in this List. Cursor is undefined after this
   // operation. Pre: length()>0, getIndex()>=0
   void delete(){
      if (length() <= 0)
         throw new RuntimeException("List Error: delete() called on empty List");
      if (getIndex() < 0){
         throw new RuntimeException("List Error: delete() called on undefined cursor");
      }
      else{
         cursor.prev.next = cursor.next;
         cursor.next.prev = cursor.prev;
         length--;
      }
      cursor = null;
      cursorIndex = -1;
   }
   
   
   /***********************************************************
   // Other methods
   ***********************************************************/
   
   // Overides Object's toString method. Creates a string
   // consisting of a space separated sequence of integers
   // with front on the left and back on the right. The
   // cursor is ignored.
   public String toString(){
      String str = "";
      for (Node N = front; N != null; N = N.next){
         str += N.toString() + " ";
      }
      return str;
   }
   
   // Returns a new list representing the same integer sequence as this
   // list. The cursor in the new list is undefined, regardless of the
   // state of the cursor in this List. This List is unchanged.
   List copy(){
      List newList = new List();
      newList = this;
      newList.cursor = null;
      newList.cursorIndex = -1;
      return newList;
   }
   
   // Returns a new List which is the concatenation of
   // this list followed by L. The cursor in the new list
   // is undefined, regardless of the states of the cursors
   // in this list and L. The states of this list and L are
   // unchanged.
   List concat(List L){
      List newList = this.copy();
      newList.back.next = L.front;
      newList.back.next.prev = this.back;
      newList.back = L.back;
      return newList;
   }
}

