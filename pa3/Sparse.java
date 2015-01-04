// Brad Cardello
// bcardell
// pa3

import java.io.*;
import java.util.Scanner;
import java.lang.System.*;

// Sparse client for Matrix (main program)

class Sparse{
   public static void main (String[] args) throws IOException{
      int n, a, b, k;
      n = a = b = 0;
      Matrix A, B;
      A = B = null;
      
      if (args.length != 2){
         System.err.println ("Usage: Sparse infile outfile");
         System.exit(1);
      }
      
      Scanner in = new Scanner (new File (args[0]));
      PrintWriter out = new PrintWriter(new FileWriter(args[1]));
      
      while (in.hasNextInt()){
         n = in.nextInt();
         a = in.nextInt();
         b = in.nextInt();      
         A = new Matrix(n);
         B = new Matrix(n);
         for (int i = 1; i <= a; i++){
            int rowA = in.nextInt();
            int colA = in.nextInt();
            double valA = in.nextDouble();
            A.changeEntry(rowA, colA, valA);
         }
         for (int j = 1; j <= b; j++){
            int rowB = in.nextInt();
            int colB = in.nextInt();
            double valB = in.nextDouble();
            B.changeEntry(rowB, colB, valB);
         }
      }

      out.println("A has " + a + " non-zero entries:\n" + A);
      out.println("B has " + b + " non-zero entries:\n" + B);
      
      out.println("(1.5)*A = \n" + A.scalarMult(1.5));
      out.println("A+B = \n" + A.add(B));
      out.println("A+A = \n" + A.add(A));
      out.println("B-A = \n" + B.sub(A));
      out.println("A-A = \n" + A.sub(A));
      out.println("Transpose(A) = \n" + A.transpose());
      out.println("A*B = \n" + A.mult(B));
      out.print("B*B = \n" + B.mult(B));
      
      in.close();
      out.close();
   }
}