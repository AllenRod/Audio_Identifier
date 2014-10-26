package audioframe;
import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

/**
 * FFT calculator that decompose samples into weighted sine 
 * and cosine signals. It also calculates the power of each
 * signal.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 2
 * 10/25/14
 */
public class FFTCalculator {
    // the fft algorithm that decompose the samples
    private DoubleFFT_1D fftransformer;
    
    // numbers of samples in each section
    private int sectionSize;
    
    /**
     * create a FFTCalculator object
     * @param sectionSize
     * 				number of samples in
     * 				each section, or the
     * 				window size
     */
    public FFTCalculator(int sectionSize) {
	
    }
    
    /**
     * perform power calculation
     * @param fftResult	result array that holds result of fft
     * @return the 	complete power spectrums of all samples
     */
    public PowerSpectrum calculatePower(double[] fftResult) {
	return null;
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
	
    }
    
    /**
     * perform fft on the given samples
     * @param sample	samples that require fft
     * @return		double array with the weighted
     * 			sine and cosine waves
     */
    public double[] transform(double[] sample) {
	return null;
    }
}
