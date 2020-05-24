package pages.periodictable;

public class Nonmetal extends Element {
    // Non-empty constructor
    public Nonmetal(String[] details) {
        super(details);
        setDefaultStyle("-fx-background-color: #00aca1");
        setHoverStyle("-fx-background-color: #2c9c91");
        setIcon();
    }
}
