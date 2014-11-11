package audioframe;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

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
    
    // max amplitude of the sample
    private double maxAmp;

    // zoom factor of the graph
    private double hzoom;

    /**
     * create a WaveformPanel object
     * @param sample	
     * 			sample that needs to be drawn
     */
    public WaveformPanel(double[] sample) {
	super();
	
	this.sample = sample;
	
	// determine the maximum amplitude
	maxAmp = Double.MIN_NORMAL;
	for (int i = 0; i < sample.length; i++) {
	    double a = Math.abs(sample[i]);
	    if (a > maxAmp)
		maxAmp = a;
	}
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
	Color c = g.getColor();
	g.setColor(Color.BLUE);
	Dimension d = getSize();
	long length = sample.length;
	double samplesPerPixel = (double)length / d.getWidth();
	hzoom = samplesPerPixel;
	double amp = maxAmp;
	if(amp < 1E-6)
	    amp = 1E-6;
	int px = 0;
	int py = 0;
	Rectangle bounds = g.getClipBounds();
	int startX = (int)bounds.getX();
	int endX = startX + (int)bounds.getWidth();
	// We probably need to overshoot the clip region in order to properly draw the waveform.
	if(startX > 0)
	    startX--;
	double i = startX * samplesPerPixel;
	for(int x = startX; x < endX; x++, i += samplesPerPixel) {
	    for(double j = 0.0; j < samplesPerPixel; j += 1.0) {
		double v = sample[((int)(i+j))] / amp;
		int y = d.height - (int)((d.height * v + d.height)/2.0);
		if(x > 0)
		    g.drawLine(px, py, x, y);
		px = x;
		py = y;
	    }
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
	int width = (int)(sample.length / hzoom);
	int height = 100;
	setPreferredSize(new Dimension(width, height));
	revalidate();
    }
}
