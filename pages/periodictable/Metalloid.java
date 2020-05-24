package pages.periodictable;

public class Metalloid extends Element {
    // Non-empty constructor
    public Metalloid(String[] details) {
        super(details);
        setDefaultStyle("-fx-background-color: #dbfd45");
        setHoverStyle("-fx-background-color: #b6de02");
        setIcon();
    }
}
