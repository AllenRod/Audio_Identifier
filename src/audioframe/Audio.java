package audioframe;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.*;
import javax.swing.JPanel;


/**
 * Super class whose objects represent audio file 
 * 
 * @author Eugene W. Stark
 * @author Jiajie Li
 * CSE 260 PRJ 3
 * 10/25/14
 */

public class Audio implements Serializable{
    // duration of the audio
    private int duration;
    
    // format of the audio
    private transient AudioFormat format;
    
    // graph view of the audio
    private JPanel graphView;
    
    // number of peaks
    private int peakSize;
    
    // number of probes
    private int probeSize;
    
    // array of double of samples in the audio
    private double[] sample;
    
    
    /**
     * create an Audio object
     * @param format	format of the audio 
     * @param sample	sample of the audio 
     */
    public Audio(AudioFormat format, double[] sample) {
	this.format = format;
	this.sample = sample;
	duration = size() / 8000;
	peakSize = 0;
	probeSize = 0;
    }
    
    /**
     * construct the graph view of this audio, called by
     * AudioAnalyser
     * @param power	power spectrum of the sample
     */
    public void constructView(PowerSpectrum power, 
	    HashMap<Integer, ArrayList<Integer>> peak) {
	graphView = new AudioGraphView(getSample(), power, peak);
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
     * get the number of peaks
     * @return	number of peaks
     */
    public int getPeakNum() {
	return peakSize;
    }
    
    /**
     * get the number of probes
     * @return	number of probes
     */
    public int getProbeNum() {
	return probeSize;
    }
    
    /**
     * get the sample of the audio
     * @return	sample of the audio
     */
    public double[] getSample() {
	return sample;
    }
    
    /**
     * return the graph view of the audio
     * @return 	graph view of the audio
     */
    public JPanel getView() {
	return graphView;
    }
    
    /**
     * receive stats from AudioAnalyser
     * @param peakSize		number of peaks
     * @param probeSize		number of probes
     */
    public void passStats(int peakSize, int probeSize) {
	this.peakSize = peakSize;
	this.probeSize = probeSize;
    }
    
    /**
     * size of the samples
     * @return	size of the samples
     */
    public int size() {
	return sample.length;
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
	AudioInputStream ain = toStream();
	SourceDataLine outputLine = AudioSystem.getSourceDataLine(format);
	outputLine.open(format);
	outputLine.start();
	byte[] buf = new byte[1024];
	int n;
	while((n = ain.read(buf)) != -1)
	    outputLine.write(buf, 0, n);
	outputLine.drain();
	try {
	    Thread.sleep(1000);
	} catch(InterruptedException x){
	    // Do nothing.
	}
	outputLine.close();

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
	// Verify that the input stream has the format we need.
	// Right now this is 16-bit signed PCM format.
	AudioFormat newformat = in.getFormat();
	if(newformat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED)
	    throw new IllegalArgumentException("Signed PCM format required.");
	int channels = newformat.getChannels();
	if(channels != 1 && channels != 2)
	    throw new IllegalArgumentException("Mono or stereo required.");
	int bytesPerFrame = newformat.getFrameSize();
	int bytesPerSample = bytesPerFrame/channels;
	if(bytesPerSample != 2)
	    throw new IllegalArgumentException("16-bit samples required.");

	boolean bigEndian = newformat.isBigEndian();
	long length = in.getFrameLength();
	if(length > Integer.MAX_VALUE)
	    throw new IllegalArgumentException("Clip too long");

	// Convert the samples in the original stream to floating point.
	// Stereo is reduced to mono by averaging the channel values.
	double[] samples = new double[(int)length];
	byte[] buf = new byte[bytesPerFrame];
	for(int i = 0; i < length; i++) {
	    double v = 0.0;
	    in.read(buf);
	    for(int j = 0; j < channels; j++) {
		byte b1 = buf[2*j];
		byte b2 = buf[2*j+1];
		if(!bigEndian) {
		    byte tmp = b1;
		    b1 = b2;
		    b2 = tmp;
		}
		int s = ((b1<<8) | (b2&0xff));
		v += (s/32768.0);
	    }
	    v /= channels;
	    samples[i] = v;
	}
	return new Audio(newformat, samples);
    }
    
    /**
     * Create an audio input stream from the data in this Audio.
     *
     * @param format The audio format to use to encode the data.
     * @return the audio input stream created from the data in this
     * Audio Clip.
     */
    public AudioInputStream toStream() {
	// Verify that the input stream has the format we need.
	// Right now this is 16-bit signed PCM format.
	if(format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED)
	    throw new IllegalArgumentException("Signed PCM format required.");
	int channels = format.getChannels();
	if(channels != 1 && channels != 2)
	    throw new IllegalArgumentException("Mono or stereo required.");
	int bytesPerFrame = format.getFrameSize();
	int bytesPerSample = bytesPerFrame/channels;
	if(bytesPerSample != 2)
	    throw new IllegalArgumentException("16-bit samples required.");
	boolean bigEndian = format.isBigEndian();

	// Reconstruct PCM samples from the clip and wrap them
	// in an input stream.
	byte[] result = new byte[sample.length * bytesPerFrame];
	int off = 0;
	for(int i = 0; i < sample.length; i++, off += bytesPerFrame) {
	    double v = sample[i] * 32768.0;
	    short s = v > Short.MAX_VALUE ? Short.MAX_VALUE : (short)v;
	    byte b1 = (byte)((s>>8) & 0xff);
	    byte b2 = (byte)(s & 0xff);
	    if(!bigEndian) {
		byte tmp = b1;
		b1 = b2;
		b2 = tmp;
	    }
	    for(int j = 0; j < channels; j++) {
		// If there is more than one channel, duplicate samples.
		result[off+2*j] = b1;
		result[off+2*j+1] = b2;
	    }
	}
	return
		new AudioInputStream(new ByteArrayInputStream(result),
			format, sample.length);
    }
}
