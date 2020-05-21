package classes.pages;

import classes.pages.periodictable.Element;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class TablePage extends Page {
    private Button back;
    private Button toCalculators;

    ArrayList<Element> periodicTableValues = new ArrayList<Element>();

    // https://stackoverflow.com/questions/11145681/how-to-convert-a-string-with-unicode-encoding-to-a-string-of-letters
    // https://en.wikipedia.org/wiki/Unicode_subscripts_and_superscripts
    // Learning about Unicode:
    // https://docs.oracle.com/javase/tutorial/i18n/text/unicode.html
    private static String[] solubilityHeaders = { "", "C\u2082H\u2083O\u2082\u207B", "Br\u00B2\u207B",
            "CO\u2083\u00B2\u207B", "ClO\u2082\u207B", "Cl\u207B", "OH\u207B", "I\u207B", "NO\u2083\u207B",
            "O\u00B2\u207B", "ClO\u2084\u207B", "PO\u2084\u00B3\u207B", "SO\u2084\u00B2\u207B",
            "SO\u2083\u00B2\u207B" };
    private TableView<String[]> solubilityTable = new TableView<String[]>();
    private String solubilityFileName = "SolubilityTable.csv";

    private static String[] activityHeaders = {"Element", "Displaces H\u207A in"};
    private TableView<String[]> activitySeries = new TableView<String[]>();
    private String activityFileName = "ActivitySeries.csv";

    private static String[] vapourPressureHeaders = {"Temperature (K)", "Pressure (kPa)"};
    private TableView<String[]> vapourPressureTable = new TableView<String[]>();
    private String vapourPressureFileName = "VapourPressure.csv";

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
        setTable("Periodic Table");
        

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

        // Add Stoic Calc Menu Here
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

        if(this.tableType.equals("Periodic Table")) {
            setPeriodicTable();
        } else {
            createTable(this.tableType);
        }

        this.pane.getChildren().addAll(this.tableBox);
    }

    private void setPeriodicTable() {

    }
    
    // https://stackoverflow.com/questions/11145681/how-to-convert-a-string-with-unicode-encoding-to-a-string-of-letters
    // Strings can only turn unicode into chars in initialization
    // If stored in a text file, they need to be parsed explicitly
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

    private void createTable(String type) {
        ObservableList<String[]> csv = FXCollections.observableArrayList();
        TableView<String[]> table = new TableView<String[]>();
        String[] headers = new String[1];
        int columnWidth = 0;
        int tableHeight = 0;

        if(type.equals("Solubility Table")) {
            try {
                csv = readCSV(this.solubilityFileName);
            } catch(IOException e) {
                System.out.println(this.solubilityFileName + " could not be found.");
            }
            headers = solubilityHeaders;
            columnWidth = 75;
            tableHeight = 440;
        } else if(type.equals("Activity Series")) {
            try {
                csv = readCSV(this.activityFileName);
            } catch(IOException e) {
                System.out.println(this.activityFileName + " could not be found.");
            }
            headers = activityHeaders;
            columnWidth = 150;
            tableHeight = 525;
        } else if(type.equals("Vapour Pressure Table")) {
            try {
                csv = readCSV(this.vapourPressureFileName);
            } catch(IOException e) {
                System.out.println(this.vapourPressureFileName + " could not be found.");
            }
            headers = vapourPressureHeaders;
            columnWidth = 200;
            tableHeight = 500;
        }

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

    private ObservableList<String[]> readCSV(String fileName) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\classes\\pages\\CSV\\" + fileName;
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        ObservableList<String[]> fileValues = FXCollections.observableArrayList();
		String line;
		while ((line = br.readLine()) != null) {
            String[] row = line.split(",");
            for(int i = 0; i < row.length; i++) {
                row[i] = parse(row[i]);
            }
            fileValues.add(row);
        }
        br.close();
        return fileValues;
    }
}