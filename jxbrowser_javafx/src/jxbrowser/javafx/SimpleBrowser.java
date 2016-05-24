package jxbrowser.javafx;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserCore;
import com.teamdev.jxbrowser.chromium.internal.Environment;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class SimpleBrowser extends Application {
    private Browser browser;
    
    @Override
    public void init() throws Exception {
        if (Environment.isMac()) {
            BrowserCore.initialize();
        }
    }
    
    @Override
    public void stop() throws Exception {
        this.browser.stop();
        this.browser.dispose();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane border = new BorderPane();
        HBox headerBox = createHeaderBox();
        Node browserBox = createBrowserBox();
        border.setTop(headerBox);
        border.setCenter(browserBox);
        
        Scene scene = new Scene(border, 1024, 768);
        primaryStage.setTitle("JxBrowser :: Simple");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //primaryStage.setFullScreen(true);
    }
    
    private HBox createHeaderBox() {
        HBox hbox = new HBox();
        TextField textField = new TextField();
        HBox.setHgrow(textField, Priority.ALWAYS);
        textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    loadURL(textField.getText());
                }
            }

        });
        hbox.getChildren().add(textField);
        return hbox;
    }

    private Node createBrowserBox() {
        return createBrowserView();
    }
    
    private BrowserView createBrowserView() {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);
        browser.loadHTML("<html><body><h1>Simple Browser</h1></body></html>");
        this.browser = browser;
        return browserView;
    }
    
    private void loadURL(String url) {
        this.browser.loadURL(url);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
