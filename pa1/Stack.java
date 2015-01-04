// Stack.java
// An integer stack ADT

class Stack{

   private class Node{
      // Fields
      private int data;
      private Node next;
      // Constructor
      Node(int data) { this.data = data; next = null; }
      // toString:  Overides Object's toString method.
      public String toString() { return String.valueOf(data); }
   }

   // Fields
   private Node top;
   private int height;

   // Constructor
   Stack() { top = null; height = 0; }


   // Access Functions //////////////////////////////////////////////////////////////////

   // getTop(): returns element on top of this stack
   // Pre: !this.isEmpty()
   int getTop(){
      if( this.isEmpty() ){
         throw new RuntimeException("Stack Error: getTop() called  on empty Stack");
      }
      return top.data;
   }

   // getHeight(): returns height of this stack
   int getHeight() { return height; }

   // isEmpty(): returns true if this is an empty stack, false otherwise
   boolean isEmpty() { return height == 0; }


   // Manipulation Procedures ///////////////////////////////////////////////////////////

   // push(): appends data to top of this stack
   // Post:  !this.isEmpty()
   void push(int data){
      Node node = new Node(data);
      if( this.isEmpty() ){
         top = node;
      }else{
         node.next = top;
         top = node;
      }
      height++;
   }

   // pop(): deletes element from top of this stack
   // Pre: !this.isEmpty()
   void pop(){
      if(this.isEmpty()){
         throw new RuntimeException("Stack Error: pop() called on empty Stack");
      }
      top = top.next;
      height--;
   }



   // Other Functions ///////////////////////////////////////////////////////////////////

   // toString():  overides Object's toString method.
   public String toString(){
      String str = "";
      for(Node N=top; N!=null; N=N.next){
         str += N.toString() + " ";
      }
      return str;
   }

   // equals(): returns true if this Stack is identical to S, false otherwise.
   boolean equals(Stack S){
      boolean flag  = true;
      Node N = this.top;
      Node M = S.top;

      if( this.height==S.height ){
         while( flag && N!=null){
            flag = (N.data==M.data);
            N = N.next;
            M = M.next;
         }
         return flag;
      }else{
         return false;
      }
   }

   // copy(): returns a new Stack identical to this one.
   Stack copy(){
      Stack S = new Stack();
      Node N = this.top;
      Node M = null;

      if( !this.isEmpty() ){
         S.top = M = new Node(N.data);
         S.height++;
         N = N.next;
         while( N!=null ){
            M = M.next = new Node(N.data);
            S.height++;
            N = N.next;
         }
      }
      return S;
   }

}

