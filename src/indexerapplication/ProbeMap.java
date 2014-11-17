package indexerapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import audioframe.Probe;

/**
 * Probe map that uses probe as a key, and pair
 * of trackID and time as the value. Extends from
 * HashMap.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 3
 * 10/25/14
 */
public class ProbeMap extends HashMap<Probe, ID_TimePair>{      
    /**
     * create a ProbeMap object
     */
    public ProbeMap() {
	super();
    }
    
    /**
     * construct an array of ID_TimePair and return it
     * @return		an array of ID_TimePair
     */
    public ID_TimePair[] getPairs() {
	ID_TimePair[] pairArray = new ID_TimePair[this.size()];
	int index = 0;
	for (Map.Entry<Probe, ID_TimePair> entry : this.entrySet()) {
	    pairArray[index] = entry.getValue();
	    index++;
	}
	return pairArray;
    }
    
    /**
     * construct an array of Probe and return it
     * @return		an array of Probe
     */
    public Probe[] getProbes() {
	Probe[] probeArray = new Probe[this.size()];
	int index = 0;
	for (Map.Entry<Probe, ID_TimePair> entry : this.entrySet()) {
	    probeArray[index] = entry.getKey();
	    index++;
	}
	return probeArray;
    }
    
    /**
     * remove the mapping for the specific trackID
     * from the map if exist
     * @param trackID	trackID to be removed
     * @return		ID_TimePair that is removed
     * 			from the map
     */
    public ID_TimePair removeByID(int trackID) {
	return null;
    }
}
