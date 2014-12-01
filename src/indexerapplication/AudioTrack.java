package indexerapplication;

import audioframe.Audio;

/**
 * Audio track that is loaded in the indexer 
 * application. Extends from Audio.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 4
 * 10/25/14
 */
public class AudioTrack extends Audio{
    // ID of the track
    private int trackID;
    
    /**
     * create an audioTrack object
     * @param trackID	ID of the track
     * @param format	format of the audio track
     * @param sample	sample of the audio track
     */
    public AudioTrack(int trackID, Audio audio) {
	super(audio.getFormat(), audio.getSample(), audio.getName());
	this.trackID = trackID;
    }
    
    /**
     * get the track ID of the track
     * @return		ID of the track
     */
    public int getTrackID() {
	return trackID;
    }
}
