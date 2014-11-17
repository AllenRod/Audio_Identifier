package audioframe;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

/**
 * The graph view of the audio.
 * 
 * @author Jiajie Li 
 * CSE 260 PRJ 3 
 * 10/25/14
 */
public class AudioGraphView extends JPanel {
    // zoom factor of the graph
    private double hzoom;

    // waveform view of the audio
    private WaveformPanel waveformView;

    // spectrogram view of the audio
    private SpectrogramPanel spectrogramView;
    
    /**
     * create an AudioGraphView object
     * @param sample	sample of the audio
     * @param maxAmp	maximum amplitude in the audio
     * @param spectrum	power spectrums of the audio
     */
    public AudioGraphView(double[] sample, 
	    PowerSpectrum spectrum, 
	    HashMap<Integer, ArrayList<Integer>> peaks) {
	super();
	setLayout(new BorderLayout());
	waveformView = new WaveformPanel(sample);
	spectrogramView = new SpectrogramPanel(spectrum, 
		peaks, sample.length);
	add(waveformView, BorderLayout.NORTH);
	add(spectrogramView, BorderLayout.CENTER);
	setZoom(16.0);
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
    }

    /**
     * set the horizontal zoom factor
     * this causes the size of the panel to change
     *
     * @param horiz  The horizontal zoom factor, in samples per pixel
     */
    public void setZoom(double horiz) {
	hzoom = horiz;
	waveformView.setZoom(hzoom);
	spectrogramView.setZoom(hzoom);
    }
    
    /**
     * zoom in or zoom out the graph view
     * @param zoomIn	true for zoom in, false for zoom out
     */
    public void zoom(boolean zoomIn) {
	if (zoomIn) {
	    waveformView.setZoom(waveformView.getZoom() / 2);
	    spectrogramView.setZoom(spectrogramView.getZoom() / 2);
	} else {
	    waveformView.setZoom(waveformView.getZoom() * 2);
	    spectrogramView.setZoom(spectrogramView.getZoom() * 2);
	}
    }
}
