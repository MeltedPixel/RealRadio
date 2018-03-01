/*
* This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 
* International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ 
* or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */
package io.whiskey.realradio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author MPHI14
 */
public class Main extends Application {
    public static final boolean isDebugRunning = true;
    public static final String applicationName = "Real Radio";
    public static Stage contentStage;
    private double xOffset = 0;
    private double yOffset = 0;
    
    @Override
    public void start(Stage mainStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Content.fxml"));
        
        Scene rootScene = new Scene(root);
        
        rootScene.setFill(Color.TRANSPARENT);
        mainStage.initStyle(StageStyle.TRANSPARENT);
        mainStage.setScene(rootScene);
        mainStage.show();
        mainStage.centerOnScreen();
        mainStage.setTitle(applicationName);
        mainStage.getIcons().add(new Image("io/whiskey/realradio/images/logoheadset.png"));
        
        contentStage = mainStage;
        
        root.requestFocus();
        
        rootScene.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        
        rootScene.setOnMouseDragged((MouseEvent event) -> {
            mainStage.setX(event.getScreenX() - xOffset);
            mainStage.setY(event.getScreenY() - yOffset);
        });
        
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        mainStage.setX((primScreenBounds.getWidth() - mainStage.getWidth()) / 2); 
        mainStage.setY((primScreenBounds.getHeight() - mainStage.getHeight()) / 2);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
