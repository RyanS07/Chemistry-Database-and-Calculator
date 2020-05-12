package classes.pages;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CalculatorPage extends Page {
    private Button back;

    public CalculatorPage() {
        this.pane = new Pane();
        this.scene = new Scene(this.pane, Page.pageWidth, Page.pageHeight);

        this.back = setBackButton();
        this.pane.getChildren().add(this.back);
    }

    public void setRedirects() {
        this.back.setOnAction(event -> {
            goTo("Tables");
        });
    }
}