package clientapplication;

import audioframe.Probe;

/**
 * Pair of trackID and time difference as values in HashMap.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 3
 * 10/25/14
 */
public class ID_TimeDiffPair {
    // number of hits
    private int hit;
    
    // ID of track which probe occurs
    private int trackID;

    // time of probe first occurrence
    private int timeDiff;

    /**
     * create a ID_TimePair object
     * @param trackID		ID of track
     * @param time		time of occurrence
     */
    public ID_TimeDiffPair(int trackID, int timeDiff) {
	hit = 0;
	this.trackID = trackID;
	this.timeDiff = timeDiff;
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
	ID_TimeDiffPair other = (ID_TimeDiffPair) obj;
	if (this.getTimeDiff() != other.getTimeDiff()) {
	    return false;
	} 
	if (this.getTrackID() != other.getTrackID()) {
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
    public int getTimeDiff() {
	return timeDiff;
    }
    
    @Override
    /**
     * compute the hash code of this object, override method
     * in Object
     * @return		hash code of this object
     */
    public int hashCode() {
	int code  = trackID * 31 + timeDiff * 37;
	return code;
    }
}