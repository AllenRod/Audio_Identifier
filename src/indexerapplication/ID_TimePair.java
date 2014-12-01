package indexerapplication;

import java.io.Serializable;

/**
 * Pair of trackID and time as values in ProbeMap.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 4
 * 10/25/14
 */
public class ID_TimePair implements Serializable{
    // ID of track which probe occurs
    private int trackID;

    // time of probe first occurrence
    private int occurTime;

    /**
     * create a ID_TimePair object
     * @param trackID		ID of track
     * @param occurTime		time of occurrence
     */
    public ID_TimePair(int trackID, int occurTime) {
	this.trackID = trackID;
	this.occurTime = occurTime;
    }

    /**
     * get trackID from the pair
     * @return	trackID
     */
    public int getTrackID() {
	return trackID;
    }

    /**
     * get time from the pair
     * @return	time
     */
    public int getOccurTime() {
	return occurTime;
    }
}