// Brad Cardello
// bcardell
// pa3

// List ADT for Matrix.java

class List { 

   private class Node {
      Object data;
      Node prev = null;
      Node next = null;
      
      Node(Object data){ 
         this.data = data;
         prev = next = null; 
      }
      
       public String toString(){ return String.valueOf(data); }
   }
   
   // Fields
   private Node front = null;
   private Node back = null;
   private Node cursor = null;
   int cursorIndex;
   private int length;

   // Constructor
   // Creates a new empty list.
   List(){
      front = back = cursor = null;
      length = 0;
      cursorIndex = -1;
   }
   
/*******************************************************************
                        Access functions
*******************************************************************/
   
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
   Object front(){
      if (length <= 0)
         throw new RuntimeException ("List Error: front() called on empty List");
      return front.data;
   }
   
   // Returns back element in this List
   // Pre: length()>0
   Object back(){
      if (length <= 0)
         throw new RuntimeException ("List Error: back() called on empty List");
      return back.data;
   }
   
   // Returns cursor element in this list.
   // Pre: length()>0, getIndex()>=0
   Object getElement(){
      if (length <= 0)
         throw new RuntimeException ("List Error: getElement() called on empty List");
      if (getIndex() < 0)
         throw new RuntimeException ("List Error: getElement() called on undefined cursor");
      return cursor.data;
   }

   // Returns true if this List and L are the same integer
   // sequence. The cursor is ignored in both lists.
   public boolean equals (Object x){
      List L = null;
      boolean flag = true;
      
      if(x instanceof List){
         L = (List)x;
         Node N = this.front;
         Node M = L.front;
         flag = ( this.length() == L.length() );
         while( flag && N != null){
            flag = (N.data.equals(M.data));
            N = N.next;
            M = M.next;
         }
         return flag;
      }
      else{
         flag = false;
      return flag;
      }
   }

   
/*******************************************************************
                    Manipulation procedures
*******************************************************************/
   
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
         printf("else");
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
   void prepend (Object data){
      length++;
      Node N = new Node (data);
      N.next = front;
      N.prev = null;
      if (front != null) {
         this.front.prev = N;
      }
      else
         this.back = N;
      this.front = N;
      if (cursorIndex != -1) cursorIndex++;
   }
   
   // Inserts new element after back element in this List.
   void append (Object data){
      length++;
      Node temp = new Node(data);
      temp.prev = back;
      temp.next = null;
      if (back != null) {
         back.next = temp;
      }
      else{
         front = temp;
      }
      back = temp;
   }

   // Inserts new element before cursor element in this
   // List. Pre: length()>0, getIndex()>=0
   void insertBefore (Object data){
      Node N = new Node (data);
      if (length() <= 0)
         throw new RuntimeException ("List Error: insertBefore() called on empty List");
      if (getIndex() < 0)
         throw new RuntimeException ("List Error: insertBefore() called on undefined cursor");
      if (cursor == front) prepend(data);
      
      if (!(length() >0 && getIndex() >=0)) return;
      length++;
      Node temp = new Node(data);
      temp.prev = cursor.prev;
      temp.next = cursor;
      // make sure node exists before cursor
      // if the cursor is the front node
      // front must be changed accordingly
      if (cursor.prev != null) {
         cursor.prev.next = temp;
      }
      else{
         front = temp;
      }
      cursor.prev = temp;
   }
   
   // Inserts new element after cursor element in this
   // List. Pre: length()>0, getIndex()>=0
   void insertAfter (Object data){
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
      if (cursor == front){
         cursor = null;
         cursorIndex = -1;
      }
      front = front.next;
      front.prev = null;
      length--;
   }
   
   // Deletes the back element in this List. Pre: length()>0
   void deleteBack(){
      if(length() <= 0){
         throw new RuntimeException("List Error: deleteBack() called on empty List");
      }
      if (cursor == back){
         cursor = null;
         cursorIndex = -1;
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
      if ( cursor == front) deleteFront();
      else if (cursor == back) deleteBack();
      if (length() > 0 && getIndex() > 0){
         cursor.prev.next = cursor.next;
         cursor.next.prev = cursor.prev;
         length--;
         cursor = null;
         cursorIndex = -1;
      }
   }
   
   
/****************************************************************
                        Other methods
****************************************************************/
   
   // Overides Object's toString method. Creates a string
   // consisting of a space separated sequence of integers
   // with front on the left and back on the right. The
   // cursor is ignored.
   public String toString(){
      String temp = "";
      this.moveTo(0);
      while(this.getIndex() != -1){
         Object tmp = this.getElement();
         temp += " " + tmp.toString();
         this.moveNext();
      }
      temp += "\n";
      System.out.print(temp);
      return temp;
   }
}