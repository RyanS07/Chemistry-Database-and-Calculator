package classes.pages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;

import classes.calculators.IdealGasLaw;
import classes.pages.rows.VapourPressureRow;
import classes.calculators.Cross;
import classes.calculators.Data;


public class CalculatorPage extends Page {
    private Button back;
    private String[] gasLawTitles = {"Ideal Gas Law", "Boyle's Law", "Charles' Law", 
        "Gay-Lussac's Law", "Avogadro's Hypothesis"};
    private String[][] gasLawHeaders = { 
        { "Pressure (kPa)", "Volume (L)", "Moles (mol)", "Temperature (K)" },
        { "Pressure 1 (kPa)", "Volume 1 (L)", "Pressure 2 (kPa)", "Volume 2 (L)" }, 
        { "Volume 1 (L)", "Temperature 1 (K)", "Volume 2 (L)", "Temperature 2 (K)" },
        { "Pressure 1 (kPa)", "Temperature 1 (K)", "Pressure 2 (kPa)", "Temperature 2 (K)" }, 
        { "Volume 1 (L)", "Moles 1 (mol)", "Volume 2 (L)", "Moles 2 (mol)" } 
    };
    private HashMap<String, String[]> gasLawHeaderMap = new HashMap<String, String[]>();

    private MenuBar calcMenu;

    private Text calcTitle;
    private int numInputs = 4;
    private VBox gasLawCalcBox;
    private String calcType;
    private Text calcResponse;
    private HBox gasLawBox;

