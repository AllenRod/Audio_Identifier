package audioframe;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The extractor that extracts probes from
 * peaks
 * @author Jiajie Li
 * CSE 260 PRJ 4
 * 10/25/14
 */
public class ProbeExtractor {   
    // minimum difference between frequencies
    private int freqDiff;
    
    // number of sections per second
    private int sectionPerSec;
    
    // size of each index
    private int timeDiff;

    /**
     * 
     * create a ProbeExtractor object
     * @param timeDiff
     * 				time difference
     * 				to extract the probes
     * @param sectionPerSec	number of sections per second,
     * 				used to calculate the time
     * @param sectionSize	size of each section
     */
    public ProbeExtractor(int timeDiff, int sectionPerSec, 
	    int sectionSize) {
	this.timeDiff = timeDiff;
	this.sectionPerSec = sectionPerSec;
	freqDiff = (int) (sectionSize * (0.20));
    }
    
    /**
     * extract probe from the given peaks
     * @param peaks		peaks of the audio
     */
    public ArrayList<Probe> extractProbe(
	    HashMap<Integer, ArrayList<Integer>> peaks) {
	//probe extracted from the audio
	ArrayList<Probe> probes = new ArrayList<>();
	
	// array of the sections
	Integer[] sections = peaks.keySet().toArray(new Integer[0]);
	
	for (int i = 0; i < sections.length; i++) {
	    // time of first peak
	    int time1 = getTime(sections[i]);

	    for (int j = 1; j <= timeDiff; j++) {
		// time of second peak
		int time2 = getTime(sections[i] + j);

		// see if the second peak exists
		if (peaks.get(sections[i] + j) == null) {
		    continue;
		}

		// iterates through the frequencies in first peak
		for (Integer peak1 : peaks.get(sections[i])) {
		    int freq1 = peak1;

		    // iterates through the frequencies in second peak
		    for (Integer peak2 : peaks.get(sections[i] + j)) {
			int freq2 = peak2;
			
			if (Math.abs(freq2 - freq1) > freqDiff) {
			    probes.add(new Probe(time1, time2, 
				    freq1, freq2));
			}
		    }
		}
	    }
	}
	return probes;
    }
    
    /**
     * calculate the time in seconds with the given sectionIndex
     * @param sectionIndex	index of section
     * @return			time of occurrence in seconds
     */
    private int getTime(int sectionIndex) {
	int time = sectionIndex / sectionPerSec;
	return time;
    }
    
    /**
     * set new max time difference
     * @param newTimeDiff	new time difference
     * 				to extract the probes
     */
    public void setTimeDiff(int newTimeDiff) {
	timeDiff = newTimeDiff;
    }
}
