public class Metalloid extends Element {

    // Empty default constructor
    public Metalloid() {
        super();
    }

    // Non-empty constructor
    public Metalloid(String[] details) {
        super(details);
    }

    // Implement abstract method from Element class for Metalloids
    public String determineIon() {
        return "+" + this.getNumberOfValence();
    }
}
