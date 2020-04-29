import java.util.Scanner;
import classes.SigFig;
import java.text.DecimalFormat;

public class Test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String a = sc.nextLine();
        String b = sc.nextLine();
        System.out.println(SigFig.divide(a, b));
        System.out.println("Hello");

        // for(int i = 0; i < 5; i++) {
        //     String val = sc.nextLine();
        //     System.out.println("Parsed: " + SigFig.parse(val));
        //     System.out.println();
        // }

        // for(int i = 0; i < 5; i++) {
        //     String val = randomVal();
        //     System.out.println("Val w SF: " + val);
        //     System.out.println("Parsed: " + SigFig.parse(val));
        //     System.out.println();
        // }
        
        // test(5);
        sc.close();
    }
    public static void test(int n) {
        for(int i = 0; i < n; i++) {
            String a = randomVal();
            String b = randomVal();
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
    public static String randomVal() {
        int sf = (int) (Math.random() * 4 + 1);
        double val = (Math.random() * 99 + 1);
        String stringVal = "" + val;
        int intLength = 0;
        int decLength = 0;
        String format = "";
        if(("" + val).indexOf(".") > -1) {
            intLength = stringVal.substring(0, stringVal.indexOf(".")).length();
            decLength = sf-intLength;
        }
        if(sf < intLength) {
            format = "#";
            if(decLength > 0) {
                format += ".";
                for(int i = 0; i < sf-1; i++) {
                    format += "#";
                }
            }
            format += "E0";
        } else {
            for(int i = 0; i < intLength; i++) {
                format += "#";
            }
            if(decLength > 0) {
                format += ".";
                for(int i = 0; i < decLength; i++) {
                    format += "#";
                }
            }
        }        
        System.out.println("Val: " + val);
        System.out.println("SF: " + sf);
        System.out.println("RV Format: " + format);
        DecimalFormat df1 = new DecimalFormat(format);
        return df1.format(val);
    }
}