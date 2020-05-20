public abstract class Element {

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

    /*
     * Helper classes
     */
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

    // non-empty constructor for Element class
    public Element(String[] details) {
        this.atomicNumber = ParseInt(details[0]);
        this.element = details[1];
        this.symbol = details[2];
        this.atomicMass = ParseDouble(details[3]);
        this.numberOfNeutrons = ParseInt(details[4]);
        this.numberOfProtons = ParseInt(details[5]);
        this.numberOfElectrons = ParseInt(details[6]);
        this.period = ParseInt(details[7]);
        this.group = ParseInt(details[8]);
        this.phase = details[9];
        this.isRadioactive = details[10].equals("yes") ? true : false;
        this.isNatural = details[11].equals("yes") ? true : false;
        this.type = details[15];
        this.atomicRadius = ParseDouble(details[16]);
        this.electronegativity = ParseDouble(details[17]);
        this.firstIonization = ParseDouble(details[18]);
        this.density = ParseDouble(details[19]);
        this.meltingPoint = ParseDouble(details[20]);
        this.boilingPoint = ParseDouble(details[21]);
        this.numberOfIsotopes = ParseInt(details[22]);
        this.discoverer = details[23];
        this.year = ParseInt(details[24]);
        this.specificHeat = ParseDouble(details[25]);
        this.numberOfShells = ParseInt(details[26]);
        this.numberOfValence = ParseInt(details[27]);
    }

    // override toString method to allow printing
    @Override
    public String toString() {
        return atomicNumber + " " + element + " " + symbol + " " + atomicMass + " " + numberOfNeutrons + " " + numberOfProtons + " " + numberOfElectrons + " " + period + " " + group + " " + phase + " " + isRadioactive + " " + isNatural + " " + type + " " + atomicRadius + " " + electronegativity + " " + firstIonization + " " + density + " " + meltingPoint + " " + numberOfIsotopes + " " + discoverer + " " + year + " " + specificHeat + " " + numberOfShells + " " + numberOfValence + "\n";
    }

    // Abstract methods
    public abstract String determineIon();

}
