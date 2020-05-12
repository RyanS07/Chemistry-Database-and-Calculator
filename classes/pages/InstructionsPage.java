package classes.pages;


import java.util.HashMap;

import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InstructionsPage extends Page {
    private Button back;

    public InstructionsPage() {
        this.pane = new Pane();
        this.scene = new Scene(this.pane, Page.pageWidth, Page.pageHeight);

        this.back = setBackButton();
        this.pane.getChildren().add(this.back);
    }

    public void setRedirects() {
        this.back.setOnAction(event -> {
            goTo("Home");
        });
    }
}