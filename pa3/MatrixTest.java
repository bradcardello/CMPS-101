// Brad Cardello
// bcardell
// pa3

import java.io.*;
import java.util.Scanner;
import java.lang.System.*;

class MatrixTest{
   public static void main (String[] args){      
      Matrix A = new Matrix(2);
      for (int i = 1; i <= 2; i++){
         for (int j = 1; j <= 2; j++){
            A.changeEntry(i, j, (double)j);
         }
      }
      System.out.println("Matrix A:");
      System.out.println(A);
      
      Matrix B = new Matrix(2);
      for (int i = 1; i <= 2; i++){
         for (int j = 1; j <= 2; j++){
            B.changeEntry(i, j, (double)j*2);
         }
      }
      System.out.println("A equals B is " + A.equals(B));
      System.out.println("Matrix B:");
      System.out.println(B);
      
      Matrix C = A.add(B);
      System.out.println("C = A + B:");
      System.out.println(C);
      
      Matrix D = C.sub(B);
      System.out.println("D = C - B:");
      System.out.println(D);
      
      Matrix E = new Matrix(2);
      System.out.println("Matrix E:");
      System.out.println(E);
      
      Matrix F = E.sub(A);
      System.out.println("F: E - A:");
      System.out.println(F);
      
      Matrix G = A.sub(E);
      System.out.println("G: A - E:");
      System.out.println(G);
      
      Matrix H = A.transpose();
      System.out.println("A Transpose:");
      System.out.println(H);
      
      Matrix I = A.copy();
      System.out.println("I = A:");
      System.out.println(I);
      
      Matrix J = A.mult(B);
      System.out.println("A * B:");
      System.out.println(J);
      
      System.out.println("Number of non-zeros in A = " + A.getNNZ());
   }
}
   