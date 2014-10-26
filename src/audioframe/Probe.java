package audioframe;

/**
 * A probe is a triple of (time2 - time1, frequency1, 
 * frequency2) in each audio.
 * 
 * @author Jiajie Li
 * CSE 220 PRJ 2
 * 10/25/14
 */
public class Probe {
    // peak frequency 1
    private int freq1;
    
    // peak frequency 2
    private int freq2;
    
    // time occurred in the audio
    private double occurTime;
    
    // time difference
    private double timeDiff;
    
    /**
     * 
     * create a Probe object
     * @param time1	time when first peak occurs
     * @param time2	time when second peak occurs
     * @param freq1	frequency of first peak
     * @param freq2	frequency of second peak
     */
    public Probe(double time1, double time2, 
	    int freq1, int freq2) {
	
    }
    
    /**
     * compare this probe with another probe
     * @param other	probe to be compared
     * @return		true if two probes have the
     * 			same (t2 - t1, f1, f2) triple
     */
    public boolean compareTo(Probe other) {
	return true;
    }
    
    /**
     * get the time this probe occurred in the audio
     * @return	time this probe occurred in the audio
     */
    public double getOccurTime() {
	return occurTime;
    }
}
