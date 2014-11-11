package audioframe;

import java.util.ArrayList;

/**
 * Holds analyzed information and statistics
 * from the audio. Also holds different agents
 * to analyze the data from audio.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 2
 * 10/25/14
 */
public class AudioAnalyser {
    // peaks extracted from the power spectrum
    private ArrayList<Peak> peaks;
    
    // power spectrum of the audio
    private PowerSpectrum powerSpectrum;
    
    // probes extracted from the audio
    private ArrayList<Probe> probes;
    
    // calculator that does FFT and calculate power spectrum
    private FFTCalculator fftCalculator;
    
    // peak extractor that extracts peaks from power spectrum
    private PeakExtractor peakExtractor;
    
    // probes extractor that extracts probes from peaks
    private ProbeExtractor probeExtractor;
    
    /**
     * create an AudioAnalyser object
     */
    public AudioAnalyser() {
	fftCalculator = new FFTCalculator(1024);
    }
    
    /**
     * analyze the given audio
     * @param audio	audio to be analyzed
     */
    public void analyze(Audio audio) {
	powerSpectrum = fftCalculator.calculatePower(audio.getSample());
    }
    
    /**
     * get an ArrayList of peaks from the audio
     * @return	list of peaks from the audio
     */
    public ArrayList<Peak> getPeaks() {
	return peaks;
    }
    
    /**
     * get power spectrum of the audio
     * @return	power spectrum of the audio
     */
    public PowerSpectrum getPowerSpectrum() {
	return powerSpectrum;
    }

    /**
     * get an ArrayList of probes from the audio
     * @return	list of probes from the audio
     */
    public ArrayList<Probe> getProbes() {
	return probes;
    }
    
}
