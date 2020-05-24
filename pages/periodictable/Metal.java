package pages.periodictable;

public class Metal extends Element {
    // Non-empty constructor
    public Metal(String[] details) {
        super(details);
        // Source: http://tutorials.jenkov.com/javafx/button.html
        setDefaultStyle("-fx-background-color: #f15b5b");
        setHoverStyle("-fx-background-color: #eb1e1e");
        setIcon();
    }
}
