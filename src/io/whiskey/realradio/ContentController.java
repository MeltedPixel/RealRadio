/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.whiskey.realradio;

import com.jfoenix.controls.JFXButton;
import gov.nasa.xpc.XPlaneConnect;
import static io.whiskey.realradio.Main.contentStage;
import static io.whiskey.realradio.Main.isDebugRunning;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import org.ini4j.Ini;


/**
 *
 * @author MPHI14
 */
public class ContentController implements Initializable {
    public static Ini iReadINI;
    private String selectedComStack = "com1";
    private Thread xPlaneListener = null;
    private static int xPlaneListenerRefresh = 1000;
    private static int radioControllerRefresh = 500;
    public static final String sWorkingPath = GeneralClasses.getWorkingDirectory();
    public static String sIniFileName = "frequencies.ini";
    private final String com1ActiveDref = "sim/cockpit/radios/com1_freq_hz";
    private final String com2ActiveDref = "sim/cockpit/radios/com2_freq_hz";
    private final String com1StandbyDref = "sim/cockpit/radios/com1_stdby_freq_hz";
    private final String com2StandbyDref = "sim/cockpit/radios/com2_stdby_freq_hz";
    private final String batteryPowerDref = "sim/cockpit/electrical/battery_on";
    private final String com1ListenDref = "sim/cockpit2/radios/actuators/audio_selection_com1";
    private final String com2ListenDref = "sim/cockpit2/radios/actuators/audio_selection_com2";
    private static String com1ActiveFreq;
    private static String com2ActiveFreq;
    private static String com1StandbyFreq;
    private static String com2StandbyFreq;
    private static String com1LastFreq = "";
    private static String comLastFreq = "";
    private static String selectedActiveFreq;
    private static String selectedStandbyFreq;
    private static String com1LightState;
    private static String com2LightState;
    private static String batteryState;
    private static Boolean isListenerRunning = false;
    private static Thread audioThread = null;
    private static String AudioStream = "";
    
    
    @FXML
    private Label label_leftLCD;
    
    @FXML
    private Label lbl_connectionStatus;

    @FXML
    private Label label_rightLCD;
    
    @FXML
    private ImageView vhf1_light;

    @FXML
    private JFXButton btn_vhf1;

    @FXML
    private ImageView vhf2_light;

    @FXML
    private JFXButton btn_vhf2;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File iniCheck = new File(sWorkingPath + "/" + sIniFileName);
            
        if (!iniCheck.exists()) {
            // Debug Statement
            if (isDebugRunning) {
                System.out.println("Debug: " + "ini file could not be found in " + sWorkingPath);
            }

            List<String> iniData = Arrays.asList("[frequencies]", "122.810 = http://pulseedm.cdnstream1.com:8124/1373_128");

            Path iniFile = Paths.get(sWorkingPath + "/" + sIniFileName);

            try {
                Files.write(iniFile, iniData, Charset.forName("UTF-8"));
            } catch (IOException ex) {
                // Debug Statement
                if (isDebugRunning) {
                    System.out.println("Debug: " + "Unable to write ini file in " + sWorkingPath);
                }
            }

            if (isDebugRunning) {
                System.out.println("Debug: " + "Generating ini file in " + sWorkingPath);
            }

        } 

        iReadINI = new Ini();
        try {
            iReadINI.load(new FileReader(sWorkingPath + "/" + sIniFileName));
        } catch (IOException ex) {
            // Debug Statement
            if (isDebugRunning) {
                System.out.println("Debug: " + "ini file could not be found in " + sWorkingPath);
            }
        }
        
        vhf1_light.setVisible(true);
        vhf2_light.setVisible(false);
        
        setRightLCDPower(false);
        setLeftLCDPower(false);
        
