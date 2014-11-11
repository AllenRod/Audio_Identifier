package audioframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.*;

/**
 * The graph view of the audio.
 * 
 * @author Jiajie Li 
 * CSE 260 PRJ 2 
 * 10/25/14
 */
public class AudioGraphView extends JPanel {
    // zoom factor of the graph
    private double hzoom;

    // waveform view of the audio
    private WaveformPanel waveformView;

    // spectrogram view of the audio
    private SpectrogramPanel spectrogramView;
    
    // width of the panel
    private int width;
    
    /**
     * create an AudioGraphView object
     * @param sample	sample of the audio
     * @param maxAmp	maximum amplitude in the audio
     * @param spectrum	power spectrums of the audio
     */
    public AudioGraphView(double[] sample, PowerSpectrum spectrum) {
	super();
	setLayout(new BorderLayout());
	width = sample.length;
	waveformView = new WaveformPanel(sample);
	spectrogramView = new SpectrogramPanel(spectrum, 
		null, sample.length);
	setZoom(16.0);
	add(waveformView, BorderLayout.NORTH);
	add(spectrogramView, BorderLayout.CENTER);
    }

    /**
     * paint the content of this panel
     * 
     * @param g
     *            graphic content of drawing
     */
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	waveformView.paintComponent(g);
	spectrogramView.paintComponent(g);
	revalidate();
	repaint();
    }

    /**
     * set the horizontal zoom factor
     * this causes the size of the panel to change
     *
     * @param horiz  The horizontal zoom factor, in samples per pixel
     */
    public void setZoom(double horiz) {
	hzoom = horiz;
	width = (int)(width / hzoom);
	int height = 100;
	waveformView.setPreferredSize(new Dimension(width, height));
	spectrogramView.setPreferredSize(new Dimension(width, height));
	revalidate();
    }
}
