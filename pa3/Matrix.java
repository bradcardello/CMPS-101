// Brad Cardello
// bcardell
// pa3

// Matrix client for List ADT

class Matrix { 

   private class Entry {
      int col;
      double val;
      
      Entry(int col, double val){ 
         this.col = col;
         this.val = val; 
      }
      
      public String toString(){
         return (" (" + col + ", " + val + ")");
      }
      
      public boolean equals(Object x){ 
         Entry E = (Entry)x;
         double epsilon = 1e-7;
         boolean tolerance = (Math.abs(this.val - E.val) < epsilon);
         return (this.col == E.col && tolerance);
      }
   }
   
   // Fields
   private int size;
   private List[] L;


   /* Constructor */
   // Makes a new n x n zero Matrix. pre: n>=1
   Matrix(int n){
      if (n < 1)
         throw new RuntimeException ("Matrix Error: n must be greater than or equal to 1");
      L = new List[n + 1];
      for (int i = 1; i <= n; i++)
         L[i] = new List();
      size = n;
   }


/*******************************************************************
                       Access functions 
*******************************************************************/

   // Returns n, the number of rows and columns of this Matrix
   int getSize(){
      return this.size;
   }

   // Returns the number of non-zero entries in this Matrix
   int getNNZ(){
      int NNZ = 0;
      for(int i = 1; i <= this.getSize(); i++)
         NNZ += this.L[i].length();
      return NNZ;
   }

   // overrides Object's equals() method
   public boolean equals(Object x){
      Matrix M = null;
      boolean eq = true;

      if(x instanceof Matrix){
         M = (Matrix)x;
         eq = (this.size == M.size);
         for (int i = 1; eq && i <= this.getSize() - 1; i++){
            eq = (this.L[i].equals(M.L[i]));
         }
      }
      return eq;
   }


/****************************************************************** 
                    Manipulation procedures 
******************************************************************/
   
   // sets this Matrix to the zero state 
   void makeZero(){
      for (int i = 1; i <= this.getSize(); i++){
         this.L[i].clear();
      }
   }

   // returns a new Matrix having the same entries as this Matrix 
   Matrix copy(){
      Matrix cpy = new Matrix(getSize());
      for(int i = 1; i <= this.getSize(); i++){
         L[i].moveTo(0);
         if (this.L[i].length() == 0){
            continue;
         }
         else{   
            while(L[i].getIndex() != -1){
               Entry tmp = (Entry)(L[i].getElement());
               cpy.changeEntry(i, tmp.col, tmp.val);
               L[i].moveNext();
            }
         }
      }
      return cpy;
   }

   // changes ith row, jth column of this Matrix to x
   // pre: 1<=i<=getSize(), 1<=j<=getSize()
   void changeEntry(int i, int j, double x){
      if(!(1 <= i && i <= this.getSize())){
         throw new RuntimeException("Matrix Error: changeEntry calls invalid row number");
      }
      if(!(1 <= j && j <= this.getSize())){
         throw new RuntimeException("Matrix Error: changeEntry calls invalid column number");
      }
      
      List list = L[i];
      Entry E = null;
      for (list.moveTo(0); list.getIndex() >= 0; list.moveNext()){
         E = (Entry)(list.getElement());
         if (E.col < j) continue;
         break;
      }

      // if the cursor's undefined and x isn't 0
      if (list.getIndex() == -1 && x != 0){
         list.append(new Entry(j, x));
         list.moveTo(0);
      }
      else if (E != null && E.col == j){
         // if new value is 0, get rid of current node
         if(x == 0){
            list.delete();
         }
         else{
            E.val = x;
         }
      }
      else{
         if(x != 0){
            list.insertBefore(new Entry(j, x));
         }
      }
   }

   // returns a new Matrix that is the scalar product of this Matrix with x
   Matrix scalarMult(double x){
      Matrix M = new Matrix(this.getSize());
      if (this.getNNZ() == 0 || this.getSize() == 0){
         return this;
      }
      else if (x == 0.0){
         return M;
      }
      for (int i = 1; i <= this.getSize(); i++){
         this.L[i].moveTo(0);
         if (this.L[i].length() == 0) break;
         while(this.L[i].getIndex() != -1){
            Entry curr = (Entry)(this.L[i].getElement());
            M.changeEntry(i, curr.col, x * curr.val);
            this.L[i].moveNext();
         }
      }
      return M;
   }
    
   // returns a new Matrix that is the sum of this Matrix with M
   // pre: getSize()==M.getSize()
   Matrix add(Matrix M){
      if (this.getSize() != M.getSize()){
         throw new RuntimeException("Matrix Error: add() cannot add matrices of different sizes");
      }
      
      if(M.equals(this)){
         return this.scalarMult(2);
      }
      Matrix N = new Matrix(getSize());
      
      // for loop that iterates over rows
      for(int i = 1; i <= this.getSize(); i++){
         List list1 = this.L[i];
         List list2 = M.L[i];
         List results = N.L[i];
         list1.moveTo(0);
         list2.moveTo(0);
         
         // iterates through each list
         while(list1.getIndex() >= 0 && list2.getIndex() >= 0){
            Entry e1 = (Entry)(list1.getElement());
            Entry e2 = (Entry)(list2.getElement());
            if (e1.col == e2.col){
               if(e1.val + e2.val != 0){
                  results.append(new Entry(e1.col, e1.val + e2.val));
                  list1.moveNext();
                  list2.moveNext();
               }
               else{
                  list1.moveNext();
                  list2.moveNext();
               }
            }
            else if (e1.col < e2.col){
               results.append(new Entry(e1.col, e1.val));
               list1.moveNext();
            }
            else{
               results.append(new Entry(e2.col, e2.val));
               list2.moveNext();
            }
         }

         if (list1.getIndex() == list2.getIndex()){
            continue;
         }
         List list3 = ((list1.getIndex() > -1)? list1 : list2);
         while(list3.getIndex() > -1){
            Entry e3 = (Entry)(list3.getElement());
            results.append(new Entry(e3.col, e3.val));
            list3.moveNext();
         }
      }
      return N;
   }


