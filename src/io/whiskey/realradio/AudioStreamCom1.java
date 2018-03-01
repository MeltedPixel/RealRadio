/*
* This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 
* International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ 
* or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */
package io.whiskey.realradio;

import gov.nasa.xpc.XPlaneConnect;
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
            
            input = new URL(streamURL).openStream();
            Player player = new Player(input);
            player.play();

        } catch (InterruptedIOException e) {
            Thread.currentThread().interrupt();
            
        } catch (IOException e) {
            if (!isInterrupted()) {
               
                try(XPlaneConnect xpc = new XPlaneConnect()) {
                    // Display 'Hello from Java' 100 pixels from the left
                    // edge of the screen and at the default height
                    xpc.sendTEXT("Real Radio - Frequency Undefined", 10, 10);
                } catch (IOException ex) {
                    
                }
                
            } else {

            }
            
        } catch (JavaLayerException ex) {
           
        }
       
    }
}
