package audioframe;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

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
    
    // maximum power in each section
    private double[] maxPower;
    
    // peaks of each section
    private double[][] peaks;
    
    // length of the sample
    private int length;
    
    // zoom factor of the graph
    private double hzoom;
    
    /**
     * create a WaveformPanel object
     * @param powerSpectrums	
     * 			power spectrums of each section
     * 			that needs to be drawn
     * @param peaks	
     * 			peaks of each section that needs 
     * 			to be drawn
     */
    public SpectrogramPanel(PowerSpectrum powerspectrums,
	    ArrayList<Peak> peaks, int length) {
	super();
	powerSpectrums = powerspectrums.getPower();
	maxPower = powerspectrums.getMaxPower();
	this.length = length;
    }
    
    /**
     * Get the horizontal zoom factor.
     * 
     * @return  the horizontal zoom factor, in samples per pixel.
     */
    public double getZoom() {
	return hzoom;
    }
    
    /**
     * paint the content of this panel
     * 
     * @param g
     *            graphic content of drawing
     */
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) g;
	Color c = g.getColor();

	Dimension d = getSize();
	double samplesPerPixel = (double) length / d.getWidth();
	hzoom = samplesPerPixel;

	double height = getHeight();
	double width = getWidth();
	double horizontalFactor = width / length;
	double verticalFactor = height / powerSpectrums[0].length;


	double px = getX();
	double py = getY();

	for (int i = 0; i < powerSpectrums.length; i++) {
	    double x = horizontalFactor;
	    double y = verticalFactor;

	    for (int j = 0; j < powerSpectrums[i].length; j++) {
		py = j * verticalFactor;

		// get color of rectangle
		double rgbValue = powerSpectrums[i][j];
		rgbValue = rgbValue * 255 / maxPower[i];
		int value = (int) rgbValue;
		Color col = new Color(value, value, value);
		g2.setColor(col);

		// draw rectangle
		//g2.draw(new Rectangle2D.Double(px, py, x, y));
		g2.fill(new Rectangle2D.Double(px, py, x, y));
	    }
	    px += x;
	}

	g.setColor(c);
    }
    
    /**
     * set the horizontal zoom factor
     * this causes the size of the panel to change
     *
     * @param horiz  The horizontal zoom factor, in samples per pixel
     */
    public void setZoom(double horiz) {
	hzoom = horiz;
	int width = (int)(length / hzoom);
	int height = 100;
	setPreferredSize(new Dimension(width, height));
	revalidate();
    }
}
