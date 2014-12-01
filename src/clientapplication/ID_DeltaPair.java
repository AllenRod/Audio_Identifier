package clientapplication;


/**
 * Pair of trackID and time difference as values in HashMap.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 4
 * 10/25/14
 */
public class ID_DeltaPair {    
    // ID of track which probe occurs
    private int trackID;

    // time of probe first occurrence
    private int delta;

    /**
     * create a ID_TimePair object
     * @param trackID		ID of track
     * @param time1		time of first occurrence
     * @param time2		time of second occurrence
     */
    public ID_DeltaPair(int trackID, int time1, int time2) {
	this.trackID = trackID;
	this.delta = time2 - time1;
    }

    @Override
    /**
     * compare this pair with another pair, override method
     * in Object
     * @param obj	object to be compared
     * @return		true if two pairs have the same ID and
     * 			time difference value
     */
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj.getClass() != this.getClass()) {
	    return false;
	} 
	ID_DeltaPair other = (ID_DeltaPair) obj;
	if (delta != other.getDelta()) {
	    return false;
	} 
	if (trackID != other.getTrackID()) {
	    return false;
	}
	return true;
    }
    
    /**
     * get trackID from the pair
     * @return	trackID
     */
    public int getTrackID() {
	return trackID;
    }

    /**
     * get time difference from the pair
     * @return	time difference
     */
    public int getDelta() {
	return delta;
    }
    
    @Override
    /**
     * compute the hash code of this object, override method
     * in Object
     * @return		hash code of this object
     */
    public int hashCode() {
	int code  = trackID * 37 + delta * 17;
	return code;
    }
}