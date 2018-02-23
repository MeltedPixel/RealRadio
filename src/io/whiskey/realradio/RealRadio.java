/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.whiskey.realradio;

import gov.nasa.xpc.XPlaneConnect;
import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javazoom.jl.decoder.JavaLayerException;


/**
 *
 * @author MPHI14
 */
public class RealRadio extends javax.swing.JFrame {
    private static Thread audioThread = null;
    private static final String currentStream = "http://d.liveatc.net/kpdx2";
    private static String com1ActiveFreq;
    private static String com1StandbyFreq;
    private static String com1LightState;
    private static Boolean isBatteryOn;
    private static String batteryState;
    private static int refreshTime = 1000;
    public static final boolean isDebugRunning = true;
    public static final String applicationName = "Real Radio";
    
    
    /**
     * Creates new form Updater
     */
    public RealRadio() {
        initComponents();
        
        setLocationRelativeTo(null);
        setBackground(new Color(0, 0, 0, 0));
        setTitle(applicationName);
        
        URL imageurl = getClass().getResource("images/logoheadset.png");
        ImageIcon img = new ImageIcon(imageurl);
        setIconImage(img.getImage());

        DragListener drag = new DragListener();
        addMouseListener(drag);
        addMouseMotionListener(drag);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        minimizeButton = new javax.swing.JButton();
        optionsButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        realRadioLogo = new javax.swing.JLabel();
        RealRadio = new javax.swing.JLabel();
        lbl_standbyFreq = new javax.swing.JLabel();
        lbl_activeFreq = new javax.swing.JLabel();
        vhf1_light = new javax.swing.JLabel();
        updaterBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setIconImages(getIconImages());
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(404, 198));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        minimizeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/whiskey/realradio/images/minimize.png"))); // NOI18N
        minimizeButton.setBorderPainted(false);
        minimizeButton.setContentAreaFilled(false);
        minimizeButton.setMaximumSize(new java.awt.Dimension(22, 23));
        minimizeButton.setMinimumSize(new java.awt.Dimension(22, 23));
        minimizeButton.setPreferredSize(new java.awt.Dimension(22, 23));
        minimizeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minimizeButtonActionPerformed(evt);
            }
        });
        getContentPane().add(minimizeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 2, 18, 19));

        optionsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/whiskey/realradio/images/options.png"))); // NOI18N
        optionsButton.setBorderPainted(false);
        optionsButton.setContentAreaFilled(false);
        optionsButton.setMaximumSize(new java.awt.Dimension(22, 23));
        optionsButton.setMinimumSize(new java.awt.Dimension(22, 23));
        optionsButton.setPreferredSize(new java.awt.Dimension(22, 23));
        getContentPane().add(optionsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 2, 18, 19));

        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/whiskey/realradio/images/close.png"))); // NOI18N
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setMaximumSize(new java.awt.Dimension(22, 23));
        closeButton.setMinimumSize(new java.awt.Dimension(22, 23));
        closeButton.setPreferredSize(new java.awt.Dimension(22, 23));
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        getContentPane().add(closeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(374, 2, 18, 19));

        realRadioLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/whiskey/realradio/images/logo18px.png"))); // NOI18N
        getContentPane().add(realRadioLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 2, -1, -1));

        RealRadio.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        RealRadio.setForeground(new java.awt.Color(255, 255, 255));
        RealRadio.setText("Real Radio");
        getContentPane().add(RealRadio, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 2, -1, -1));

        lbl_standbyFreq.setFont(new java.awt.Font("Digital-7", 0, 30)); // NOI18N
        lbl_standbyFreq.setForeground(new java.awt.Color(255, 255, 255));
        lbl_standbyFreq.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_standbyFreq.setText("112.80");
        getContentPane().add(lbl_standbyFreq, new org.netbeans.lib.awtextra.AbsoluteConstraints(238, 57, 110, 30));

        lbl_activeFreq.setFont(new java.awt.Font("Digital-7", 0, 30)); // NOI18N
        lbl_activeFreq.setForeground(new java.awt.Color(255, 255, 255));
        lbl_activeFreq.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_activeFreq.setText("112.80");
        getContentPane().add(lbl_activeFreq, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 57, 110, 30));

        vhf1_light.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/whiskey/realradio/images/light.png"))); // NOI18N
        getContentPane().add(vhf1_light, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 118, -1, -1));

        updaterBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/whiskey/realradio/images/uiskin.png"))); // NOI18N
        getContentPane().add(updaterBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 404, 198));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_closeButtonActionPerformed

    private void minimizeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minimizeButtonActionPerformed
        setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_minimizeButtonActionPerformed

    private void audioTestButton(ActionEvent event) throws MalformedURLException, IOException, JavaLayerException {
        
        if (audioThread == null) {
            // Debug Statement
            if (isDebugRunning) {
                System.out.println("Debug: " + "Audio Thread is Null - Probably first boot.");
            }
            audioThread = new AudioStream(currentStream);
            audioThread.start();
            
        } else {
            if (audioThread.isAlive()) {
                // Debug Statement
                if (isDebugRunning) {
                    System.out.println("Debug: " + "Audio Thread is Alive - Can't start another thread!");
                }
            } else {
                // Debug Statement
                if (isDebugRunning) {
                    System.out.println("Debug: " + "Audio Thread not started");
                }
                audioThread = new AudioStream(currentStream);
                audioThread.start();
                
            }
        }
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
    
    private static void createListenerThread() {
        Thread xPlaneListener = new Thread() {
            
            @Override
            public void run() {
                try (XPlaneConnect xpc = new XPlaneConnect()) {
                    while(true) {

                        String com1ActiveDref = "sim/cockpit/radios/com1_freq_hz";
                        String com1StandbyDref = "sim/cockpit/radios/com1_stdby_freq_hz";
                        String batteryPowerDref = "sim/cockpit/electrical/battery_on";
                        String com1ListenDref = "sim/cockpit2/radios/actuators/audio_selection_com1";

                        float[] com1ActiveDrefResult = xpc.getDREF(com1ActiveDref);
                        float[] com1StandbyDrefResult = xpc.getDREF(com1StandbyDref);
                        float[] com1ListenDrefResult = xpc.getDREF(com1ListenDref);
                        float[] batteryPowerDrefResult = xpc.getDREF(batteryPowerDref);

                        com1ActiveFreq = String.valueOf(com1ActiveDrefResult[0]);
                        com1StandbyFreq = String.valueOf(com1StandbyDrefResult[0]);
                        com1LightState = String.valueOf(com1ListenDrefResult[0]);
                        batteryState = String.valueOf(batteryPowerDrefResult[0]);

                        com1ActiveFreq = com1ActiveFreq.replaceAll("\\[\\]", "");
                        com1ActiveFreq = com1ActiveFreq.replaceAll("\\.", "");
                        com1ActiveFreq = com1ActiveFreq.substring(0, 3) + "." + com1ActiveFreq.substring(3);
                        
                        com1StandbyFreq = com1StandbyFreq.replaceAll("\\[\\]", "");
                        com1StandbyFreq = com1StandbyFreq.replaceAll("\\.", "");
                        com1StandbyFreq = com1StandbyFreq.substring(0, 3) + "." + com1StandbyFreq.substring(3);

                        // Debug Statement
                        if (isDebugRunning) {
                            System.out.println("Debug: " + "Com1Active = " + com1ActiveFreq);
                            System.out.println("Debug: " + "Com1Standby = " + com1StandbyFreq);
                            System.out.println("Debug: " + "Com1Listen = " + com1ListenDrefResult[0]);
                            System.out.println("Debug: " + "Battery Power = " + batteryState);
                        }
                        
                        java.awt.EventQueue.invokeLater(() -> {
                            setActiveRadioLabel(com1ActiveFreq);
                            setStandbyRadioLabel(com1StandbyFreq);
                            
                            if (batteryState.matches("1.0")) {
                                lbl_activeFreq.setVisible(true);
                                lbl_standbyFreq.setVisible(true);
                                
                                if (com1LightState.matches("1.0")) {
                                    vhf1_light.setVisible(true);
                                    
                                    if (audioThread == null) {
                                        // Debug Statement
                                        if (isDebugRunning) {
                                            System.out.println("Debug: " + "Audio Thread is Null - Probably first boot.");
                                        }
                                        audioThread = new AudioStream(currentStream);
                                        audioThread.start();

                                    } else {
                                        if (audioThread.isAlive()) {
                                            // Debug Statement
                                            if (isDebugRunning) {
                                                System.out.println("Debug: " + "Audio Thread is Alive - Can't start another thread!");
                                            }
                                        } else {
                                            // Debug Statement
                                            if (isDebugRunning) {
                                                System.out.println("Debug: " + "Audio Thread not started");
                                            }
                                            audioThread = new AudioStream(currentStream);
                                            audioThread.start();

                                        }
                                    }
                                    
                                } else {
                                    vhf1_light.setVisible(false);
                                    
                                    stopAudioStream();
                                }
                                
                                isBatteryOn = true;

                            } else {
                                lbl_activeFreq.setVisible(false);
                                lbl_standbyFreq.setVisible(false);
                                vhf1_light.setVisible(false);
                                
                                isBatteryOn = false;
                                
                                stopAudioStream();
                            }
                        });

                        try {
                            Thread.sleep(refreshTime);
                        } catch (InterruptedException ex) {

                        }

                        if(System.in.available() > 0) {
                            break;
                        }
                    }
                }
                catch(IOException ex) {
                    // Debug Statement
                    if (isDebugRunning) {
                        System.out.println("Debug: " + "Error:");
                        System.out.println("Debug: " + ex.getMessage());
                    }
                    
                    // Debug Statement
                    if (isDebugRunning) {
                        System.out.println("Debug: " + "Attempting  in 5 seconds");
                    }
                    
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex1) {
                        
                    }
                    createListenerThread();
                }
            }
        };
        xPlaneListener.start();
    }
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String args[]) throws IOException, Exception {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RealRadio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new RealRadio().setVisible(true);
            
            lbl_activeFreq.setVisible(false);
            lbl_standbyFreq.setVisible(false);
            vhf1_light.setVisible(false);
            isBatteryOn = false;
        });
        
        createListenerThread();

    }
    
    public static void setActiveRadioLabel(String text) {
        lbl_activeFreq.setText(text);
    }
    
    public static void setStandbyRadioLabel(String text) {
        lbl_standbyFreq.setText(text);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel RealRadio;
    private javax.swing.JButton closeButton;
    private static javax.swing.JLabel lbl_activeFreq;
    private static javax.swing.JLabel lbl_standbyFreq;
    private javax.swing.JButton minimizeButton;
    private javax.swing.JButton optionsButton;
    private javax.swing.JLabel realRadioLogo;
    private javax.swing.JLabel updaterBackground;
    private static javax.swing.JLabel vhf1_light;
    // End of variables declaration//GEN-END:variables
}