    private VBox vapourPressureTableBox;
    private Text vapourPressureTableTitle;
    private TableView<VapourPressureRow> vapourPressureTable;
    private String[] vapourPressureTableProperties = {"Temperature", "Pressure"};
    private String[] vapourPressureTableHeaders = {"Temperature (K)", "Pressure (kPa)"};
    private String vapourPressureFileName = "VapourPressure.csv";

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
                    displayAnswer();
                }
            }
        });

        setRedirects();
    }

    // Called once
    protected void setRedirects() {
        this.back.setOnAction(event -> {
            goTo("Tables");
        });
    }

    // Called once
    private void setGasLawHeaderMap() {
        for (int i = 0; i < gasLawTitles.length; i++) {
            this.gasLawHeaderMap.put(this.gasLawTitles[i], this.gasLawHeaders[i]);
        }
    }

    private void setGasLawCalc(String type) {
        // Clears the old setup, if there is one
        this.pane.getChildren().remove(this.gasLawCalcBox);

        this.gasLawBox = new HBox(20);
        this.gasLawBox.setPadding(new Insets(20, 20, 20, 20));
        
        this.calcType = type;
        this.calcTitle = new Text(this.calcType);
        
        for(int i = 0; i < numInputs; i++) {
            VBox inputBox = new VBox(5);
            inputBox.getChildren().add(new Text(gasLawHeaderMap.get(calcType)[i]));
            inputBox.getChildren().add(new TextField());
            this.gasLawBox.getChildren().add(inputBox);
        }
        this.gasLawBox.setLayoutY(this.calcTitle.getLayoutY() + 10);

        this.calcResponse = new Text();
        this.calcResponse.setLayoutY((this.gasLawBox.getLayoutY() + 10));
        this.calcResponse.setLayoutX(20);

        this.gasLawCalcBox = new VBox(20);
        this.gasLawCalcBox.setPadding(new Insets(20,20,20,20));
        this.gasLawCalcBox.setLayoutY(this.calcMenu.getLayoutY() + 100);
        this.gasLawCalcBox.setLayoutX(20);
        this.gasLawCalcBox.getChildren().addAll(this.calcTitle, this.gasLawBox, this.calcResponse);

        if(this.calcType.equals(gasLawTitles[0])) {
            setVapourPressureTable();
        } else {
            clearVapourPressureTable();
        }

        this.pane.getChildren().add(this.gasLawCalcBox);
    }

    private String calculate() {
        if(gasLawValidInput()) {
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
        } else {
            return "Invalid Entry";
        }
        
    }

    // Check if no units provided
    private boolean gasLawValidInput() {
        int numEmpty = 0;
        for(int i = 0; i < this.numInputs; i++) {
            VBox vb = (VBox) this.gasLawBox.getChildren().get(i);
            TextField tf = (TextField) vb.getChildren().get(1);
            String fieldValue = tf.getCharacters().toString().trim();
            if(fieldValue.equals("")) {
                numEmpty++;
            } 
        }
        return numEmpty == 1;
    }

    private void displayAnswer() {
        for(int i = 0; i < this.numInputs; i++) {
            VBox vb = (VBox) this.gasLawBox.getChildren().get(i);
            Text header = (Text) vb.getChildren().get(0);
            TextField tf = (TextField) vb.getChildren().get(1);
            if(tf.getCharacters().toString().trim().equals("")) {
                String headerText = header.getText();
                int start = headerText.indexOf('(') + 1;
                int end = headerText.indexOf(')');
                // Will never get IndexOutOfBounds because headers are predefined
                this.calcResponse.setText(" = " + calculate() + headerText.substring(start, end));
                break;
            } 
        }  
    }
    
    // Called once
    private void setCalcMenu() {
        Menu gasLawCalcMenu = new Menu("Gas Law");
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent> () {
            public void handle(ActionEvent e) { 
                String selection = ((MenuItem) e.getSource()).getText();
                setGasLawCalc(selection);
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
        // Add Concentration Menu
        this.calcMenu.getMenus().add(gasLawCalcMenu);
        this.pane.getChildren().add(calcMenu);
    }

    private void setVapourPressureTable() {
        this.vapourPressureTableTitle = new Text("Vapour Pressure of Water");
        this.vapourPressureTableTitle.setLayoutX(20);

        // Initialized to emptp list to avoid "Not initialized" warning
        ObservableList<VapourPressureRow> vapourPressureValues = FXCollections.observableArrayList();
        try {
            vapourPressureValues = getVapourPressureValues();
        } catch (IOException e) {
            System.out.println("Solubilty.csv not found");
        }
        this.vapourPressureTable = new TableView<VapourPressureRow>();
        for(int i = 0; i < vapourPressureTableProperties.length; i++) {
            TableColumn<VapourPressureRow, String> column;
            // Assigned on separate line to improve readability
            column = new TableColumn<VapourPressureRow, String>(vapourPressureTableHeaders[i]);
            // Source: https://stackoverflow.com/questions/13455326/javafx-tableview-text-alignment
            column.setStyle("-fx-alignment: CENTER;");
            column.setPrefWidth(200);
            column.setCellValueFactory(new PropertyValueFactory<VapourPressureRow, String>(vapourPressureTableProperties[i]));
            column.setSortable(false);
            this.vapourPressureTable.getColumns().add(column);
        }
        this.vapourPressureTable.setItems(vapourPressureValues);
        this.vapourPressureTable.setPrefHeight(300);
        this.vapourPressureTable.setSelectionModel(null);
        this.vapourPressureTable.setLayoutY(this.vapourPressureTableTitle.getLayoutY() + 20);        

        this.vapourPressureTableBox = new VBox();
        this.vapourPressureTableBox.setPadding(new Insets(20,20,20,20));
        this.vapourPressureTableBox.getChildren().addAll(this.vapourPressureTableTitle, this.vapourPressureTable);

        this.vapourPressureTableBox.setLayoutY(this.gasLawCalcBox.getLayoutY() + 150);
        this.vapourPressureTableBox.setLayoutX(20);
        this.pane.getChildren().add(this.vapourPressureTableBox);
    }

    private ObservableList<VapourPressureRow> getVapourPressureValues() throws IOException {
        String filePath = System.getProperty("user.dir") + "\\classes\\pages\\CSV\\" + this.vapourPressureFileName;
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        ObservableList<VapourPressureRow> fileValues = FXCollections.observableArrayList();
		String line;
		while ((line = br.readLine()) != null) {
            String[] row = line.split(",");
			fileValues.add(new VapourPressureRow(row[0], row[1]));
        }
        br.close();
        return fileValues;
    }

    private void clearVapourPressureTable() {
        this.pane.getChildren().remove(this.vapourPressureTableBox);
    }
}