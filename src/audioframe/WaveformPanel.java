package audioframe;
import java.awt.Graphics;
import javax.swing.*;

/**
 * Waveform view of the audio.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 2
 * 10/25/14
 */
public class WaveformPanel extends JPanel{
    // samples to be drawn in waveform
    private double[] sample;

    /**
     * create a WaveformPanel object
     * @param sample	
     * 			sample that needs to be drawn
     */
    public WaveformPanel(double[] sample) {
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
