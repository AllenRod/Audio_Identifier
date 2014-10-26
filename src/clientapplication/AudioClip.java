package clientapplication;
import javax.sound.sampled.AudioFormat;
import audioframe.Audio;

/**
 * Audio clip to be loaded by the client or user.
 * Extends Audio.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 2
 * 10/25/14
 */
public class AudioClip extends Audio {
    /**
     * create an AudioClip object
     * @param format	format of the clip
     * @param sample	sample from the clip
     */
    public AudioClip(AudioFormat format, double[] sample) {
	super(format, sample);
    }

}
