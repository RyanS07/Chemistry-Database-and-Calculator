package classes.pages.periodictable;

public class Metalloid extends Element {

    // Empty default constructor
    public Metalloid() {
        super();
        this.style = "-fx-background-color: #dbfd45";
    }

    // Non-empty constructor
    public Metalloid(String[] details) {
        super(details);
        this.style = "-fx-background-color: #dbfd45";
    }

    // Implement abstract method from Element class for Metalloids
    protected String determineIon() {
        return "+" + this.getNumberOfValence();
    }
}
