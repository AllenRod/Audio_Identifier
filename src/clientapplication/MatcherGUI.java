package clientapplication;
import indexerapplication.IndexTable;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import audioframe.Audio;
import audioframe.AudioGraphView;
import audioframe.AudioLoader;

/**
 * GUI of the Matcher application.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 4
 * 11/15/14
 */
public class MatcherGUI {
    // base URI of the index
    private URI base;
    
    // the GUI frame of matcher application
    private JFrame GUIFrame;
    
    // the frame of the index
    private JFrame indexFrame;
    
    // the frame of result table
    private JFrame resultFrame;
    
    // the audio loader
    private AudioLoader loader;
    
    // the table of index
    private IndexTable indexTable;
    
    // the result table of matching application
    private ResultTable table;
    
    // the matcher application
    private Matcher matcher;
    
    /**
     * construct a MatcherGUI object
     * @param matcher		the matcher application
     */
    public MatcherGUI(Matcher matcher) {
	GUIFrame = new JFrame("Match Application");
	indexFrame = new JFrame("Index Frame");
	resultFrame = new JFrame("Match Result");
	
	base = null;
	loader = new AudioLoader(GUIFrame);
	indexTable = null;
	table = new ResultTable();
	this.matcher = matcher;
	constructGUIFrame();
	constructIndexFrame();
	constructTableFrame();
    }
    
    /**
     * construct the GUI frame
     */
    private void constructGUIFrame() {
	GUIFrame.setMinimumSize(new Dimension(800, 500));
	GUIFrame.getContentPane().setLayout(new BorderLayout());
	
	JMenuBar menuBar = new JMenuBar();
	JMenu file = new JMenu("File");
	
	JMenuItem load = new JMenuItem("Load Clip");
	load.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		matcher.loadClip(loader);
		if (matcher.getClip() != null) {
		    GUIFrame.getContentPane().removeAll();
		    GUIFrame.getContentPane().add(
			    new JScrollPane(matcher.getClip().getView()));
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
	
	JMenuItem loadIndex = new JMenuItem("Load Index");
	loadIndex.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Load Audio Index");
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		int ret = chooser.showOpenDialog(GUIFrame);
		if (ret == JFileChooser.APPROVE_OPTION) {
		    try {
			String fileName = chooser.getSelectedFile().getPath();
			if (!matcher.loadIndex(fileName))
			    return;
			indexTable = new IndexTable(matcher.getIndex());
			
			indexFrame.getContentPane().removeAll();
			indexFrame.getContentPane().add(indexTable);
			indexFrame.revalidate();
			indexFrame.repaint();
			indexFrame.setVisible(true);
		    } catch (IOException x) {
			JOptionPane.showMessageDialog(GUIFrame, x.getMessage());
		    } catch (Throwable x) {
			JOptionPane.showMessageDialog(GUIFrame, x.getMessage());
			x.printStackTrace();
		    }
		}
	    }
	});
	file.add(loadIndex);
	
	JMenuItem match = new JMenuItem("Match");
	match.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (matcher.getClip() != null) {
		    table.showResult(matcher.match());
		    resultFrame.revalidate();
		    resultFrame.repaint();
		    resultFrame.setVisible(true);
		}
	    }
	});
	file.add(match);
	
	
	JMenuItem quit = new JMenuItem("Quit");
	quit.addActionListener(new ActionListener() {
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {
		GUIFrame.dispose();
		indexFrame.dispose();
		resultFrame.dispose();
		System.exit(0);
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
		if (matcher.getClip() != null) {
		    ((AudioGraphView) matcher.getClip().
			    getView()).zoom(true);
		}
	    }
	});
	clipMenu.add(zoomIn);
	
	JMenuItem zoomOut = new JMenuItem("Zoom Out");
	zoomOut.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (matcher.getClip() != null) {
		    ((AudioGraphView) matcher.getClip().
			    getView()).zoom(false);
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
     * construct the index frame
     */
    private void constructIndexFrame() {
	indexFrame.getContentPane().add(table);
	
	indexFrame.pack();
	indexFrame.setLocationByPlatform(true);
	indexFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
    
    /**
     * construct the table frame
     */
    private void constructTableFrame() {
	resultFrame.getContentPane().add(table);
	
	JMenuBar menuBar = new JMenuBar();
	JMenu menu = new JMenu("Result");
	
	JMenuItem play = new JMenuItem("Play Matched Track");
	play.addActionListener(new ActionListener(){
	    @Override
	    public void actionPerformed(ActionEvent e){
		int row = table.getTable().getSelectedRow();
		if (row < 0) {
		    JOptionPane.showMessageDialog(resultFrame, 
			    "Please selected one result");
		    return;
		}
		try {
		    // get the relative path
		    String relative = (String)table.getTable().
			    getValueAt(row, 1);
		    
		    // get the base path
		    while (base == null) {
			JOptionPane.showMessageDialog(resultFrame,
				"No base path found. Please set one up");
			base = setBasePath();
		    }
		    base = base.resolve(relative);
		    
		    // get the audio 
		    Audio matchedAudio = loader.loadFromFile(base.toURL());
		    
		    // get the offset
		    int off = (int)table.getTable().getValueAt(row, 2);
		    matchedAudio.play(off, matcher.getClip().getDuration());
		} catch (IOException err) {
		    JOptionPane.showMessageDialog(resultFrame, 
			    err.getMessage());
		    err.printStackTrace();
		    base = null;
		} catch (UnsupportedAudioFileException err) {
		    JOptionPane.showMessageDialog(resultFrame, 
			    err.getMessage());
		    base = null;
		} catch (LineUnavailableException err) {
		    JOptionPane.showMessageDialog(resultFrame, 
			    err.getMessage());
		    base = null;
		}
	    }
	    
	});
	menu.add(play);
	
	JMenuItem setPath = new JMenuItem("Set Base Path");
	setPath.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		base = setBasePath();
	    }
	    
	});
	menu.add(setPath);
	
	menuBar.add(menu);
	resultFrame.setJMenuBar(menuBar);
	
	resultFrame.pack();
	resultFrame.setLocationByPlatform(true);
	resultFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
    
    /**
     * get this GUI frame
     * @return		the GUI frame of match application
     */
    public JFrame getFrame() {
	return GUIFrame;
    }
    
    /**
     * set the base path for result table
     * @return		the URI of the base path
     */
    private URI setBasePath() {
	JFileChooser fc = new JFileChooser();
	fc.setDialogTitle("Set Base Path");
	fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	fc.setAcceptAllFileFilterUsed(false);
	int ret = fc.showOpenDialog(resultFrame);
	if (ret == JFileChooser.APPROVE_OPTION) {
	    try {
		File f = fc.getSelectedFile();
		return f.toURI();
	    } catch (Throwable x) {
		JOptionPane.showMessageDialog(GUIFrame, x.getMessage());
		x.printStackTrace();
	    }
	}
	return null;
    }
}