   // returns a new Matrix that is the difference of this Matrix with M
   // pre: getSize()==M.getSize()
   Matrix sub(Matrix M){
      if (this.getSize() != M.getSize()){
         throw new RuntimeException("Matrix Error: subtract() cannot subtract matrices of different sizes");
      }
      
      Matrix N = new Matrix(getSize());
      if(M.equals(this)){
         return N;
      }
      
      // for loop that iterates over rows
      for(int i = 1; i <= this.getSize(); i++){
         List list1 = this.L[i];
         List list2 = M.L[i];
         List results = N.L[i];
         list1.moveTo(0);
         list2.moveTo(0);
         
         // iterates through each list
         while(list1.getIndex() > -1 && list2.getIndex() > -1){
            Entry e1 = (Entry)(list1.getElement());
            Entry e2 = (Entry)(list2.getElement());
            if (e1.col == e2.col){
               if((e1.val - e2.val) != 0){
                  results.append(new Entry(e1.col, e1.val - e2.val));
                  list1.moveNext();
                  list2.moveNext();
               }
               else{
                  list1.moveNext();
                  list2.moveNext();
               }
            }
            else if (e1.col < e2.col){
               results.append(new Entry(e1.col, e1.val));
               list1.moveNext();
            }
            else{
               results.append(new Entry(e2.col, 0 - e2.val));
               list2.moveNext();
            }
         }

         if (list1.getIndex() == list2.getIndex()){
            continue;
         }
         List list3 = ((list1.getIndex() > -1)? list1 : list2);
         int sign = ((list1.getIndex() > -1)? 1 : -1);
         while(list3.getIndex() >= 0){
            Entry e3 = (Entry)(list3.getElement());
            results.append(new Entry(e3.col, sign * e3.val));
            list3.moveNext();
         }
      }
      return N;
   }
    
   // returns a new Matrix that is the transpose of this Matrix
   Matrix transpose(){
      Matrix T = new Matrix(getSize());
      for(int i = 1; i <= this.getSize(); i++){
         L[i].moveTo(0);
         if (this.L[i].length() == 0){
            continue;
         }
         else{   
            while(L[i].getIndex() != -1){
               Entry tmp = (Entry)L[i].getElement();
               T.changeEntry(tmp.col, i, tmp.val);
               L[i].moveNext();
            }
         }
      }
      return T;
   }

   // returns a new Matrix that is the product of this Matrix with M
   // pre: getSize()==M.getSize()
   Matrix mult(Matrix M){
      if (this.getSize() != M.getSize()){
         throw new RuntimeException("Matrix Error: mult() cannot multiply matrices of different sizes");
      }
      
      Matrix lhs = this;
      Matrix rhs = M;
      Matrix T = rhs.transpose();
      Matrix N = new Matrix(lhs.getSize());
      
      // if either matrix is in zero-state, return matrix in zero-state
      if (lhs.getNNZ() == 0 || rhs.getNNZ() == 0){
         return N;
      }
      
      for (int i = 1; i <= lhs.getSize(); i++){
         // if current row is empty, move on to next row
         if (lhs.L[i].length() == 0){
            continue;  
         }
         for (int j = 1; j <= T.getSize(); j++){
            // if current row of Transpose(rhs) is empty, move on to next column
            if (T.L[j].length() == 0){
               continue;
            }
            else{
               double dotProd = dot(lhs.L[i], T.L[j]);
               if (dotProd != 0.0){
                  N.L[i].append(new Entry(j, dotProd));
               }
            }
         }
      }
      return N;
   }
  
    
/*******************************************************************
                        Other functions 
*******************************************************************/

   // overrides Object's toString() method
   public String toString(){
      String temp = "";
      for(int i = 1; i <= this.getSize(); i++){
         L[i].moveTo(0);
         if (this.L[i].length() == 0){
            continue;
         }
         else{   
            temp += i + ":";
            while(L[i].getIndex() != -1){
               Entry tmp = (Entry)L[i].getElement();
               temp += tmp.toString();
               L[i].moveNext();
            }
            temp += "\n";
         }
      }
      return temp;
   }

   private static double dot(List P, List Q){
      double sum = 0.0;
      P.moveTo(0);
      Q.moveTo(0);
      while (P.getIndex() != -1 && Q.getIndex() != -1 ){
         Entry cmp1 = (Entry)(P.getElement());
         Entry cmp2 = (Entry)(Q.getElement());
         if (cmp1.col < cmp2.col){
            P.moveNext();
         }
         else if (cmp1.col == cmp2.col){
            sum += cmp1.val * cmp2.val;
            P.moveNext();
            Q.moveNext();
         }
         else{
            Q.moveNext();
         }
      }
      return sum;
   }
}
