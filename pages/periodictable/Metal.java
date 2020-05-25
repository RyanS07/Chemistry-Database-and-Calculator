package pages.periodictable;

public class Metal extends Element {
    /* Since the Metal class builds on the Element class, it calls the super()
     * constructor, while doing a little more. 
     */
    public Metal(String[] details) {
        super(details);
        // Defines the css styles of each icon.
        // Source/Research: http://tutorials.jenkov.com/javafx/css-styling.html
        setDefaultStyle("-fx-background-color: #f15b5b");
        setHoverStyle("-fx-background-color: #eb1e1e");
        setIcon();
    }
}
