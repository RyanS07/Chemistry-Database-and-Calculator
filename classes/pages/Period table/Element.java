import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class Element {

    // instance variables made private to not be easily accessible and changeable
    private SimpleIntegerProperty atomicNumber;
    private SimpleStringProperty symbol;
    private SimpleStringProperty name;
    private SimpleDoubleProperty atomicMass;
    private SimpleStringProperty electronicConfiguration;
    private SimpleDoubleProperty electronegativity;
    private SimpleDoubleProperty ionizationEnergy;
    private SimpleDoubleProperty electronAffinity;
    private SimpleStringProperty standardState;
    private SimpleDoubleProperty meltingPoint;
    private SimpleDoubleProperty boilingPoint;
    private SimpleDoubleProperty density;
    private SimpleStringProperty groupBlock;

    // getter for element class for all instance variables made public to be allowed to be accessible by other classes
    public SimpleIntegerProperty getAtomicNumber() {
        return atomicNumber;
    }

    public SimpleStringProperty getSymbol() {
        return symbol;
    }

    public SimpleStringProperty getName() {
        return name;
    }

    public SimpleDoubleProperty getAtomicMass() {
        return atomicMass;
    }

    public SimpleStringProperty getElectronicConfiguration() {
        return electronicConfiguration;
    }

    public SimpleDoubleProperty getElectronegativity() {
        return electronegativity;
    }

    public SimpleDoubleProperty getIonizationEnergy() {
        return ionizationEnergy;
    }

    public SimpleDoubleProperty getElectronAffinity() {
        return electronAffinity;
    }

    public SimpleStringProperty getStandardState() {
        return standardState;
    }

    public SimpleDoubleProperty getMeltingPoint() {
        return meltingPoint;
    }

    public SimpleDoubleProperty getBoilingPoint() {
        return boilingPoint;
    }

    public SimpleDoubleProperty getDensity() {
        return density;
    }

    public SimpleStringProperty getGroupBlock() {
        return groupBlock;
    }


    /*
     * Helper classes
     */
    // converts string into double with exception handling
    public static SimpleDoubleProperty ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) { // if the string is empty return 0, otherwise return the double value
            try {
                return new SimpleDoubleProperty(Double.parseDouble(strNumber));
            } catch (Exception e) {
                return new SimpleDoubleProperty(0.0);
            }
        } else return new SimpleDoubleProperty(0);
    }

    // converts string into int with exception handling
    public static SimpleIntegerProperty ParseInt(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) { // if the string is empty return 0, otherwise return the integer value
            try {
                return new SimpleIntegerProperty(Integer.parseInt(strNumber));
            } catch (Exception e) {
                return new SimpleIntegerProperty(0);
            }
        } else return new SimpleIntegerProperty(0);
    }

    // non-empty constructor for Element class
    public Element(String[] details) {
        atomicNumber = ParseInt(details[0]);
        symbol = new SimpleStringProperty(details[1]);
        name = new SimpleStringProperty(details[2]);
        atomicMass = ParseDouble(details[3]);
        electronicConfiguration = new SimpleStringProperty(details[4]);
        electronegativity = ParseDouble(details[5]);
        ionizationEnergy = ParseDouble(details[6]);
        electronAffinity = ParseDouble(details[7]);
        standardState = new SimpleStringProperty(details[8]);
        meltingPoint = ParseDouble(details[9]);
        boilingPoint = ParseDouble(details[10]);
        density = ParseDouble(details[11]);
        groupBlock = new SimpleStringProperty(details[12]);
    }

    @Override
    public String toString() {
        return atomicNumber +  " " + symbol +  " " + name +  " " + atomicMass +  " " + electronicConfiguration +  " "
               + electronegativity +  " " + ionizationEnergy +  " " + electronAffinity +  " " + standardState +  " "
               + meltingPoint +  " " + boilingPoint +  " "
               + density +  " " + groupBlock;
    }

    // Abstract methods
    public abstract String determineIon();

}
