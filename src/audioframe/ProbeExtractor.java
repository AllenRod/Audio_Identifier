package audioframe;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The extractor that extracts probes from
 * peaks
 * @author Jiajie Li
 * CSE 260 PRJ 3
 * 10/25/14
 */
public class ProbeExtractor {
    //probe extracted from the audio
    ArrayList<Probe> probes;
    
    // size of each index
    private int timeDiff;

    // number of sections per second
    private int sectionPerSec;
    
    // frame rate of the audio
    private final int FRAME_PER_SECOND = 8000;

    /**
     * 
     * create a ProbeExtractor object
     * @param timeDiff
     * 				time difference
     * 				to extract the probes
     * @param sectionPerSec	number of sections per second,
     * 				used to calculate the time
     */
    public ProbeExtractor(int timeDiff, int sectionPerSec) {
	probes = new ArrayList<>();
	this.timeDiff = timeDiff;
	this.sectionPerSec = sectionPerSec;
    }
    
    /**
     * extract probe from the given peaks
     * @param peaks		peaks of the audio
     */
    public ArrayList<Probe> extractProbe(
	    HashMap<Integer, ArrayList<Integer>> peaks) {
	if (!probes.isEmpty()) {
	    probes.clear();
	}
	
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
			
			if (Math.abs(freq2 - freq1) > 10) {
			    probes.add(new Probe(time1, time2, freq1, freq2));
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
    public int getTime(int sectionIndex) {
	int time = sectionIndex * FRAME_PER_SECOND / sectionPerSec;
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
