package audioframe;

import java.util.ArrayList;

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
    
    /**
     * create a PeakExtractor object
     * @param minPercentDiff 		
     * 			minimum percent difference to 
     * 			extract the peak from power spectrums
     */
    public PeakExtractor(double minPercentDiff) {
	
    }
    
    /**
     * extract peaks from power spectrum
     */
    public ArrayList<Peak> extractPeaks() {
	return null;
    }
    
    /**
     * set a new minimum percent difference
     * @param newPercentDiff	new minimum percent difference
     */
    public void setPercentDiff(double newPercentDiff) {
	
    }
}
