package pages.periodictable;

public class Nonmetal extends Element {
    /* Since the Nonmetal class builds on the Element class, it calls the super()
     * constructor, while doing a little more. 
     */
    public Nonmetal(String[] details) {
        super(details);
        // Defines the css styles of each icon.
        // Source/Research: http://tutorials.jenkov.com/javafx/css-styling.html
        setDefaultStyle("-fx-background-color: #00aca1");
        setHoverStyle("-fx-background-color: #2c9c91");
        setIcon();
    }
}
