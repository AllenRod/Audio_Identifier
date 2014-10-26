package indexerapplication;
/**
 * Pair of trackID and time as values in ProbeMap.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 2
 * 10/25/14
 */
public class ID_TimePair {
    // ID of track which probe occurs
    private int trackID;

    // time of probe first occurrence
    private double time;

    /**
     * create a ID_TimePair object
     * @param trackID	ID of track
     * @param time		time of occurrence
     */
    public ID_TimePair(int trackID, double time) {

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
    public double getTime() {
	return time;
    }
}