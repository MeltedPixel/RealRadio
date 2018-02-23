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
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author Whiskeysim
 */

public class AudioStream extends Thread {
    private InputStream input = null;
    private final String streamURL;
    
    public AudioStream(String stream) {
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
