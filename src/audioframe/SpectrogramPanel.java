package audioframe;
import java.awt.Graphics;
import javax.swing.*;
import java.util.ArrayList;

/**
 * Spectrogram of the audio.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 2
 * 10/25/14
 */
public class SpectrogramPanel extends JPanel{
    // power spectrums of each section
    private double[][] powerSpectrums;
    
    // peaks of each section
    private double[][] peaks;
    
    /**
     * create a WaveformPanel object
     * @param powerSpectrums	
     * 			power spectrums of each section
     * 			that needs to be drawn
     * @param peaks	
     * 			peaks of each section that needs 
     * 			to be drawn
     */
    public SpectrogramPanel(double[][] powerSpectrums,
	    double[][] peaks) {
	super();
    }
    
    /**
     * paint the content of this panel
     * 
     * @param g
     *            graphic content of drawing
     */
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
    }
}
