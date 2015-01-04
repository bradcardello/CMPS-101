   void changeEntry(int i, int j, double x){
      if(!(1 <= i && i <= this.getSize())){
         throw new RuntimeException("Matrix Error: changeEntry calls invalid row number");
      }
      if(!(1 <= j && j <= this.getSize())){
         throw new RuntimeException("Matrix Error: changeEntry calls invalid column number");
      }
      
      Entry E = new Entry(j, x);
      Entry curr = null;
      boolean zeroInsert = (x == 0.0);
      boolean jFound = false;
      
      if (L[i].length() == 0 && !zeroInsert){
         L[i].append(E);
         return;
      }
      
      for (L[i].moveTo(0); 
           L[i].getIndex() <= getSize() && L[i].getIndex() > -1;
           L[i].moveNext()){
         curr = (Entry)(L[i].getElement());
         if (curr.col > j){
            break;
         }
         else if (curr.col == j){
            jFound = true;
            break;
         }
      }
      
      if (!jFound){
         if (zeroInsert){ // x = 0, y = 0
            return;
         }
         else{           // x = 0, y != 0
            for (int b = 0; b <= getSize() - 1; b++){
               L[i].moveTo(b);
               Entry cmp1 = (Entry)(L[i].getElement());
               if (L[i].length() == 1){
                  if (j < cmp1.col){
                     L[i].prepend(E);
                     break;
                  }
                  else if (j > cmp1.col){
                     L[i].append(E);
                     break;
                  }
               }
               else{
                  b++;
                  int a = b - 1;
                  L[i].moveTo(a);
                  Entry cmp2 = (Entry)(L[i].getElement());
                  
                  while (a > -1 && cmp1.col < cmp2.col){
                     a--;
                     L[i].movePrev();
                  }
                  if (L[i].getIndex() == -1){
                     L[i].prepend(E);
                     return;
                  }
                  else{
                     L[i].append(E);
                     return;
                  }
               }
            }
         }
      }
      else{
         if (zeroInsert){ // x != 0, y = 0
            L[i].delete();
         }
         else{           // x != 0, y != 0    
            curr.val = x;
         }
      }
   }