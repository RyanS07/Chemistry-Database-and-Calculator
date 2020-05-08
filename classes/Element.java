public class Element {

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
