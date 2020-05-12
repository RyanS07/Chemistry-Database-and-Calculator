package classes.pages;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TablePage extends Page {
    private Button back;
    private Button toCalculators;

    public TablePage() {
        this.pane = new Pane();
        this.scene = new Scene(this.pane, Page.pageWidth, Page.pageHeight);

        this.back = setBackButton();
        this.pane.getChildren().add(this.back);

        this.toCalculators = setButton("Calculators");
        this.toCalculators.setLayoutX(Page.pageWidth - this.toCalculators.getPrefWidth() - 10);
        this.toCalculators.setLayoutY(Page.pageHeight - this.toCalculators.getPrefHeight() - 10);
        this.pane.getChildren().add(this.toCalculators);
    }

    public void setRedirects() {
        this.back.setOnAction(event -> {
            goTo("Home");
        });
        this.toCalculators.setOnAction(event -> {
            goTo("Calculators");
        });
    }
}