        xPlaneListener();
        radioController();
    }

    // Button Minimize
    @FXML
    private void buttonActionMinimize(ActionEvent event) {
        // Debug Statement
        if (isDebugRunning) {
            System.out.println("Debug: " + "Application Minimized");
        }

        // Set the State of the PrimaryStage to Minimized (Iconified)
        contentStage.requestFocus();
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
    
    @FXML
    private void btn_vhf1ActionPerformed(ActionEvent event) {                                        
        if (vhf1_light.isVisible()) {
            vhf2_light.setVisible(false);
            vhf1_light.setVisible(true);
        } else {
            vhf1_light.setVisible(true);
            vhf2_light.setVisible(false);
            
            selectedComStack = "com1";
        }
        contentStage.requestFocus();
    }                                       

    @FXML
    private void btn_vhf2ActionPerformed(ActionEvent event) {                                         
        if (vhf2_light.isVisible()) {
            vhf2_light.setVisible(true);
            vhf1_light.setVisible(false);
        } else {
            vhf1_light.setVisible(false);
            vhf2_light.setVisible(true);
            
            selectedComStack = "com2";
        }
        contentStage.requestFocus();
    }
    
    private static void stopAudioStream() {
        if (audioThread == null) {
            // Debug Statement
            if (isDebugRunning) {
                System.out.println("Debug: " + "Can't stop a Null Thread");
            }
        } else {
            
            audioThread.interrupt();

        }
    }
    
    private void radioController() {
        Thread radioControllerThread = new Thread() {
            
            @Override
            public void run() {
                while (true) {
                    
                    if (isListenerRunning) {
                        
                        System.out.println("I am Connected");
                        
                        Platform.runLater(() -> {
                            setLeftLCDPower(true);
                            setRightLCDPower(true);
                        });
                        
                        try(XPlaneConnect messageTwo = new XPlaneConnect()) {
                            messageTwo.sendTEXT("", 10, 10);
                        } catch (IOException ex) {

                        }
                        
                        switch (selectedComStack) {
                            case "com1":
                                selectedActiveFreq = com1ActiveFreq;
                                selectedStandbyFreq = com1StandbyFreq;
                                break;

                            case "com2":
                                selectedActiveFreq = com2ActiveFreq;
                                selectedStandbyFreq = com2StandbyFreq;
                                break;
                        }
                        
                        if (comLastFreq.equalsIgnoreCase(selectedActiveFreq)) {
                            Platform.runLater(() -> {
                                setLeftLCD(selectedActiveFreq);
                                setRightLCD(selectedStandbyFreq);

                                if (batteryState.matches("1.0")) {
                                    setRightLCDPower(true);
                                    setLeftLCDPower(true);

                                    if (audioThread == null) {
                                       
                                        AudioStream = iReadINI.get("frequencies", selectedActiveFreq, String.class);

                                        try(XPlaneConnect messageOne = new XPlaneConnect()) {
                                            messageOne.sendTEXT("Real Radio - Connecting", 10, 10);
                                        } catch (IOException ex) {

                                        }
                                        
                                        audioThread = new AudioStreamCom1(AudioStream);
                                        audioThread.start();

                                    } else {
                                        if (audioThread.isAlive()) {
                                            
                                        } else {
                                         
                                            AudioStream = iReadINI.get("frequencies", selectedActiveFreq, String.class);

                                            try(XPlaneConnect messageTwo = new XPlaneConnect()) {
                                                messageTwo.sendTEXT("Real Radio - Connecting", 10, 10);
                                            } catch (IOException ex) {

                                            }
                                            
                                            audioThread = new AudioStreamCom1(AudioStream);
                                            audioThread.start();

                                        }
                                    }

                                } else {
                                    setRightLCDPower(false);
                                    setLeftLCDPower(false);

                                    stopAudioStream();
                                }
                            });
                        } else {
                            comLastFreq = selectedActiveFreq;
                            if (isDebugRunning) {
                                System.out.println("Stream Changed");
                            }

                            if (audioThread == null) {
                                // Debug Statement
                                if (isDebugRunning) {
                                    System.out.println("Debug: " + "Audio Thread is Null and Shouldn't be closed.");
                                }
                            } else {
                                if (audioThread.isAlive()) {
                                    stopAudioStream();
                                }
                            }
                        }
 
                    } else {
                        System.out.println("Not Connected Yet");
                        
                        Platform.runLater(() -> {
                            setRightLCDPower(false);
                            setLeftLCDPower(false);
                        });
                    }
                    
                    try {
                        Thread.sleep(radioControllerRefresh);
                    }
                    catch (InterruptedException ex) {}
                }
            }
        };
        radioControllerThread.start();
    }
    
    private void xPlaneListener() {
        xPlaneListener = new Thread() {

            @Override
            public void run() {
                while (true) {
                    try (XPlaneConnect xpc = new XPlaneConnect()) {

                        float[] com1ActiveDrefResult = xpc.getDREF(com1ActiveDref);
                        float[] com2ActiveDrefResult = xpc.getDREF(com2ActiveDref);
                        float[] com1StandbyDrefResult = xpc.getDREF(com1StandbyDref);
                        float[] com2StandbyDrefResult = xpc.getDREF(com2StandbyDref);
                        float[] com1ListenDrefResult = xpc.getDREF(com1ListenDref);
                        float[] com2ListenDrefResult = xpc.getDREF(com2ListenDref);
                        float[] batteryPowerDrefResult = xpc.getDREF(batteryPowerDref);

                        com1ActiveFreq = String.valueOf(com1ActiveDrefResult[0]);
                        com2ActiveFreq = String.valueOf(com2ActiveDrefResult[0]);
                        com1StandbyFreq = String.valueOf(com1StandbyDrefResult[0]);
                        com2StandbyFreq = String.valueOf(com2StandbyDrefResult[0]);
                        com1LightState = String.valueOf(com1ListenDrefResult[0]);
                        com2LightState = String.valueOf(com2ListenDrefResult[0]);
                        batteryState = String.valueOf(batteryPowerDrefResult[0]);

                        com1ActiveFreq = GeneralClasses.trimFreqResult(com1ActiveFreq);
                        com1StandbyFreq = GeneralClasses.trimFreqResult(com1StandbyFreq);
                        com2ActiveFreq = GeneralClasses.trimFreqResult(com2ActiveFreq);
                        com2StandbyFreq = GeneralClasses.trimFreqResult(com2StandbyFreq);

                        //System.out.println("Debug: " + "Com1Active = " + com1ActiveFreq);
                        //System.out.println("Debug: " + "Com1Standby = " + com1StandbyFreq);
                        //System.out.println("Debug: " + "Com1Listen = " + com1ListenDrefResult[0]);
                        //System.out.println("Debug: " + "Com2Active = " + com2ActiveFreq);
                        //System.out.println("Debug: " + "Com2Standby = " + com2StandbyFreq);
                        //System.out.println("Debug: " + "Com2Listen = " + com2ListenDrefResult[0]);
                        //System.out.println("Debug: " + "Battery Power = " + batteryState);
                        //System.out.println("Debug: " + "com1LastFreq = " + com1LastFreq);

                        Platform.runLater(() -> {
                            setConnectionStatus("Connected");
                        });
                        
                        isListenerRunning = true;

                        System.out.println("Running");

                    }
                    catch(IOException ex) {
                        Platform.runLater(() -> {
                            setConnectionStatus("Not Connected");
                        });
                        
                        isListenerRunning = false;
                        
                        System.out.println("Error:");
                        System.out.println(ex.getMessage());
                    }
                    
                    try {
                        Thread.sleep(xPlaneListenerRefresh);
                    }
                    catch (InterruptedException ex) {}
                    
                }
            }
        };
        xPlaneListener.start();
    }

    public Label getLeftLCD() {
        return label_leftLCD;
    }

    public void setLeftLCD(String frequency) {
        this.label_leftLCD.setText(frequency);
    }
    
    public void setLeftLCDPower(Boolean state) {
        label_leftLCD.setVisible(state);
    }
    
    public void setRightLCDPower(Boolean state) {
        label_rightLCD.setVisible(state);
    }

    public Label getRightLCD() {
        return label_rightLCD;
    }

    public void setRightLCD(String frequency) {
        this.label_rightLCD.setText(frequency);
    }
    
    public void setConnectionStatus(String status) {
        switch (status.toLowerCase()){
            case "connected":
                lbl_connectionStatus.setText("Connected");
                lbl_connectionStatus.setTextFill(Color.web("#00FF00"));
                break;
            case "not connected":
                lbl_connectionStatus.setText("Not Connected");
                lbl_connectionStatus.setTextFill(Color.web("#FFFF00"));
                break;
        }
    }
    
    
}
