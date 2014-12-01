package audioframe;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The extractor that extracts peaks from
 * all power spectrums.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 4
 * 10/25/14
 */
public class PeakExtractor {  
    // minimum percent difference to extract 
    // the peak from power spectrums
    private double minPercentDiff;
    
    // number of peaks
    private int num;
        
    /**
     * create a PeakExtractor object
     * @param minPercentDiff 		
     * 			minimum percent difference to 
     * 			extract the peak from power spectrums
     */
    public PeakExtractor(double minPercentDiff) {
	this.minPercentDiff = minPercentDiff;
	num = 0;
    }
    
    /**
     * compare the local max to the previous and next local min
     * by the given minPercentDiff
     * @param prevMin		previous local min
     * @param currentMax	current local max
     * @param nextMin		next local min
     * @return			result of comparison
     */
    private boolean compare(double prevMin, double currentMax, double nextMin) {
	double minPower = currentMax * (1 - minPercentDiff);
	if ((prevMin >= minPower) && (minPower <= nextMin)) {
	    return false;
	}
	return true;
    }
    
    /**
     * extract peaks from power spectrum
     * @param powerSpectrum	the power spectrums of audio
     */
    public HashMap<Integer, ArrayList<Integer>> 
    	extractPeaks(double[][] powerSpectrum) {
	// HashMap of peaks
	HashMap<Integer, ArrayList<Integer>> peaks = new HashMap<>();

	num = 0;
	for (int i = 0; i < powerSpectrum.length; i++) {
	    ArrayList<Integer> frequencies = new ArrayList<>();
	    double prevMin = powerSpectrum[i][0];
	    double current, nextMin;
	    int skipIndex = 1;
	    for (int j = 1; j < powerSpectrum[i].length - 1; j++) {
		current = powerSpectrum[i][j];
		nextMin = powerSpectrum[i][j + 1];
		skipIndex = j;
		if (prevMin > current) {
		    prevMin = current;
		    continue;
		}
		if (nextMin < current) {
		    // find next min
		    for (int k = j + 2; k < powerSpectrum[i].length - 1; k++) {
			double nextPower = powerSpectrum[i][k];
			if (nextPower <= nextMin) {
			    nextMin = nextPower;
			    skipIndex = k;
			} else {
			    break;
			}
		    }
		    if (compare(prevMin, current, nextMin)) {
			   if ( i > 0 && i < powerSpectrum.length - 1) {
			       if (!compare(powerSpectrum[i-1][j],current,powerSpectrum[i+1][j])){
				   	prevMin = nextMin;
				   	j = skipIndex;
			   		continue;
			       }
			   }
			   frequencies.add(j);
			   num++;
		    }
		    prevMin = nextMin;
		}		
		j = skipIndex;
	    }
	    peaks.put(i, frequencies);
	}
	return peaks;
    }
    
    /**
     * get the number of peaks
     * @return		number of peaks
     */
    public int getNum() {
	return num;
    }
    
    /**
     * set a new minimum percent difference
     * @param newPercentDiff	new minimum percent difference
     */
    public void setPercentDiff(double newPercentDiff) {
	this.minPercentDiff = newPercentDiff;
    }
}
