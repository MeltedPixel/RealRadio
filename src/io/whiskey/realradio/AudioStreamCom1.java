/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.whiskey.realradio;

import static io.whiskey.realradio.RealRadio.isDebugRunning;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author Whiskeysim
 */

public class AudioStreamCom1 extends Thread {
    private InputStream input = null;
    private final String streamURL;
    
    public AudioStreamCom1(String stream) {
        this.streamURL = stream;
    }
    
    @Override
    public void interrupt() {
        super.interrupt();
        
        try {
            input.close();
            
        } catch (IOException e) {
            // quietly close
            
        }
    }
    
    @Override
    public void run() {
        
        try {
            Clip audioSuccess = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(RealRadio.class.getResource("sounds/JoinChannel.wav"));
            audioSuccess.open(inputStream);
            audioSuccess.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println(e.getMessage());
        }
        
        try {
            input = new URL(streamURL).openStream();
            Player player = new Player(input);
            player.play();
            
        } catch (InterruptedIOException e) {
            Thread.currentThread().interrupt();
            
            // Debug Statement
            if (isDebugRunning) {
                System.out.println("Debug: " + "Thread Interrupted via InterruptedIOException");
            }
            
        } catch (IOException e) {
            if (!isInterrupted()) {
                // Silent Space
                
            } else {
                
                // Debug Statement
                if (isDebugRunning) {
                    System.out.println("Debug: " + "Thread Interupted");
                }
                
            }
            
        } catch (JavaLayerException ex) {
            // Debug Statement
            if (isDebugRunning) {
                System.out.println("Debug: " + "Audio Bitstream Closed");
            }
        }
        
        // Debug Statement
        if (isDebugRunning) {
            System.out.println("Debug: " + "Shutting down thread");
        }
    }
}
