import java.util.Scanner;
import classes.SigFig;
import java.text.DecimalFormat;

public class Test {
    public static void main(String[] args) {
        // System.out.println(SigFig.subtract("37.21", "30.64"));
        test(5);
    }
    public static void test(int n) {
        for(int i = 0; i < n; i++) {
            int sf1 = (int) (Math.random() * 2 + 3);
            int sf2 = (int) (Math.random() * 2 + 3);
            String a = String.format("%." + sf1 + "g", (Math.random() * 99 + 1));
            String b = String.format("%." + sf2 + "g", (Math.random() * 99 + 1));
            System.out.println("A: " + a);
            System.out.println("B: " + b);
            System.out.println("Sum: " + SigFig.add(a, b));
            if(Double.parseDouble(a) > Double.parseDouble(b)) {
                System.out.println("Difference: " + SigFig.subtract(a, b));
            } else {
                System.out.println("Difference: " + SigFig.subtract(b, a));
            }
            System.out.println("Product: " + SigFig.multiply(a, b));
            System.out.println("Quotient: " + SigFig.divide(a, b));
            System.out.println();
        }
    }
}