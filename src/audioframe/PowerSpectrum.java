package audioframe;

/**
 * Power spectrums of all samples.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 2
 * 10/25/14
 */
public class PowerSpectrum {
    // max power of each section hold in double array
    private double[] maxPower;
    
    // nyquist frequency of all section
    private int nyquistFreq;
    
    // power of each section and each frequency in 2D 
    // double array
    private double[][] power;
    
    // number of samples in each section
    private int sectionSize;
    
    /**
     * create a PowerSpectrum object
     */
    public PowerSpectrum() {
	
    }
    
    /**
     * get nyquist frequency of each section
     * @return	nyquist frequency of each section
     */
    public int getNyqFreq() {
	return nyquistFreq;
    }
    
    /**
     * get max power each section
     * @return	max power of each section
     */
    public double[] getMaxPower() {
	return maxPower;
    }
    
    /**
     * get power spectrums in all section
     * @return	power spectrums in all section
     */
    public double[][] getPower() {
	return power;
    }
}
