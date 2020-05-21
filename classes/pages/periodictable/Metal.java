package classes.pages.periodictable;

public class Metal extends Element {
    // Empty default constructor
    public Metal() {
        super();
        this.style = "-fx-background-color: #f44b74";
    }

    // Non-empty constructor
    public Metal(String[] details) {
        super(details);
        this.style = "-fx-background-color: #f44b74";
    }

    @Override
    public void setIcon() {
        super.setIcon();
        // Implement style
    }

    // Implement abstract method from Element class for Metals
    protected String determineIon() {
        return "-" + this.getNumberOfValence();
    }
}
