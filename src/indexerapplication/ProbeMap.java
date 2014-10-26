package indexerapplication;

import java.util.ArrayList;
import java.util.HashMap;
import audioframe.Probe;

/**
 * Probe map that uses probe as a key, and pair
 * of trackID and time as the value. Extends from
 * HashMap.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 2
 * 10/25/14
 */
public class ProbeMap extends HashMap<Probe, ID_TimePair>{
    // used in serialization
    private static final long serialVersionUID = -8604030401919965427L;

    // probes as keys
    private ArrayList<Probe> probes;
    
    // ID_TimePairs as values
    private ArrayList<ID_TimePair> pairs;
    
    /**
     * create a ProbeMap object
     */
    public ProbeMap() {
	super();
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
