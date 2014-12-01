package clientapplication;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Count the hit of a specific track ID and time difference of
 * probe in a clip.
 * @author Jiajie Li
 * CSE 260 PRJ 4
 * 11/23/14
 */
public class HitCounter extends HashMap<ID_DeltaPair, Integer>{
    /**
     * create a HitCounter object
     */
    public HitCounter() {
	super();
    }
    
    /**
     * increment the hit of the key
     * @param key	the hit ID_DeltaPair
     */
    public void hit(ID_DeltaPair key) {
	if (containsKey(key)) {
	    int hit = get(key);
	    remove(key);
	    put(key, hit + 1);
	} else {
	    put(key, 0);
	}
    }
    
    /**
     * sort the map and return the top ten hits of probes
     * @return		the Map that holds the ten hits of probes
     */
    public ID_DeltaPair[] sortHits() {
	// result of the sorting
	ID_DeltaPair[] result = new ID_DeltaPair[10];
	
	// get a list of the entries from the map
	List<Map.Entry<ID_DeltaPair, Integer>> list = 
		new LinkedList<>(entrySet());
	
	// sort the list by comparing the hits counts
	Collections.sort(list, 
		new Comparator<Map.Entry<ID_DeltaPair, Integer>>() {

		    @Override
		    public int compare(
			    Map.Entry<ID_DeltaPair, Integer> o1,
			    Map.Entry<ID_DeltaPair, Integer> o2) {
			return (o2.getValue().compareTo(o1.getValue()));
		    }
	});
	
	// get top ten hits after comparison
	for (int i = 0; i < 10; i++) {
	    if (i == list.size()) {
		break;
	    }
	    result[i] = list.get(i).getKey();
	}
	
	return result;
    }
}
