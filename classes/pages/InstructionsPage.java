package classes.pages;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class InstructionsPage extends Page {
    private Button back;

    public InstructionsPage() {
        this.pane = new Pane();
        this.scene = new Scene(this.pane, Page.width, Page.height);

        this.back = setBackButton();
        this.pane.getChildren().add(this.back);

        setRedirects();
    }

    public void setRedirects() {
        this.back.setOnAction(event -> {
            goTo("Home");
        });
    }
}