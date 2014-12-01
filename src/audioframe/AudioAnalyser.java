package audioframe;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Holds analyzed information and statistics
 * from the audio. Also holds different agents
 * to analyze the data from audio.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 4
 * 10/25/14
 */
public class AudioAnalyser {
     
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
     * @param sectionSize	number of samples per section
     * @param sectionPerSec	number of section per second
     * @param minPercentDiff	minimum percent difference
     * @param timeDiff		time differences for ProbeExtractor
     */
    public AudioAnalyser(int sectionSize, int sectionPerSec,
	    double minPercentDiff, int timeDiff) {
	fftCalculator = new FFTCalculator(sectionSize, sectionPerSec);
	peakExtractor = new PeakExtractor(minPercentDiff);
	probeExtractor = new ProbeExtractor(timeDiff, 
		sectionPerSec, sectionSize);
    }
    
    /**
     * create an AudioAnalyser with default parameter
     */
    public AudioAnalyser() {
	fftCalculator = new FFTCalculator(1024, 10);
	peakExtractor = new PeakExtractor(0.85);
	probeExtractor = new ProbeExtractor(1, 10, 1024);
    }
    
    /**
     * analyze the given audio
     * @param audio	audio to be analyzed
     */
    public void analyze(Audio audio) {
	// power spectrum of the audio
	PowerSpectrum powerSpectrum = fftCalculator.calculatePower(audio.getSample());
	
	// peaks extracted from the power spectrum
	HashMap<Integer, ArrayList<Integer>> peaks = peakExtractor.extractPeaks(powerSpectrum.getPower());
	probes = probeExtractor.extractProbe(peaks);
	audio.constructView(powerSpectrum, peaks);
	audio.passStats(peakExtractor.getNum(), probes.size());
    }

    /**
     * get an ArrayList of probes from the audio
     * @return	list of probes from the audio
     */
    public ArrayList<Probe> getProbes() {
	return probes;
    }
    
}
