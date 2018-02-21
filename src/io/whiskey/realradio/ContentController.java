/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.whiskey.realradio;

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
import javafx.scene.paint.Color;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


/**
 *
 * @author MPHI14
 */
public class ContentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws MalformedURLException, IOException, JavaLayerException {

        Thread t1 = new Thread("Audio Thread");

        if (t1.isAlive()) {
            System.out.format("%s is alive.%n", t1.getName());
        } else {
            System.out.format("%s is not alive.%n", t1.getName());
            
            InputStream input = null;
            try {
                input = new URL("http://d.liveatc.net/kpdx2").openStream();
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
        t1.start();
        
        
        /*
        Thread audioThread = new Thread() {
            
            @Override
            public void run() {
                InputStream input = null;
                try {
                    input = new URL("http://d.liveatc.net/kpdx2").openStream();
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
        */
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }   
}
