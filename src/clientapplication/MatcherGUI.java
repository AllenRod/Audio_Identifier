package clientapplication;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import audioframe.Audio;
import audioframe.AudioGraphView;
import audioframe.AudioLoader;

/**
 * GUI of the Matcher application.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 3
 * 11/15/14
 */
public class MatcherGUI {
    // the GUI frame of matcher application
    private JFrame GUIFrame;
    
    // the audio loader
    private AudioLoader loader;
    
    // the loaded audio clip
    private Audio clip;
    
    // the matcher application
    private Matcher matcher;
    
    /**
     * construct a MatcherGUI object
     * @param matcher		the matcher application
     */
    public MatcherGUI(Matcher matcher) {
	GUIFrame = new JFrame("Match Application");
	
	loader = new AudioLoader(GUIFrame);
	clip = null;
	this.matcher = matcher;
	constructGUIFrame();
    }
    
    /**
     * construct the GUI frame
     */
    private void constructGUIFrame() {
	GUIFrame.setMinimumSize(new Dimension(800, 500));
	GUIFrame.getContentPane().setLayout(new BorderLayout());
	
	JMenuBar menuBar = new JMenuBar();
	JMenu file = new JMenu("File");
	
	JMenuItem load = new JMenuItem("Load");
	load.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		clip = matcher.loadClip(loader);
		if (clip != null) {
		    GUIFrame.getContentPane().removeAll();
		    GUIFrame.getContentPane().add(new JScrollPane(clip.getView()));
		    GUIFrame.revalidate();
		    GUIFrame.repaint();
		} else {
		    GUIFrame.getContentPane().removeAll();
		    GUIFrame.revalidate();
		    GUIFrame.repaint();
		}
	    }
	});
	file.add(load);
	
	JMenuItem match = new JMenuItem("Match");
	match.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (matcher.getClip() != null) {
		    matcher.match();
		}
	    }
	});
	file.add(match);
	
	
	JMenuItem quit = new JMenuItem("Quit");
	quit.addActionListener(new ActionListener() {
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {
		GUIFrame.dispose();
	    }
	});
	file.add(quit);
	
	JMenu clipMenu = new JMenu("Clip");
	
	JMenuItem play = new JMenuItem("Play");
	play.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		matcher.playClip();
	    }
	});
	clipMenu.add(play);
	
	JMenuItem zoomIn = new JMenuItem("Zoom In");
	zoomIn.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (clip != null) {
		    ((AudioGraphView) clip.getView()).zoom(true);
		}
	    }
	});
	clipMenu.add(zoomIn);
	
	JMenuItem zoomOut = new JMenuItem("Zoom Out");
	zoomOut.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (clip != null) {
		    ((AudioGraphView) clip.getView()).zoom(false);
		}
	    }
	});
	clipMenu.add(zoomOut);
	
	menuBar.add(file);
	menuBar.add(clipMenu);
	GUIFrame.setJMenuBar(menuBar);
	
	GUIFrame.pack();
	GUIFrame.setLocationByPlatform(true);
	GUIFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	GUIFrame.setVisible(true);
    }

    /**
     * get this GUI frame
     * @return		the GUI frame of match application
     */
    public JFrame getFrame() {
	return GUIFrame;
    }
}
