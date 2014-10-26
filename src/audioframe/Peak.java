package audioframe;
/**
 * the local maxima in the given time in an audio.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 2
 * 10/25/14
 */
public class Peak {
    // time of occurrence in the audio
    private double time;
    
    // the local maximum frequency  
    private int frequency;
    
    /**
     * create a Peak object
     * @param time	time of occurrence
     * @param frequency	local maximum frequency
     */
    public Peak(double time, int frequency) {
	
    }
    
    /**
     * get the local maximum frequency
     * @return	local maximum frequency
     */
    public int getFrequency() {
	return frequency;
    }
    
    /**
     * get the time of occurrence
     * @return	time of occurrence
     */
    public double getTime() {
	return time;
    }
}
