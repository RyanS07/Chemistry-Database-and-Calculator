package classes.pages;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class Page {
    // Instance variabls of each Page
    // Variables are protected so subclasses have access but files outside the pages package do not
    protected Pane pane;
    protected Scene scene;

    // Static variables all pages use
    // setStage() and setPageMap() must be called before any subclass method
    protected static Stage stage;
    protected static HashMap<String, Page> pageMap;

    // Final variables for the corresponding component
    protected final static double width = 1250;
    protected final static double height = 900;
    protected final static double buttonWidth = 100;
    protected final static double buttonHeight = 50;
        
    /* Every subclass of the Page class has to redirect to another page.
     * The methodology the Page class is based on HTML, where each new
     * page has its own file (in this case its own class). 
     */
    protected abstract void setRedirects();

    /* Description:
     *  - See each Page subclass's setRedirects() for example
     *  - Method created to aid code readability and reuse
     *  - Redirects to the page with the pageMap key name
     * 
     * Precondition:
     *  - name must be one of the pageMap keys
     */
    public void goTo(String name) {
        Page.stage.setScene(pageMap.get(name).getScene());
    }

    public Scene getScene() {
        return this.scene;
    }
    public Pane getPane() {
        return this.pane;
    }

    /* Description:
     *  - All buttons have the same dimensions
     *  - Method is used to aid code readibility and reuse
     *  - Returns a Button instance with the coresponding name text
     */
    public Button setButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(buttonWidth);
        button.setPrefHeight(buttonHeight);
        return button;
    }
    
    /* Description:
     *  - Similar purpose to setButton()
     *  - Pages have back/return buttons to return to the previous page
     *  - All back buttons have the same properties, so a method is used
     *    to aid code readability and reuse
     */
    public Button setBackButton() {
        Button back = new Button("<-");
        back.setPrefWidth(buttonWidth);
        back.setPrefHeight(buttonHeight);
        back.setLayoutX(10);
        back.setLayoutY(10);
        return back;
    }
    
    // Sets static variable Page.stage to stage
    public static void setStage(Stage stage) {
        Page.stage = stage;
    }
    // Sets static variable Page.pageMap to pageMap
    public static void setPageMap(HashMap<String, Page> pageMap) {
        Page.pageMap = pageMap;
    }
}