package classes.pages.periodictable;

public class Filler extends Element {

    public Filler() {
        super();
        this.style = "-fx-background-color: #a6b7bf";
    }

    public Filler(String[] details) {
        super(details);
        this.style = "-fx-background-color: #a6b7bf";
    }

    protected String determineIon() {
        return "";
    }
}