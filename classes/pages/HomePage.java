package classes.pages;

import java.util.HashMap;

import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomePage extends Page {
    private Button start;
    private Button instructions;

    public HomePage() {
        this.pane = new Pane();
        this.scene = new Scene(this.pane, Page.pageWidth, Page.pageHeight);
        
        Text title = new Text(this.appTitle);
        title.setFont(Font.font("Impact", 80));
        Bounds titleBounds = title.getLayoutBounds();
        int offset = 100;
        title.setX((pageWidth-titleBounds.getWidth())/2);
        // Puts the title offset pixels from the top of the screen to the top of the text
        // setY() assigns bottom left corner
        title.setY(titleBounds.getHeight() + offset);
        this.pane.getChildren().add(title);

        this.start = setButton("Begin");
        this.start.setLayoutX((pageWidth-this.start.getPrefWidth())/2);
        this.start.setLayoutY(title.getY() + 100);
        this.pane.getChildren().add(start);

        this.instructions = setButton("Guide");
        this.instructions.setLayoutX((pageWidth - this.instructions.getPrefWidth())/2);
        this.instructions.setLayoutY(this.start.getLayoutY() + 100);
        this.pane.getChildren().add(instructions);
    }

    public void setRedirects() {
        this.start.setOnAction(event -> {
            goTo("Tables");
        });
        this.instructions.setOnAction(event -> {
            goTo("Instructions");
        });
    }

}