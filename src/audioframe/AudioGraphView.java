package audioframe;

import java.awt.BorderLayout;
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

    /**
     * create an AudioGraphView object
     * @param audio	
     * 			audio that requires the 
     * 			graph view
     * @param audioInfo
     * 			audio info that are needed
     * 			in the graph view
     */
    public AudioGraphView(Audio audio, AudioAnalyser audioInfo) {
	super();
	setLayout(new BorderLayout());
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

    /**
     * zoom the view with horizontal zoom factor
     * 
     * @param zoomFactor
     *            the zoom factor of the graph
     */
    public void setZoom(double zoomFactor) {

    }
}
