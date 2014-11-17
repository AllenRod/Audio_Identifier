package audioframe;

import java.io.Serializable;

/**
 * A probe is a triple of (time2 - time1, frequency1, 
 * frequency2) in each audio.
 * 
 * @author Jiajie Li
 * CSE 220 PRJ 3
 * 10/25/14
 */
public class Probe implements Serializable{
    // peak frequency 1
    private int freq1;
    
    // peak frequency 2
    private int freq2;
    
    // time occurred in the audio
    private int occurTime;
    
    // time difference
    private int timeDiff;
    
    private final int FRAME_PER_SECOND = 8000;
    
    /**
     * 
     * create a Probe object
     * @param time1		time when first peak occurs
     * @param time2		time when second peak occurs
     * @param freq1		frequency of first peak
     * @param freq2		frequency of second peak
     */
    public Probe(int time1, int time2, int freq1, int freq2) {
	this.freq1 = freq1;
	this.freq2 = freq2;
	occurTime = time1 / FRAME_PER_SECOND;
	timeDiff = time2 - time1;
    }
    
    /**
     * compare this probe with another probe
     * @param other	other probe to be compared
     * @return		true if two probes have the
     * 			same (t2 - t1, f1, f2) triple
     */
    public boolean compareTo(Probe other) {
	if (this == other) {
	    return true;
	}
	if (this.getFreq1() != other.getFreq1()) {
	    return false;
	} 
	if (this.getFreq2() != other.getFreq2()) {
	    return false;
	}
	if (this.getTimeDiff() != other.getTimeDiff()) {
	    return false;
	}
	return true;
    }
    
    /**
     * get the frequency in first peak
     * @return		frequency in first peak
     */
    public int getFreq1() {
	return freq1;
    }
    
    /**
     * get the frequency in second peak
     * @return		frequency in second peak
     */
    public int getFreq2() {
	return freq2;
    }
    
    /**
     * get the time this probe occurred in the audio
     * @return	time this probe occurred in the audio
     */
    public int getOccurTime() {
	return occurTime;
    }
    
    /**
     * get the time difference between 2 peaks
     * @return		time difference between 2 peaks
     */
    public int getTimeDiff() {
	return timeDiff;
    }
    
    /**
     * compute the hash code of this object, override method
     * in Object
     * @return		hash code of this object
     */
    public int hashCode() {
	int code  = freq1 * 1013 + freq2 * 31;
	code += timeDiff * 7 + occurTime;
	return code;
    }
}
