import java.util.Scanner;
import classes.SigFig;

public class Test {
    public static void main(String[] args) {
        // for(int i = 0; i < 10; i++) {
        //     System.out.println("Test " + (i+1) + ":");
        //     double a = Math.random() * 100;
        //     double b = Math.random() * 100;
        //     int sf1 = (int) (Math.random() * 5);
        //     int sf2 = (int) (Math.random() * 5);
        //     String x = String.format("%." + sf1 + "g", a);
        //     String y = String.format("%." + sf2 + "g", b);
        //     System.out.println("X: " + x);
        //     System.out.println("Y: " + y);
        //     System.out.println("Add:      " + SigFig.add(x,y));
        //     if(a > b) {
        //         System.out.println("Subtract: " + SigFig.subtract(x, y));
        //     } else {
        //         System.out.println("Subtract: " + SigFig.subtract(y, x));
        //     }
        //     System.out.println("Multiply: " + SigFig.multiply(x, y));
        //     System.out.println("Divide:   " + SigFig.divide(x,y));
        //     System.out.println();
        // }
        System.out.println(SigFig.subtract("0.05", "0.02"));
    }
}