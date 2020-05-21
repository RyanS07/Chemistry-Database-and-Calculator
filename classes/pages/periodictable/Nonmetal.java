package classes.pages.periodictable;

public class Nonmetal extends Element {

    // Empty default constructor
    public Nonmetal() {
        super();
        this.style = "-fx-background-color: #00aca1";
    }

    // Non-empty constructor
    public Nonmetal(String[] details) {
        super(details);
        this.style = "-fx-background-color: #00aca1";
    }

    // Implement abstract method from Element class for Non-metals
    protected String determineIon() {
        return "+" + this.getNumberOfValence();
    }
}
