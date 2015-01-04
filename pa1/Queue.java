/////////////////////////////////////////////////////////////////////////////////////////
//
//   This file constitutes the solution to the first excercise in the ADT handout.
//   Recall that all ADT operations should state their own preconditions in a comment
//   block, and then check that those conditions are satisfied.  If a precondition is
//   violated, the ADT operation should cause the program to quit with a useful error
//   message.  See functions getFront() and Dequeue() below for examples on how to do
//   this. Observe that I've added two other operations called equals() and copy()
//   respectively.  equals() compares two queues for equality and returns an appropriate
//   boolean value.  Note that equals() does not overide Object's equals() method. copy()
//   creates a new Queue object identical to it's argument Queue.
//
/////////////////////////////////////////////////////////////////////////////////////////

// Queue.java
// An integer queue ADT

class Queue{

   private class Node{
      // Fields
      int data;
      Node next;
      // Constructor
      Node(int data) { this.data = data; next = null; }
      // toString:  Overides Object's toString method.
      public String toString() { return String.valueOf(data); }
   }

   // Fields
   private Node front;
   private Node back;
   private int length;

   // Constructor
   Queue() { front = back = null; length = 0; }


   // Access Functions //////////////////////////////////////////////////////////////////

   // getFront(): returns front element in this queue
   // Pre: !this.isEmpty()
   int getFront(){
      if( this.isEmpty() ){
         throw new RuntimeException("Queue Error: getFront() called on empty Queue");
      }
      return front.data;
   }

   // getLength(): returns length of this queue
   int getLength() { return length; }

   // isEmpty(): returns true if this is an empty queue, false otherwise
   boolean isEmpty() { return length==0; }


   // Manipulation Procedures ///////////////////////////////////////////////////////////

   // Enqueue(): appends data to back of this queue
   void Enqueue(int data){
      Node node = new Node(data);
      if( this.isEmpty() ) { front = back = node; }
      else { back.next = node; back = node; }
      length++;
   }

   // Dequeue(): deletes element from front of this queue
   // Pre: !this.isEmpty()
   void Dequeue(){
      if(this.isEmpty()){
         throw new RuntimeException("Queue Error: Dequeue() called on empty Queue");
      }
      if(this.length>1) {front = front.next;}
      else {front = back = null;}
      length--;
   }


   // Other Functions ///////////////////////////////////////////////////////////////////

   // toString():  overides Object's toString() method.
   public String toString(){
      String str = "";
      for(Node N=front; N!=null; N=N.next){
         str += N.toString() + " ";
      }
      return str;
   }

   // equals(): returns true if this Queue is identical to  Q, false otherwise.
   boolean equals(Queue Q){
      boolean flag  = true;
      Node N = this.front;
      Node M = Q.front;

      if( this.length==Q.length ){
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

   // copy(): returns a new Queue identical to this one.
   Queue copy(){
      Queue Q = new Queue();
      Node N = this.front;

      while( N!=null ){
         Q.Enqueue(N.data);
         N = N.next;
      }
      return Q;
   }

}

