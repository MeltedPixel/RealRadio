/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.whiskey.realradio;

import static io.whiskey.realradio.Main.contentStage;
import static io.whiskey.realradio.Main.isDebugRunning;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


/**
 *
 * @author MPHI14
 */
public class ContentController implements Initializable {

    @FXML
    private Label label_leftLCD;

    @FXML
    private Label label_rightLCD;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLeftLCD("121.90");
        setRightLCD("000.00");
    }  
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws MalformedURLException, IOException, JavaLayerException {
        startAudioStream("http://d.liveatc.net/kpdx2");
    }
    
    // Button Minimize
    @FXML
    private void buttonActionMinimize(ActionEvent event) {
        // Debug Statement
        if (isDebugRunning) {
            System.out.println("Debug: " + "Application Minimized");
        }

        // Set the State of the PrimaryStage to Minimized (Iconified)
        contentStage.setIconified(true);
    }

    // Button Close
    @FXML
    private void buttonActionClose(ActionEvent event) {
        // Debug Statement
        if (isDebugRunning) {
            System.out.println("Debug: " + "Application Closed");
        }

        // Close the Application
        System.exit(0);

    }

    public void startAudioStream(String audioUrl) {
        Thread audioThread = new Thread() {
            
            @Override
            public void run() {
                InputStream input = null;
                try {
                    input = new URL(audioUrl).openStream();
                    Player player = new Player(input);
                    player.play();
                    
                    Platform.runLater(() -> {
                        
                    });
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ContentController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException | JavaLayerException ex) {
                    Logger.getLogger(ContentController.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        input.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ContentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        audioThread.start();
    }

    public Label getLeftLCD() {
        return label_leftLCD;
    }

    public void setLeftLCD(String frequency) {
        this.label_leftLCD.setText(frequency);
    }

    public Label getRightLCD() {
        return label_rightLCD;
    }

    public void setRightLCD(String frequency) {
        this.label_rightLCD.setText(frequency);
    }
    
    
}
