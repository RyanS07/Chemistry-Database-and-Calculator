package classes.pages;

import java.util.Arrays;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;

import classes.calculators.IdealGasLaw;
import classes.calculators.Cross;


public class CalculatorPage extends Page {
    private Button back;
    private String[] gasLawTitles = {"Ideal Gas Law", "Boyle's Law", "Charles' Law", 
        "Gay-Lussac's Law", "Avogadro's Hypothesis"};
    private String[][] gasLawHeaders = { 
        { "Pressure", "Volume", "Moles", "Temperature" },
        { "Pressure 1", "Volume 1", "Pressure 2", "Volume 2" }, 
        { "Volume 1", "Temperature 1", "Volume 2", "Temperature 2" },
        { "Pressure 1", "Temperature 1", "Pressure 2", "Temperature 2" }, 
        { "Volume 1", "Moles 1", "Volume 2", "Moles 2" } 
    };
    private HashMap<String, String[]> gasLawHeaderMap = new HashMap<String, String[]>();

    private MenuBar calcMenu;

    private Text calcTitle;
    private int numInputs = 4;
    private String calcType;
    private HBox gasLawBox;

    public CalculatorPage() {
        this.pane = new Pane();
        this.scene = new Scene(this.pane, Page.width, Page.height);

        this.back = setBackButton();
        this.pane.getChildren().add(this.back);

        setGasLawHeaderMap();
        setCalcMenu();
        setGasLawCalc(gasLawTitles[0]);

        this.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.ENTER) {
                    if(gasLawValidInput()) {
                        System.out.println(calculate());
                    } else {
                        System.out.println("Invalid Entry");
                    }
                    
                }
            }
        });

        setRedirects();
    }

    protected void setRedirects() {
        this.back.setOnAction(event -> {
            goTo("Tables");
        });
    }

    private void setGasLawHeaderMap() {
        for (int i = 0; i < gasLawTitles.length; i++) {
            this.gasLawHeaderMap.put(this.gasLawTitles[i], this.gasLawHeaders[i]);
        }
    }

    private void setGasLawCalc(String type) {
        this.pane.getChildren().remove(this.calcTitle);
        this.pane.getChildren().remove(this.gasLawBox);
        this.gasLawBox = new HBox(20);
        this.gasLawBox.setPadding(new Insets(20, 20, 20, 20));
        this.calcType = type;
        this.calcTitle = new Text(this.calcType);
        this.calcTitle.setLayoutY(this.calcMenu.getLayoutY() + 100);
        this.calcTitle.setLayoutX(20);
        
        for(int i = 0; i < numInputs; i++) {
            VBox inputBox = new VBox(5);
            inputBox.getChildren().add(new Text(gasLawHeaderMap.get(calcType)[i]));
            inputBox.getChildren().add(new TextField());
            this.gasLawBox.getChildren().add(inputBox);
        }
        this.gasLawBox.setLayoutY(this.calcTitle.getLayoutY() + 10);

        this.pane.getChildren().addAll(this.calcTitle, this.gasLawBox);
    }

    private void updateGasLawCalc(String type) {
        // this.pane.getChildren().remove(this.calcTitle);
        // this.pane.getChildren().remove(this.gasLawBox);
        setGasLawCalc(type);
    }

    private String calculate() {
        // Pull values from TextFields
        String[] inputs = new String[this.numInputs];
        for(int i = 0; i < inputs.length; i++) {
            VBox vb = (VBox) this.gasLawBox.getChildren().get(i);
            TextField tf = (TextField) vb.getChildren().get(1);
            inputs[i] = tf.getCharacters().toString().trim();
            tf.clear();
        }

        // Uses headerList to assure same string
        // calcType will only ever be assigned a value from title[]
        if(this.calcType.equals(gasLawTitles[0])) {
            return IdealGasLaw.solve(inputs);
        } else if(this.calcType.equals(gasLawTitles[1])) {
            return Cross.divide(inputs);
        } else {
            return Cross.multiply(inputs);
        }
    }

    private boolean gasLawValidInput() {
        int numEmpty = 0;
        for(int i = 0; i < this.numInputs; i++) {
            VBox vb = (VBox) this.gasLawBox.getChildren().get(i);
            TextField tf = (TextField) vb.getChildren().get(1);
            if(tf.getCharacters().toString().trim().equals("")) {
                numEmpty++;
            }
        }
        return numEmpty == 1;
    }
    
    private void setCalcMenu() {
        Menu gasLawCalcMenu = new Menu("Gas Law");
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent> () {
            public void handle(ActionEvent e) { 
                String selection = ((MenuItem) e.getSource()).getText();
                updateGasLawCalc(selection);
            }
        };
        for(int i = 0; i < gasLawTitles.length; i++) {
            MenuItem mi = new MenuItem(gasLawTitles[i]);
            mi.setOnAction(event);
            gasLawCalcMenu.getItems().add(mi);
        }
        this.calcMenu = new MenuBar();
        this.calcMenu.setLayoutX(20);
        this.calcMenu.setLayoutY(100);

        // Add Stoic Calc Menu Here
        calcMenu.getMenus().add(gasLawCalcMenu);
        this.pane.getChildren().add(calcMenu);
    }
}