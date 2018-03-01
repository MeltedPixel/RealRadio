/*
* This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 
* International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ 
* or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
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
