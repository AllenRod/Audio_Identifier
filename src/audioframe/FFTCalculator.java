package audioframe;
import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

/**
 * FFT calculator that decompose samples into weighted sine 
 * and cosine signals. It also calculates the power of each
 * signal.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 4
 * 10/25/14
 */
public class FFTCalculator {
    // the fft algorithm that decompose the samples
    private DoubleFFT_1D fftransformer;
    
    // total numbers of sections in the sample
    private int sectionNum;
    
    // numbers of samples in each section
    private int sectionSize;
    
    // numbers of section in each second
    private int sectionPerSec;
    
    // nyquist frequency
    private int nyqFreq;
    
    // frame rate of the audio
    private final double FRAME_PER_SECOND = 8000.0;
  
    
    /**
     * create a FFTCalculator object
     * @param sectionSize
     * 				number of samples in
     * 				each section, or the
     * 				window size
     */
    public FFTCalculator(int sectionSize, int sectionPerSec) {
	this.sectionSize = sectionSize;
	this.sectionPerSec = sectionPerSec;
	fftransformer = new DoubleFFT_1D(sectionSize);
	sectionNum = 0;
	nyqFreq = sectionSize / 2;
    }
    
    /**
     * perform power calculation, call transform()
     * @param sample	sample to perform power spectrum calculation
     * @return the 	complete power spectrums of all samples
     */
    public PowerSpectrum calculatePower(double[] sample) {
	// perform fftransform on the sample array
	double[][] fftArray = transform(sample);
	
	// hold power of whole samples
	double[][] powerArray = new double[fftArray.length][nyqFreq];
	
	// hold maximum power in each section
	double[] maxPower = new double[fftArray.length];
	for (int i = 0; i < maxPower.length; i++) {
	    maxPower[i] = Double.MIN_VALUE;
	}
	
	// calculate the power
	for (int i = 0; i < fftArray.length; i++) {
	    // index of the result array to put in result
	    int index = 0;
	    
	    // calculate the power in each section
	    for (int j = 0; j < nyqFreq; j += 2) {
		// adding the square of sine wave and cosine wave
		double power = fftArray[i][j] * fftArray[i][j] + 
			fftArray[i][j + 1] * fftArray[i][j + 1];
		// determine the maximum power
		if (power > maxPower[i]) {
		    maxPower[i] = power;
		}
		// put in result array
		powerArray[i][index] = power;
		index++;
	    } 
	    // reset index to 0
	    index = 0;
	}
	return new PowerSpectrum(maxPower, powerArray, 
		nyqFreq, sectionSize);
    }
    
    /**
     * get the size of section
     * @return	numbers of samples in each section
     */
    public int getSectionSize() {
	return sectionSize;
    }
    
    /**
     * set new size of section
     * @param newSectionSize	new section size of
     * 				the samples
     */
    public void setSectionSize(int newSectionSize) {
	sectionSize = newSectionSize;
    }
    
    /**
     * perform fft on the given samples
     * @param sample	samples that require fft
     * @return		double array with the weighted
     * 			sine and cosine waves
     */
    private double[][] transform(double[] sample) {
	// the numbers of sections in sample
	//sectionNum = (int)Math.ceil((double)sample.length / sectionSize);
	sectionNum = (int)Math.ceil((double)sample.length / 
		FRAME_PER_SECOND * sectionPerSec);
	int diff = sectionSize - 
		(int)Math.floor((double)FRAME_PER_SECOND / sectionPerSec);
	diff = (int)Math.floor((double)sectionPerSec * diff
		/ (sectionPerSec - 1));

	// the double array that is performing fft
	double[][] fftArray = new double[sectionNum][sectionSize * 2];

	// index of the sample to put in temp array
	int index = 0;

	// put samples into fft array
	for (int i = 0; i < fftArray.length; i++) {
	    // transform samples in each section
	    int length = fftArray[i].length;
		    
	    // temp array that holds sample split in section
	    double[] temp = new double[length];
	    
	    if (i % (sectionPerSec - 1) == 0) {
		index = i * 
			(int)Math.floor((double)FRAME_PER_SECOND / sectionPerSec);
	    }
	    
	    for (int j = 0; j < length; j += 2) {
		double b = sample[index];
		temp[j] = b;
		temp[j + 1] = 0;
		index++;
		if (index >= sample.length) {
		    break;
		}
	    }

	    // perform fft
	    fftransformer.complexForward(temp);
	    for (int k = 0; k < temp.length; k++) {
		fftArray[i][k] = temp[k];
	    }
	    
	    index -= diff;
	}

	return fftArray;
    }
}
