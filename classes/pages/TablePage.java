package classes.pages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class TablePage extends Page {
    private Button back;
    private Button toCalculators;
    private ArrayList<String[]> solubilityValues;

    public TablePage() {
        this.pane = new Pane();
        this.scene = new Scene(this.pane, Page.width, Page.height);

        this.back = setBackButton();
        this.pane.getChildren().add(this.back);

        this.toCalculators = setButton("Calculators");
        this.toCalculators.setLayoutX(Page.width - this.toCalculators.getPrefWidth() - 10);
        this.toCalculators.setLayoutY(Page.height - this.toCalculators.getPrefHeight() - 10);
        this.pane.getChildren().add(this.toCalculators);

        try {
            this.solubilityValues = fileReader("Solubility.csv");
        } catch (Exception e) {
            System.out.println("Solubility.csv not found");
        }
        

        setRedirects();
    }

    protected void setRedirects() {
        this.back.setOnAction(event -> {
            goTo("Home");
        });
        this.toCalculators.setOnAction(event -> {
            goTo("Calculators");
        });
    }

    private ArrayList<String[]> fileReader(String fileName) throws Exception {
		File file = new File(System.getProperty("user.dir") + "\\CSV\\" + fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        ArrayList<String[]> fileValues = new ArrayList<String[]>();
		String line;
		while ((line = br.readLine()) != null) {
			fileValues.add(line.split(","));
		}
        br.close();
        return fileValues;
	}
}