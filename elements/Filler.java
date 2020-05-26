package elements;

public class Filler extends Element {
    /* Since the Filler class builds on the Element class, it calls the super()
     * constructor, while doing a little more. 
     */
    public Filler(String[] details) {
        super(details);
        // Defines the css styles of each icon.
        // Source/Research: http://tutorials.jenkov.com/javafx/css-styling.html
        setDefaultStyle("-fx-background-color: #a6b7bf");
        setHoverStyle("-fx-background-color: #698491");
        setIcon();
    }
}