package indexerapplication;
import javax.sound.sampled.AudioFormat;

import audioframe.Audio;

/**
 * Audio track that is loaded in the indexer 
 * application. Extends from Audio.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 2
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
    public AudioTrack(int trackID, AudioFormat format,
	    double[] sample) {
	super(format, sample);
    }
}
