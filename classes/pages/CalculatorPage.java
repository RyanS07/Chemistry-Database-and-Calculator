package classes.pages;

import java.util.Arrays;
import java.util.HashMap;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;

public class CalculatorPage extends Page {
    private Button back;
    private String[][] headerList = { { "Ideal Gas Law", "P", "V", "n", "T" },
            { "Boyle's Law", "P1", "V1", "P2", "V2" }, { "Charles' Law", "V1", "T1", "V2", "T2" },
            { "Gay-Lussac's Law", "P1", "T1", "P2", "T2" }, { "Avogadro's Hypthesis", "V1", "n1", "V2", "n2" },
            { "Concentration", "C1", "V1", "C2", "V2" } };
    private HashMap<String, String[]> headers = new HashMap<String, String[]>();
    // fvi --> Four Variable Input
    private TextField[] fvi = new TextField[4];
    private HBox fviBox;
    private Text[] fviHeader = new Text[4];
    private HBox fviHeaderBox;

    private VBox fviCalculator;

    public CalculatorPage() {
        this.pane = new Pane();
        this.scene = new Scene(this.pane, Page.width, Page.height);

        this.back = setBackButton();
        this.pane.getChildren().add(this.back);

        initHeaders();
        initFVI();

        this.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.ENTER) {

                }
            }
        });

        setRedirects();
    }

    public void setRedirects() {
        this.back.setOnAction(event -> {
            goTo("Tables");
        });
    }

    public void initHeaders() {
        for (int i = 0; i < headerList.length; i++) {
            String[] subArray = Arrays.copyOfRange(headerList[i], 1, headerList.length);
            headers.put(headerList[i][0], subArray);
        }
    }

    public void initFVI() {
        fviBox = new HBox(20);
        fviHeaderBox = new HBox(170);
        fviCalculator = new VBox();
        fviCalculator.setPadding(new Insets(20, 20, 20, 20));
        for(int i = 0; i < this.fvi.length; i++) {
            this.fvi[i] = new TextField();
            this.fvi[i].setPrefColumnCount(10);
            fviBox.getChildren().add(this.fvi[i]);

            this.fviHeader[i] = new Text(headerList[0][i+1]);
            fviHeaderBox.getChildren().add(this.fviHeader[i]);
        }             
        fviCalculator.setLayoutY(100);
        fviCalculator.getChildren().addAll(fviHeaderBox, fviBox);
        this.pane.getChildren().add(fviCalculator);
    }
}