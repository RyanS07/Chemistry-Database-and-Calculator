package pages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/* The methodology of the Page class is based on HTML, where each new
 * page has its own file. The stage, located in Main.java, switches 
 * between each Page's scene to change what is displayed in the GUI. 
 */
public abstract class Page {
    // Variables are protected so subclasses have access but files outside the pages package do not.
    // Lowers the number of accessor method calls the subclasses need to make (improves readability).
    protected Pane pane;
    protected Scene scene;

    // Static variables all pages use. See goTo() on Lines 63-65.
    // setStage() and setPageMap() must be called before any subclass method (including constructors).
    private static Stage stage;
    private static HashMap<String, Page> pageMap;

    // Final variables for the corresponding component.
    // Made protected instead of private to lower the number of accessor method calls in subclasses 
    // and improve readability. Made static since these attributes are shared by all instances of 
    // subclasses. 
    protected final static double width = 1250;
    protected final static double height = 950;
    protected final static Insets boxPadding = new Insets(20,20,20,20);
    private final static double buttonWidth = 100;
    private final static double buttonHeight = 50;
        
    /* Every subclass of the Page class has to redirect to another page.
     * Made protected since subclasses need to provide implementation,
     * but classes outside the package do not need access to this. 
     */
    protected abstract void setRedirects();

    /* All pages of the app have a pane and a scene, therefore all subclasses
     * will need to create a pane and scene. 
     */
    public Page() {
        this.pane = new Pane();
        this.scene = new Scene(this.pane, Page.width, Page.height);
    }

    /* Description:
     *  - See each Page subclass's setRedirects() for example
     *  - Method created to aid code readability and reuse
     *  - Redirects to the page with the pageMap key name
     * 
     * Precondition:
     *  - name must be one of the pageMap keys
     */
    protected void goTo(String name) {
        Page.stage.setScene(pageMap.get(name).getScene());
    }

    // Scene accessor for Main.java (hence public)
    public Scene getScene() {
        return this.scene;
    }

    /* Description:
     *  - All buttons have the same dimensions
     *  - Method is used to aid code readibility and reuse
     *  - Returns a Button instance with the coresponding name text
     */
    protected Button setButton(String text) {
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
    protected Button setBackButton() {
        Button back = new Button("<-");
        back.setPrefWidth(buttonWidth);
        back.setPrefHeight(buttonHeight);
        back.setLayoutX(10);
        back.setLayoutY(10);
        return back;
    }
    
    // Sets static variable Page.stage to stage
    // To be called by Main.java (hence public)
    public static void setStage(Stage stage) {
        Page.stage = stage;
    }
    // Sets static variable Page.pageMap to pageMap
    // To be called by Main.java (hence public)
    public static void setPageMap(HashMap<String, Page> pageMap) {
        Page.pageMap = pageMap;
    }
    
    /* Returns the String stored in a text file under the name type. The 
     * parameter is called type because it refers to which type of 
     * table or calculator is being displayed. See TablePage.java 
     * (Lines ___) or CalculatorPage.java (Lines ___) for an example. 
     * 
     * This method is in Page.java because originally there 
     * was going to be more text to display in HomePage.java, but due to
     * there being no need, HomePage.java does not call getGuide(). 
     * However, since TablePage.java and CalculatorPage.java are already
     * lengthy on their own, the method stayed here. 
     */
    protected String getGuide(String type) throws IOException {
        /* See TablePage.java (Lines ___) or CalculatorPage.java (Lines ___)
         * for context. 
         * 
         * Removes any '\'' characters in type. When getGuide() is called, 
         * the title of the table/calculator is passed in. For grammatical
         * reasons, these titles have '\'' in them, which creates problems
         * accessing a file. To remove this problem, the '\'' is removed.
         */
        int index = type.indexOf('\'');
        if(index != -1) {
            type = type.substring(0, index) + type.substring(index+1);
        }
        String filePath = System.getProperty("user.dir") + "\\pages\\Instructions\\" + type + ".txt";
        File file = new File(filePath);
        // Creates a BufferedReader to read from the text file.
        BufferedReader br = new BufferedReader(new FileReader(file));
        String fileValues = "";
		String line;
		while ((line = br.readLine()) != null) {
            fileValues += line + "\n";
        }
        br.close();
        return fileValues;
    }
}