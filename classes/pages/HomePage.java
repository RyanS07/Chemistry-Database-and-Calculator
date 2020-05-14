package classes.pages;

import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HomePage extends Page {
    private Button start;
    private Button instructions;


    public HomePage() {
        this.pane = new Pane();
        this.scene = new Scene(this.pane, Page.width, Page.height);
        
        Text title = new Text("Balkulator");
        title.setFont(Font.font("Impact", 80));
        Bounds titleBounds = title.getLayoutBounds();
        int offset = 100;
        title.setX((width-titleBounds.getWidth())/2);
        // Puts the title offset pixels from the top of the screen to the top of the text
        // setY() assigns bottom left corner
        title.setY(titleBounds.getHeight() + offset);
        this.pane.getChildren().add(title);

        this.start = setButton("Begin");
        this.start.setLayoutX((width-this.start.getPrefWidth())/2);
        this.start.setLayoutY(title.getY() + 100);
        this.pane.getChildren().add(start);

        this.instructions = setButton("Guide");
        this.instructions.setLayoutX((width - this.instructions.getPrefWidth())/2);
        this.instructions.setLayoutY(this.start.getLayoutY() + 100);
        this.pane.getChildren().add(instructions);

        setRedirects();
    }

    /* Description:
     *  - Establishes all page paths 
     *  - See Page class for explanation on goTo()
     *  - Only needed to be called once
     *  - Only call after all buttons have been initialized
     *    - ie. At the end of the constructor
     */
    public void setRedirects() {
        // When start is pressed, go to "Tables" page
        this.start.setOnAction(event -> {
            goTo("Tables");
        });
        // When instructions is pressed, go to "Instructions" page
        this.instructions.setOnAction(event -> {
            goTo("Instructions");
        });
    }

}