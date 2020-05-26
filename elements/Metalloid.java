package elements;

public class Metalloid extends Element {
    /* Since the Metalloid class builds on the Element class, it calls the super()
     * constructor, while doing a little more. 
     */
    public Metalloid(String[] details) {
        super(details);
        // Defines the css styles of each icon.
        // Source/Research: http://tutorials.jenkov.com/javafx/css-styling.html
        setDefaultStyle("-fx-background-color: #dbfd45");
        setHoverStyle("-fx-background-color: #b6de02");
        setIcon();
    }
}
