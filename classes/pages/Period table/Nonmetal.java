public class Nonmetal extends Element {

    // Empty default constructor
    public Nonmetal() {
        super();
    }

    // Non-empty constructor
    public Nonmetal(String[] details) {
        super(details);
    }

    // Implement abstract method from Element class for Non-metals
    public String determineIon() {
        return "+" + this.getNumberOfValence();
    }
}
