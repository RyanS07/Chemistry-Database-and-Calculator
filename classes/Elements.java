package classes;

import java.io.*;

import java.util.*;

public class Elements {

    static class Element {

        // instance variables made private to not be easily accessible and changeable
        private int atomicNumber;
        private String element;
        private String symbol;
        private double atomicMass;
        private int numberOfNeutrons;
        private int numberOfProtons;
        private int numberOfElectrons;
        private int period;
        private int group;
        private String phase;
        private boolean isRadioactive;
        private boolean isNatural;
        private boolean isMetal;
        private boolean isNonmetal;
        private boolean isMetalloid;
        private String type;
        private double atomicRadius;
        private double electronegativity;
        private double firstIonization;
        private double density;
        private double meltingPoint;
        private double boilingPoint;
        private int numberOfIsotopes;
        private String discoverer;
        private int year;
        private double specificHeat;
        private int numberOfShells;
        private int numberOfValence;

        // getter for element class for all instance variables made public to be allowed to be accessible by other classes

        public int getAtomicNumber() {
            return this.atomicNumber;
        }

        public String getElement() {
            return this.element;
        }

        public String getSymbol() {
            return this.symbol;
        }

        public double getAtomicMass() {
            return this.atomicMass;
        }

        public int getNumberOfNeutrons() {
            return this.numberOfNeutrons;
        }

        public int getNumberOfProtons() {
            return this.numberOfProtons;
        }

        public int getNumberOfElectrons() {
            return this.numberOfElectrons;
        }

        public int getPeriod() {
            return this.period;
        }

        public int getGroup() {
            return this.group;
        }

        public String getPhase() {
            return this.phase;
        }

        public boolean getIsRadioactive() {
            return isRadioactive;
        }

        public boolean getIsNatural() {
            return this.isNatural;
        }

        public boolean getIsMetal() {
            return this.isMetal;
        }

        public boolean getIsNonmetal() {
            return this.isNonmetal;
        }

        public boolean getIsMetalloid() {
            return this.isMetalloid;
        }

        public String getType() {
            return this.type;
        }

        public double getAtomicRadius() {
            return this.atomicRadius;
        }

        public double getElectronegativity() {
            return this.electronegativity;
        }

        public double getFirstIonization() {
            return this.firstIonization;
        }

        public double getDensity() {
            return this.density;
        }

        public double getMeltingPoint() {
            return this.meltingPoint;
        }

        public double getBoilingPoint() {
            return this.boilingPoint;
        }

        public int getNumberOfIsotopes() {
            return this.numberOfIsotopes;
        }

        public String getDiscoverer() {
            return this.discoverer;
        }

        public int getYear() {
            return this.year;
        }

        public double getSpecificHeat() {
            return this.specificHeat;
        }

        public int getNumberOfShells() {
            return this.numberOfShells;
        }

        public int getNumberOfValence() {
            return this.numberOfValence;
        }

        // setters for all instance variables accessible by other classes

        public void setAtomicNumber(int atomicNumber) {
            this.atomicNumber = atomicNumber;
        }

