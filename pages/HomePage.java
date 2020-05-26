package pages;

import javafx.geometry.Bounds;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HomePage extends Page {
    // The start button that redirects to the Table Page.
    private Button start;

    public HomePage() {
        super();
        Text title = new Text("Balkulator");
        title.setFont(Font.font("Impact", 80));
        Bounds titleBounds = title.getLayoutBounds();
        int offset = 100;
        // Positions the title in the center of the pane.
        title.setX((Page.width-titleBounds.getWidth())/2);
        title.setY(Page.height/2 - offset);

        Text subtitle = new Text("Chemistry database and calculator");
        subtitle.setFont(Font.font("Impact", 20));
        Bounds subtitleBounds = subtitle.getLayoutBounds();
        // Positions the subtitle in the center of the pane.
        subtitle.setX((Page.width-subtitleBounds.getWidth())/2);
        subtitle.setY(title.getY() + 50);

        this.start = setButton("Begin");
        // Positions the start button in the center of the screen.
        this.start.setLayoutX((Page.width-this.start.getPrefWidth())/2);
        this.start.setLayoutY(subtitle.getY() + 50);

        this.pane.getChildren().addAll(title, subtitle, start);

        setRedirects();
    }

    /* Description:
     *  - Establishes all page paths 
     *  - See Page class for explanation on goTo()
     *  - Only needs to be called once
     *  - Only call after all buttons have been initialized
     *    - ie. At the end of the constructor
     *    - Cannot be part of super() since it relies on state 
     *      unique to each Page subclass
     */
    protected void setRedirects() {
        // When start is pressed, go to "Tables" page
        this.start.setOnAction(event -> {
            goTo("Table");
        });
    }
}