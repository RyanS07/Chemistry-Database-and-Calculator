package pages;

import pages.periodictable.Element;
import pages.periodictable.Filler;
import pages.periodictable.Metal;
import pages.periodictable.Metalloid;
import pages.periodictable.Nonmetal;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class TablePage extends Page {
    private Button back;
    private Button toCalculators;

    // https://stackoverflow.com/questions/11145681/how-to-convert-a-string-with-unicode-encoding-to-a-string-of-letters
    // https://en.wikipedia.org/wiki/Unicode_subscripts_and_superscripts
    // Learning about Unicode:
    // https://docs.oracle.com/javase/tutorial/i18n/text/unicode.html

    private ArrayList<Element> periodicTableValues;
    private String[] elementTableProperties;
    private String[] elementTableHeaders = {
        "#", "Symbol", "Name", "Molar Mass (g/mol)", 
        "Electron Configuration", "Period", "Group", 
        "Electronegativity", "Van Del Waals Radius (\u212B)", 
        "ionRadius (\u212B)", "1st Ionization Energy (kJ/mol)", 
        "Electron Affinity (kJ/mol)", "State", "Melting Point (K)", 
        "Boiling Point (K)", "Density (g/ml)", "Group Name", 
        "Ion Charge"
    };

    private MenuBar tableMenu;
    private Text tableTitle;
    private Text instructions;
    private VBox tableBox;
    private String tableType;
    private String[] tableOptions = {
        "Periodic Table", "Solubility Table", 
        "Activity Series", "Vapour Pressure Table"
    };

    public TablePage() {
        super();

        this.back = setBackButton();
        this.pane.getChildren().add(this.back);

        this.toCalculators = setButton("Calculators");
        this.toCalculators.setLayoutX(Page.width - this.toCalculators.getPrefWidth() - 10);
        this.toCalculators.setLayoutY(10);
        this.pane.getChildren().add(this.toCalculators);

        setTableMenu();
        setTable(tableOptions[0]);
        
        setRedirects();
    }

    protected void setRedirects() {
        this.back.setOnAction(event -> {
            goTo("Home");
        });
        this.toCalculators.setOnAction(event -> {
            goTo("Calculator");
        });
    }

    private void setTableMenu() {
        Menu tableMenuOptions = new Menu("Options");
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent> () {
            public void handle(ActionEvent e) { 
                String selection = ((MenuItem) e.getSource()).getText();
                setTable(selection);
            }
        };
        for(int i = 0; i < tableOptions.length; i++) {
            MenuItem mi = new MenuItem(tableOptions[i]);
            mi.setOnAction(event);
            tableMenuOptions.getItems().add(mi);
        }
        this.tableMenu = new MenuBar();
        this.tableMenu.setLayoutX(20);
        this.tableMenu.setLayoutY(100);

        this.tableMenu.getMenus().add(tableMenuOptions);
        this.pane.getChildren().add(tableMenu);
    }

    private void setTable(String type) {
        this.pane.getChildren().remove(this.tableBox);

        this.tableType = type;
        this.tableTitle = new Text(this.tableType);

        this.tableBox = new VBox(20);
        this.tableBox.setPadding(new Insets(20, 20, 20, 20));
        this.tableBox.setLayoutY(this.tableMenu.getLayoutY() + 50);
        this.tableBox.setLayoutX(20);
        this.tableBox.getChildren().add(this.tableTitle);

        ObservableList<String[]> csv = FXCollections.observableArrayList();
        TableView<String[]> table = new TableView<String[]>();
        String[] headers;
        int columnWidth = 0;
        int tableHeight = 0;
        String guide = "";
        instructions = new Text();
        
        if(type.equals(tableOptions[0])) {
            createPeriodicTable();
            this.pane.getChildren().addAll(this.tableBox);
            return;
        } else {
            try {
                csv = readCSV(type + ".csv");
                guide = getGuide(type);
            } catch(IOException e) {
                e.printStackTrace();
            }
            if(type.equals(tableOptions[1])) {
                columnWidth = 75;
                tableHeight = 440;
            } else if(type.equals(tableOptions[2])) {
                columnWidth = 150;
                tableHeight = 525;
            } else if(type.equals(tableOptions[3])) {
                columnWidth = 200;
                tableHeight = 550;
            }
        }
        instructions.setText(guide);
        this.tableBox.getChildren().add(instructions);
        headers = csv.get(0);
        csv.remove(0);

        for(int i = 0; i < headers.length; i++) {
            TableColumn<String[], String> column = new TableColumn<String[], String>(headers[i]);
            // Source: https://stackoverflow.com/questions/20769723/populate-tableview-with-two-dimensional-array
            int colNo = i;
            // Research CallBack
            column.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            // Source: https://stackoverflow.com/questions/13455326/javafx-tableview-text-alignment\
            column.setStyle("-fx-alignment: CENTER;");
            column.setPrefWidth(columnWidth);
            column.setSortable(false);
            table.getColumns().add(column);
        }
        table.setItems(csv);
        table.setPrefHeight(tableHeight);
        table.setSelectionModel(null);
        this.tableBox.getChildren().add(table);
        this.pane.getChildren().addAll(this.tableBox);
    }

    private void createPeriodicTable() {
        periodicTableValues = new ArrayList<Element>();
        this.instructions = new Text();
        try {
            periodicTableValues = getPeriodicTableValues(tableOptions[0] + ".csv");
            this.instructions.setText(getGuide("Periodic Table"));
        } catch(IOException e) {
            System.out.println( tableOptions[0]+ ".csv could not be parsed.");
        }

        Pane periodicTable = new Pane();
        for(int i = 0; i < periodicTableValues.size(); i++) {
            Element currentElement = periodicTableValues.get(i);
            Button icon = currentElement.getIcon();
            periodicTable.getChildren().add(icon);
        }

        HBox elementRedirectBox = new HBox();
        Button seeDetails = new Button("See Details");
        seeDetails.setOnAction(event -> {
            createElementDisplay();            
        });
        Text errorMessage = new Text("");
        elementRedirectBox.getChildren().addAll(seeDetails, errorMessage);

        Button clearSelection = new Button("Clear Selection");
        clearSelection.setOnAction(event -> {
            clearSelection();
        });

        this.tableBox.getChildren().addAll(this.instructions, periodicTable, elementRedirectBox, clearSelection);
    }

    private void createElementDisplay() {
        TableView<Element> elementTable = new TableView<Element>();
        ObservableList<Element> clickedElements = FXCollections.observableArrayList();
        for(int i = 0; i < this.periodicTableValues.size(); i++) {
            Element currentElement = this.periodicTableValues.get(i);
            if(currentElement.gotClicked()) {
                clickedElements.add(currentElement);
            }
        } 
        Text errorMessage = (Text) ((HBox) this.tableBox.getChildren().get(3)).getChildren().get(1);
        if(clickedElements.size() == 0) {
            errorMessage.setText("Please select at least 1 element.");
            return;
        } else {
            this.tableBox.getChildren().clear();
        }
        for(int i = 0; i < this.elementTableProperties.length; i++) {
            String currentHeader = this.elementTableHeaders[i];
            String currentProperty = this.elementTableProperties[i];
            TableColumn<Element, String> column = new TableColumn<Element, String>(currentHeader);
            if(currentHeader.equals("Name")) {
                column.setPrefWidth(150);
            } else {
                column.setPrefWidth(30 + this.elementTableHeaders[i].length() * 10);
            }            
            column.setCellValueFactory(new PropertyValueFactory<Element, String>(currentProperty));
            column.setStyle("-fx-alignment: CENTER;");
            elementTable.getColumns().add(column);
        }
        elementTable.setItems(clickedElements);
        elementTable.setPrefWidth(1150);

        Button returnButton = new Button("Return to Periodic Table");
        returnButton.setOnAction(event -> {
            setTable(tableOptions[0]);
        });
        this.tableBox.getChildren().addAll(elementTable, returnButton);
    }

    private void clearSelection() {
        for(int i = 0; i < this.periodicTableValues.size(); i++) {
            this.periodicTableValues.get(i).resetClicked();
        }
    }

    private ArrayList<Element> getPeriodicTableValues(String fileName) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\pages\\CSV\\" + fileName;
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        ArrayList<Element> periodicTableValues = new ArrayList<Element>();
        String line;
        String[] metalSet = {"alkali metal", "alkaline earth metal", "transition metal", "post-transition metal", "metal"};
        String[] nonmetalSet = {"nonmetal", "noble gas", "halogen"};
        String[] metalloidSet = {"metalloid"};
        int counter = 0;
        while((line = br.readLine()) != null) {
            String[] values = line.split(",");
            for(int i = 0; i < values.length; i++) {
                values[i] = values[i].trim();
                if(values[i].equals("")) {
                    values[i] = "No Data";
                }
            }
            if(counter == 0) {
                this.elementTableProperties = new String[values.length + 1];
                for(int i = 0; i < values.length; i++) {
                    this.elementTableProperties[i] = values[i];
                }
                this.elementTableProperties[values.length] = "ionCharge";
                Element.setPropertyList(this.elementTableProperties);
            } else {
                String type = values[16];
                if(isPartOfSet(type, metalSet)) {
                    periodicTableValues.add(new Metal(values));
                } else if(isPartOfSet(type, nonmetalSet)) {
                    periodicTableValues.add(new Nonmetal(values));
                } else if(isPartOfSet(type, metalloidSet)) {
                    periodicTableValues.add(new Metalloid(values));
                } else {
                    periodicTableValues.add(new Filler(values));
                }
            }
            counter++;
        }
        br.close();
        return periodicTableValues;
    }

    private boolean isPartOfSet(String key, String[] set) {
        for(int i = 0; i < set.length; i++) {
            if(key.equals(set[i])) {
                return true;
            }
        }
        return false;
    }

    private ObservableList<String[]> readCSV(String fileName) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\pages\\CSV\\" + fileName;
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        ObservableList<String[]> fileValues = FXCollections.observableArrayList();
		String line;
		while ((line = br.readLine()) != null) {
            String[] row = line.split(",");
            for(int i = 0; i < row.length; i++) {
                if(!row[i].equals("")) {
                    row[i] = parse(row[i]);
                }
            }
            fileValues.add(row);
        }
        br.close();
        return fileValues;
    }

    // https://stackoverflow.com/questions/11145681/how-to-convert-a-string-with-unicode-encoding-to-a-string-of-letters
    // Strings can only turn unicode into chars in initialization
    // If stored in a text file, they need to be parsed explicitly
    // code cannot be ""
    private String parse(String code) {
        String[] parts = code.split("/");
        String decoded = "";
        for(int i = 0; i < parts.length; i++) {
            if(parts[i].charAt(0) == 'u') {
                parts[i] = parts[i].substring(1);
                // Decodes String containing hexadecimal value to an int
                decoded += (char) Integer.parseInt(parts[i], 16);
            } else {
                decoded += parts[i];
            }
        }
        return decoded;
    }
}