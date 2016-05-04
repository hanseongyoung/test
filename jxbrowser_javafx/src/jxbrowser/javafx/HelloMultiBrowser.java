package jxbrowser.javafx;

import java.util.ArrayList;
import java.util.List;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserCore;
import com.teamdev.jxbrowser.chromium.internal.Environment;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloMultiBrowser extends Application {
    //
    private List<Browser> browsers = new ArrayList<>();
    
    @Override
    public void init() throws Exception {
        // On Mac OS X Chromium engine must be initialized in non-UI thread.
        if (Environment.isMac()) {
            BrowserCore.initialize();
        }
    }
    
    public HBox createHeaderBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5, 5, 5, 5));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        
        Label label = new Label("http://");
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
        hbox.getChildren().addAll(label, textField);
        return hbox;
    }
    
    private void loadURL(String url) {
        for (Browser browser : browsers) {
            browser.loadURL("http://"+url);
        }
    }
    
    HBox createBrowserBox2() {
        HBox hbox = new HBox();
        BrowserView bv1 = createBrowserView();
        BrowserView bv2 = createBrowserView();
        hbox.getChildren().add(bv1);
        hbox.getChildren().add(bv2);
        HBox.setHgrow(bv1, Priority.ALWAYS);
        HBox.setHgrow(bv2, Priority.ALWAYS);
        return hbox;
    }
    
    HBox createBrowserBox4() {
        HBox hbox = new HBox();
        BrowserView bv1 = createBrowserView();
        BrowserView bv2 = createBrowserView();
        BrowserView bv3 = createBrowserView();
        BrowserView bv4 = createBrowserView();
        hbox.getChildren().add(bv1);
        hbox.getChildren().add(bv2);
        hbox.getChildren().add(bv3);
        hbox.getChildren().add(bv4);
        HBox.setHgrow(bv1, Priority.ALWAYS);
        HBox.setHgrow(bv2, Priority.ALWAYS);
        HBox.setHgrow(bv3, Priority.ALWAYS);
        HBox.setHgrow(bv4, Priority.ALWAYS);
        return hbox;
    }
    
    VBox createBrowserBox8() {
        VBox vbox = new VBox();
        HBox row1 = createBrowserBox4();
        HBox row2 = createBrowserBox4();
        vbox.getChildren().add(row1);
        vbox.getChildren().add(row2);
        VBox.setVgrow(row1, Priority.ALWAYS);
        VBox.setVgrow(row2, Priority.ALWAYS);
        return vbox;
    }
    
    private BrowserView createBrowserView() {
        Browser browser = new Browser();
        browsers.add(browser);
        BrowserView browserView = new BrowserView(browser);
        browser.loadHTML("<html><body><h1>Hello World!</h1></body></html>");
        return browserView;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane border = new BorderPane();
        HBox headerBox = createHeaderBox();
        Node browserBox = createBrowserBox8();
        border.setTop(headerBox);
        border.setCenter(browserBox);
        
        Scene scene = new Scene(border, 800, 600);
        primaryStage.setTitle("JxBrowser :: JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
