
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package io.whiskey.realradio;

/*
 * @author Whiskeysim
 */

public class GeneralClasses {
    public static String getWorkingDirectory() {
        final String dir = System.getProperty("user.dir");
        return dir;
    }
    
    public static String trimFreqResult(String drefResult) {
        drefResult = drefResult.replaceAll("\\[\\]", "");
        drefResult = drefResult.replaceAll("\\.", "");
        drefResult = drefResult.substring(0, 3) + "." + drefResult.substring(3);
        
        return drefResult;
    }
        
}
