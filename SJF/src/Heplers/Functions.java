package Heplers;

public class Functions {


    // Read Positive Double Or Zero
    public static double readPositiveOrZero(double value) {
            try {
                if(value >= 0){
                    return value;
                } else {
                    System.out.println("Please enter a positive double or zero: ");
                    return -1;
                }
            } catch (Exception e) {
                System.out.println("Enter a positive double or zero: ");
                return -1;
            }
    }

    // Read Positive Double > 0

    public static double readPositive(double value) {
        try {
            if(value > 0){
                return value;
            } else {
                System.out.println("Please enter a positive double: ");
                return -1;
            }
        } catch (Exception e) {
            System.out.println("Enter a positive double: ");
            return -1;
        }
}
}