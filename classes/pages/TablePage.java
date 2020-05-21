package classes.pages;

import classes.pages.periodictable.Element;
import classes.pages.periodictable.Filler;
import classes.pages.periodictable.Metal;
import classes.pages.periodictable.Metalloid;
import classes.pages.periodictable.Nonmetal;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
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

    private MenuBar tableMenu;
    private Text tableTitle;
    private VBox tableBox;
    private String tableType;
    private String[] tableOptions = {"Periodic Table", "Solubility Table", "Activity Series", "Vapour Pressure Table"};

    public TablePage() {
        this.pane = new Pane();
        this.scene = new Scene(this.pane, Page.width, Page.height);

        this.back = setBackButton();
        this.pane.getChildren().add(this.back);

        this.toCalculators = setButton("Calculators");
        this.toCalculators.setLayoutX(Page.width - this.toCalculators.getPrefWidth() - 10);
        this.toCalculators.setLayoutY(Page.height - this.toCalculators.getPrefHeight() - 10);
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
            goTo("Calculators");
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
        this.tableBox.setLayoutY(this.tableMenu.getLayoutY() + 100);
        this.tableBox.setLayoutX(20);
        this.tableBox.getChildren().add(this.tableTitle);

        createTable(this.tableType);

        this.pane.getChildren().addAll(this.tableBox);
    }

    
    
    private void createTable(String type) {
        ObservableList<String[]> csv = FXCollections.observableArrayList();
        TableView<String[]> table = new TableView<String[]>();
        String[] headers = new String[1];
        int columnWidth = 0;
        int tableHeight = 0;

        if(type.equals(tableOptions[0])) {
            createPeriodicTable();
            return;
        } else {
            try {
                csv = readCSV(type + ".csv");
            } catch(IOException e) {
                System.out.println(type + ".csv could not be read.");
            }
            if(type.equals(tableOptions[1])) {
                columnWidth = 75;
                tableHeight = 440;
            } else if(type.equals(tableOptions[2])) {
                columnWidth = 150;
                tableHeight = 525;
            } else if(type.equals(tableOptions[3])) {
                columnWidth = 200;
                tableHeight = 500;
            }
        }
        headers = csv.get(0);
        csv.remove(0);

        for(int i = 0; i < headers.length; i++) {
            TableColumn<String[], String> column = new TableColumn<String[], String>(headers[i]);
            // Source: https://stackoverflow.com/questions/20769723/populate-tableview-with-two-dimensional-array
            int colNo = i;
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
    }

    private void createPeriodicTable() {
        ArrayList<Element> periodicTableValues = new ArrayList<Element>();
        try {
            periodicTableValues = readCSV(tableOptions[0] + ".csv", "Periodic Table");
        } catch(IOException e) {
            System.out.println( tableOptions[0]+ ".csv could not be parsed.");
        }

        Pane periodicTable = new Pane();
        for(int i = 0; i < periodicTableValues.size(); i++) {
            Element currentElement = periodicTableValues.get(i);
            currentElement.setIcon();
            Button icon = currentElement.getIcon();
            icon.setOnAction(event -> {
                
            });
            periodicTable.getChildren().add(icon);
        }

        this.tableBox.getChildren().add(periodicTable);
    }

    private ArrayList<Element> readCSV(String fileName, String key) throws IOException {
        if(key.equals("Periodic Table")) {
            String filePath = System.getProperty("user.dir") + "\\classes\\pages\\CSV\\" + fileName;
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            ArrayList<Element> periodicTableValues = new ArrayList<Element>();
            String line;
            while((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if(values[12].equals("yes")) {
                    periodicTableValues.add(new Metal(values));
                } else if(values[13].equals("yes")) {
                    periodicTableValues.add(new Nonmetal(values));
                } else if(values[14].equals("yes")) {
                    periodicTableValues.add(new Metalloid(values));
                } else {
                    periodicTableValues.add(new Filler(values));
                }
            }
            br.close();
            periodicTableValues.remove(0);
            return periodicTableValues;
        } else {
            return new ArrayList<Element>();
        }
    }

    private ObservableList<String[]> readCSV(String fileName) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\classes\\pages\\CSV\\" + fileName;
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