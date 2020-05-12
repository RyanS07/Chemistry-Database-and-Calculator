package classes.pages;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class Page {
    protected Pane pane;
    protected Scene scene;
    protected String appTitle = "Balkulator";

    protected static Stage stage;
    protected static HashMap<String, Page> pageMap;
    protected final static double pageWidth = 1250;
    protected final static double pageHeight = 900;
    protected final static double buttonWidth = 100;
    protected final static double buttonHeight = 50;
        
    public abstract void setRedirects();

    public void goTo(String name) {
        Page.stage.setScene(pageMap.get(name).getScene());
    }

    public Scene getScene() {
        return this.scene;
    }
    public Pane getPane() {
        return this.pane;
    }

    public Button setButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(buttonWidth);
        button.setPrefHeight(buttonHeight);
        return button;
    }
    
    public Button setBackButton() {
        Button back = new Button("<-");
        back.setPrefWidth(buttonWidth);
        back.setPrefHeight(buttonHeight);
        back.setLayoutX(10);
        back.setLayoutY(10);
        return back;
    }
    

    public static void setStage(Stage stage) {
        Page.stage = stage;
    }
    public static void setPageMap(HashMap<String, Page> pageMap) {
        Page.pageMap = pageMap;
    }
}