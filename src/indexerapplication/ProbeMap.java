package indexerapplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import audioframe.Probe;

/**
 * Probe map that uses probe as a key, and pair
 * of trackID and time as the value. Extends from
 * HashMap.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 4
 * 10/25/14
 */
public class ProbeMap extends HashMap<Probe, ArrayList<ID_TimePair>> {      
    /**
     * create a ProbeMap object
     */
    public ProbeMap() {
	super();
    }

    /**
     * put the pair in the list of the HashMap as value
     * @param key	the key of the mapping
     * @param pair	the pair to be added as the values
     */
    public void putInList(Probe key, ID_TimePair pair) {
	if (containsKey(key)) {
	    ArrayList<ID_TimePair> oldList = get(key);
	    oldList.add(pair);
	    put(key, oldList);
	} else {
	    ArrayList<ID_TimePair> list = new ArrayList<>();
	    list.add(pair);
	    put(key, list);
	}
    }
    
    /**
     * remove the mapping for the specific trackID
     * from the map if exist
     * @param trackID	trackID to be removed
     * @return		ID_TimePair that is removed
     * 			from the map
     */
    public void removeByID(int trackID) {
	for (Map.Entry<Probe, ArrayList<ID_TimePair>> entry : entrySet()) {
	    Iterator<ID_TimePair> list = entry.getValue().iterator();
	    while (list.hasNext()) {
		ID_TimePair pair = list.next();
		if (pair.getTrackID() == trackID) {
		    list.remove();
		}
	    }
	}
    }
}