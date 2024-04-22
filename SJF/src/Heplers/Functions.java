package Heplers;

import java.util.Scanner;

public class Functions {

        
    static Scanner input = new Scanner(System.in);

    // Read Positive Integer Or Zero
    public static double readPositiveOrZero(){
        double n = 0.0;
        while (true) {
            try {
                n = input.nextInt();
                if(n>=0){
                    return n;
                } else {
                    System.out.println("Please enter a positive double or zero: ");
                    input.nextLine();
                }
            } catch (Exception e) {
                System.out.println("Enter a positive double or zero: ");
                input.nextLine();
            }
        }
    }

    // Read Positive Integer > 0
    public static double readPositive(){
        double n = 0.0;
        while (true) {
            try {
                n = input.nextInt();
                if(n>0){
                    return n;
                } else {
                    System.out.println("Please enter a positive double: ");
                    input.nextLine();
                }
            } catch (Exception e) {
                System.out.println("Enter a positive double: ");
                input.nextLine();
            }
        }
    }
}