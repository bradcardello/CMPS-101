// Brad Cardello
// bcardell
// pa1

import java.io.*;
import java.util.Scanner;
import java.lang.System.*;

class Lex {

   public static void main (String[] args) throws IOException {

      String[] token = null;
      int i, j;
      int n = 0;
      
      if (args.length != 2){
         System.err.println ("Usage: Lex infile outfile");
         System.exit(1);
      }
      
      String line1 = null;
      Scanner in1 = new Scanner (new File (args[0]));
      PrintWriter out = new PrintWriter(new FileWriter(args[1]));
      
      // counts number of lines
      while (in1.hasNextLine()){
         n++;
         line1 = in1.nextLine()+" ";    // add extra space so split works right
      }
      token = new String[n];
      
      Scanner in2 = new Scanner (new File (args[0]));
      
      int k = 0;
      // puts tokens into token array of size n
      while (in2.hasNextLine()){ 
         token[k] = in2.nextLine();
         k++;
      }
      
      /*****************************************************************
      // Create a List whose elements are the indices of the above 
      // String array. These indices should be arranged in an order
      // that effectively sorts the array.
      *****************************************************************/
      
      List L = new List();
      if (L.length() <= 0){
         L.append(0);
      }
      for (j = 1; j <= n-1; j++){
         String temp = token[j];
         i = j - 1;
         L.moveTo(i);
         while (i > -1 && temp.compareTo(token[L.getElement()]) < 0){
            L.movePrev();
            i--;
         }
         if(L.getIndex() == -1){
            L.prepend(j);
         }
         else{
            L.insertAfter(j);
         }
      }
      
      for (L.moveTo(0); L.getIndex() <= n-1; L.moveNext()){
         out.println(token[L.getElement()]);
      }
      
      in1.close();
      in2.close();
      out.close();
   }
}