        public void setElement(String element) {
            this.element = element;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public void setAtomicMass(double atomicMass) {
            this.atomicMass = atomicMass;
        }

        public void setNumberOfNeutrons(int numberOfNeutrons) {
            this.numberOfNeutrons = numberOfNeutrons;
        }

        public void setNumberOfProtons(int numberOfProtons) {
            this.numberOfProtons = numberOfProtons;
        }

        public void setNumberOfElectrons(int numberOfElectrons) {
            this.numberOfElectrons = numberOfElectrons;
        }

        public void setPeriod(int period) {
            this.period = period;
        }

        public void setGroup(int group) {
            this.group = group;
        }

        public void setPhase(String phase) {
            this.phase = phase;
        }

        public void setIsRadioactive(String bool) {
            this.isRadioactive = bool.equals("yes");
        }

        public void setIsNatural(String bool) {
            this.isNatural = bool.equals("yes");
        }

        public void setIsMetal(String bool) {
            this.isMetal = bool.equals("yes");
        }

        public void setIsNonmetal(String bool) {
            this.isNonmetal = bool.equals("yes");
        }

        public void setIsMetalloid(String bool) {
            this.isMetalloid = bool.equals("yes");
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setAtomicRadius(double atomicRadius) {
            this.atomicRadius = atomicRadius;
        }

        public void setElectronegativity(double electronegativity) {
            this.electronegativity = electronegativity;
        }

        public void setFirstIonization(double firstIonization) {
            this.firstIonization = firstIonization;
        }

        public void setDensity(double density) {
            this.density = density;
        }

        public void setMeltingPoint(double meltingPoint) {
            this.meltingPoint = meltingPoint;
        }

        public void setBoilingPoint(double boilingPoint) {
            this.boilingPoint = boilingPoint;
        }

        public void setNumberOfIsotopes(int numberOfIsotopes) {
            this.numberOfIsotopes = numberOfIsotopes;
        }

        public void setDiscoverer(String discoverer) {
            this.discoverer = discoverer;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public void setSpecificHeat(double specificHeat) {
            this.specificHeat = specificHeat;
        }

        public void setNumberOfShells(int numberOfShells) {
            this.numberOfShells = numberOfShells;
        }

        public void setNumberOfValence(int numberOfValence) {
            this.numberOfValence = numberOfValence;
        }

        // default constructor for Element class

        public Element() {

            this.atomicNumber = 0;
            this.element = "";
            this.symbol = "";
            this.atomicMass = 0;
            this.numberOfNeutrons = 0;
            this.numberOfProtons = 0;
            this.numberOfElectrons = 0;
            this.period = 0;
            this.group = 0;
            this.phase = "";
            this.isRadioactive = false;
            this.isNatural = false;
            this.isMetal = false;
            this.isNonmetal = false;
            this.isMetalloid = false;
            this.type = "";
            this.atomicRadius = 0;
            this.electronegativity = 0;
            this.firstIonization = 0;
            this.density = 0;
            this.meltingPoint = 0;
            this.boilingPoint = 0;
            this.numberOfIsotopes = 0;
            this.discoverer = "";
            this.year = 0;
            this.specificHeat = 0;
            this.numberOfShells = 0;
            this.numberOfValence = 0;
        }

        // non-empty constructor for Element class
        public Element(int atomicNumber, String element, String symbol, double atomicMass, int numberOfNeutrons, int numberOfProtons, int numberOfElectrons, int period, int group, String phase, String isRadioactive, String isNatural, String isMetal, String isNonmetal, String isMetalloid, String type, double atomicRadius, double electronegativity, double firstIonization, double density, double meltingPoint, double boilingPoint, int numberOfIsotopes, String discoverer, int year, double specificHeat, int numberOfShells, int numberOfValence) {
            this.atomicNumber = atomicNumber;
            this.element = element;
            this.symbol = symbol;
            this.atomicMass = atomicMass;
            this.numberOfNeutrons = numberOfNeutrons;
            this.numberOfProtons = numberOfProtons;
            this.numberOfElectrons = numberOfElectrons;
            this.period = period;
            this.group = group;
            this.phase = phase;
            this.isRadioactive = isRadioactive.equals("yes");
            this.isNatural = isNatural.equals("yes");
            this.isMetal = isMetal.equals("yes");
            this.isNonmetal = isNonmetal.equals("yes");
            this.isMetalloid = isMetalloid.equals("yes");
            this.type = type;
            this.atomicRadius = atomicRadius;
            this.electronegativity = electronegativity;
            this.firstIonization = firstIonization;
            this.density = density;
            this.meltingPoint = meltingPoint;
            this.boilingPoint = boilingPoint;
            this.numberOfIsotopes = numberOfIsotopes;
            this.discoverer = discoverer;
            this.year = year;
            this.specificHeat = specificHeat;
            this.numberOfShells = numberOfShells;
            this.numberOfValence = numberOfValence;
        }

        // override toString method to allow printing
        @Override
        public String toString() {
            return atomicNumber + " " + element + " " + symbol + " " + atomicMass + " " + numberOfNeutrons + " " + numberOfProtons + " " + numberOfElectrons + " " + period + " " + group + " " + phase + " " + isRadioactive + " " + isNatural + " " + isMetal + " " + isNonmetal + " " + isMetalloid + " " + type + " " + atomicRadius + " " + electronegativity + " " + firstIonization + " " + density + " " + meltingPoint + " " + numberOfIsotopes + " " + discoverer + " " + year + " " + specificHeat + " " + numberOfShells + " " + numberOfValence + "\n";
        }
    }

    // Method that allows to read file and returns as string
    private static String readFile(String fileName) throws IOException {
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
    private static double ParseDouble(String strNumber) {
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
    private static int ParseInt(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) { // if the string is empty return 0, otherwise return the integer value
            try {
                return Integer.parseInt(strNumber);
            } catch(Exception e) {
                return 0;
            }
        }
        else return 0;
    }

    private static ArrayList<Element> elementsAr = new ArrayList<>(); // arraylist contains Elements objects

    public static void main(String[] args) {
        try {

            String periodicTable = readFile("/Users/MattonXia/Desktop/contacts/src/Balkulator/src/src/classes/PeriodTableData.csv"); // uses readFile method from specific file location

            String[] elements = periodicTable.split("\n"); // splits the file into each row of the csv file and store it into an array

            for(int i = 1; i < elements.length; i++) {
                String[] details = elements[i].split(","); // splits each row into their individual elements
                elementsAr.add(new Element(
                        ParseInt(details[0]), // atomic number
                        details[1], // element
                        details[2], // symbol
                        ParseDouble(details[3]), // atomic mass
                        ParseInt(details[4]), // number of neutrons
                        ParseInt(details[5]), // number of protons
                        ParseInt(details[6]), // number of electrons
                        ParseInt(details[7]), // perid
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
        for(Element x : elementsAr) {
            System.out.println(x);
        }
    }
}
