package classes.pages.periodictable;

public class Metal extends Element {

    // Empty default constructor
    public Metal() {
        super();
    }

    // Non-empty constructor
    public Metal(String[] details) {
        super(details);
    }

    // Implement abstract method from Element class for Metals
    public String determineIon() {
        return "-" + this.getNumberOfValence();
    }
}
