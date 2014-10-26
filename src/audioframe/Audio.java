package audioframe;
import java.io.*;
import java.util.ArrayList;

import javax.sound.sampled.*;
import javax.swing.JPanel;


/**
 * Super class whose objects represent audio file 
 * 
 * @author Eugene W. Stark
 * @author Jiajie Li
 * CSE 260 PRJ 2
 * 10/25/14
 */

public class Audio {
    // duration of the audio
    private int duration;
    
    // format of the audio
    private AudioFormat format;
    
    // array of double of samples in the audio
    private double[] sample;
    
    // graph view of the audio
    private JPanel graphView;
    
    /**
     * create an AudioClip object
     * @param format	format of the audio 
     * @param sample	sample of the audio 
     */
    public Audio(AudioFormat format, double[] sample) {
    }
    
    /**
     * get the duration of the audio 
     * @return	duration of the audio 
     */
    public int getDuration() {
	return duration;
    }
    
    /**
     * get the format of the audio 
     * @return	format of the audio 
     */
    public AudioFormat getFormat() {
	return format;
    }
    
    /**
     * get the sample of the audio
     * @return	sample of the audio
     */
    public double[] getSample() {
	return sample;
    }
    
    /**
     * Play this audio over the audio output device.
     *
     * @param format The audio format to use to encode the data.
     * @throws UnsupportedAudioFileException if the specified format is not
     * supported on this platform.
     * @throws LineUnavailableException if no audio line can be obtained
     * to play the audio.
     * @throws IOException if there is an error while playing the audio.
     */
    public void play()
	throws UnsupportedAudioFileException, LineUnavailableException,
	       IOException {
	
    }
    
    /**
     * return the graph view of the audio
     * @return 	graph view of the audio
     */
    public JPanel showView() {
	return graphView;
    }
    
    /**
     * Extract an audio from an audio input stream.
     *
     * @param in The audio input stream from which to extract the audio.
     * @param name The name of the audio.
     * @return the extracted audio audio.
     * @throws IOException if there is an error reading the input stream.
     */
    public static Audio fromStream(AudioInputStream in, String name)
	throws IOException {
	return null;
    }
    
    /**
     * Create an audio input stream from the data in this Audio.
     *
     * @param format The audio format to use to encode the data.
     * @return the audio input stream created from the data in this
     * Audio Clip.
     */
    public AudioInputStream toStream() {
	return null;
    }
}
