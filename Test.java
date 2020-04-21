import java.util.Scanner;
// import classes.SigFig;
import java.text.DecimalFormat;

public class Test {
    public static void main(String[] args) {
        double val = 12345;
        DecimalFormat format = new DecimalFormat("0.###E0");
        System.out.println(format.format(val));
    }
}