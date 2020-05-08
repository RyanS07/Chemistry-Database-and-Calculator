import java.util.*;
import java.io.*;

public class ElementsHashMap {

    // reads text from csv file
    public static String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

    // converts string into double with exception handling
    public static double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) { // if the string is empty return 0, otherwise return the double value
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return 0.0;
            }
        }
        else return 0;
    }

    // converts string into int with exception handling
    public static int ParseInt(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) { // if the string is empty return 0, otherwise return the integer value
            try {
                return Integer.parseInt(strNumber);
            } catch(Exception e) {
                return 0;
            }
        }
        else return 0;
    }

    public static Map<String, Element> elements = new HashMap<>();

    public static void initializeElements() {
        try {
            // uses readFile method from specific file location
            String periodicTable = readFile("/Users/MattonXia/Desktop/contacts/src/Balkulator/src/src/classes/PeriodTableData.csv");
            String[] elems = periodicTable.split("\n");
            for(int i = 1; i < elems.length; i++) {
                String[] details = elems[i].split(","); // splits each row into their individual elements
                elements.put(details[2], new Element(
                        ParseInt(details[0]), // atomic number
                        details[1], // element
                        details[2], // symbol
                        ParseDouble(details[3]), // atomic mass
                        ParseInt(details[4]), // number of neutrons
                        ParseInt(details[5]), // number of protons
                        ParseInt(details[6]), // number of electrons
                        ParseInt(details[7]), // period
                        ParseInt(details[8]), // group
                        details[9], // phase
                        details[10], // isradioactive
                        details[11], // isnatural
                        details[12], // ismetal
                        details[13], // isnonmetal
                        details[14], // ismetalloid
                        details[15], // type
                        ParseDouble(details[16]), // atomic radius
                        ParseDouble(details[17]), // electronegativity
                        ParseDouble(details[18]), // first ionization
                        ParseDouble(details[19]), // density
                        ParseDouble(details[20]), // melting point
                        ParseDouble(details[21]), // boiling point
                        ParseInt(details[22]), // discoverer
                        details[23], // year
                        ParseInt(details[24]), // year
                        ParseDouble(details[25]), // specific heat
                        ParseInt(details[26]), // number of shells
                        ParseInt(details[27]))); // number of valence
            }
        } catch(IOException e) { // catches any input/output exception
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        initializeElements();
        while(true) {
            String str = sc.next();
            if(str.equals("stop")) {
                return;
            }
            System.out.println(elements.get(str));
        }
    }
}
