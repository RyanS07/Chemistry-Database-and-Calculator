import java.util.*;
import java.io.*;

public class Main {

    // Reads text from csv file by using BufferedReader and passing in file location as parameter
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

    /* HashMaps for metalloids, nonmetals, metals
     * Access: O(1) --> faster than using an ArrayList
     * Insertion: O(1)
     */
    public static Map<Integer, Metalloid> metalloids = new HashMap<>();
    public static Map<Integer, Nonmetal> nonmetals = new HashMap<>();
    public static Map<Integer, Metal> metals = new HashMap<>();

    public static void initializeElements() {
        try {
            // uses readFile method from specific file location
            String periodicTable = readFile("/Users/MattonXia/Desktop/contacts/src/Balkulator/src/src/classes/PeriodTableData.csv");
            String[] elems = periodicTable.split("\n");
            for(int i = 1; i < elems.length; i++) {
                String[] details = elems[i].split(","); // splits each row into their individual elements
                if(details[12].equals("yes")) {
                    metals.put(i, new Metal(details));
                } else if(details[13].equals("yes")) {
                    nonmetals.put(i, new Nonmetal(details));
                } else if(details[14].equals("yes")) {
                    metalloids.put(i, new Metalloid(details));
                }
            }
        } catch(IOException e) { // catches any input/output exception
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        initializeElements();

    }
}
