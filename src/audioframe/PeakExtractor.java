package audioframe;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The extractor that extracts peaks from
 * all power spectrums.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 2
 * 10/25/14
 */
public class PeakExtractor {  
    // minimum percent difference to extract 
    // the peak from power spectrums
    private double minPercentDiff;
    
    // HashMap of peaks
    private HashMap<Integer, ArrayList<Integer>> peaks;
    
    /**
     * create a PeakExtractor object
     * @param minPercentDiff 		
     * 			minimum percent difference to 
     * 			extract the peak from power spectrums
     */
    public PeakExtractor(double minPercentDiff) {
	this.minPercentDiff = minPercentDiff;
	peaks = new HashMap<Integer, ArrayList<Integer>>();
    }
    
    /**
     * extract peaks from power spectrum
     * @param powerSpectrum	the power spectrums of audio
     */
    public HashMap<Integer, ArrayList<Integer>> extractPeaks(double[][] powerSpectrum) {
	for (int i = 0; i < powerSpectrum.length; i++) {
	    ArrayList<Integer> frequencies = new ArrayList<>();
	    for (int j = 1; j < powerSpectrum[i].length - 1; j++) {
		double currentPower = powerSpectrum[i][j];
		double prevPower = powerSpectrum[i][j - 1];
		double nextPower = powerSpectrum[i][j + 1];
		double power = currentPower * (1 - minPercentDiff);
		if (power > prevPower && power > nextPower) {
		    frequencies.add(j);
		}
	    }
	    peaks.put(i, frequencies);
	}
	return peaks;
    }
    
    /**
     * set a new minimum percent difference
     * @param newPercentDiff	new minimum percent difference
     */
    public void setPercentDiff(double newPercentDiff) {
	this.minPercentDiff = newPercentDiff;
    }
}
