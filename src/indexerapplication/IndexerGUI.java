package indexerapplication;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import audioframe.AudioAnalyser;
import audioframe.AudioGraphView;
import audioframe.AudioLoader;

/**
 * GUI of the Indexer application.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 3
 * 11/15/14
 */
public class IndexerGUI{
    // the GUI frame of indexer
    private JFrame GUIFrame;
    
    // the table frame of indexer
    private JFrame tableFrame;
    
    // the table panel 
    private IndexTable table;
    
    // the index from indexer
    private Index index;
    
    // the indexer application
    private Indexer indexer;
    
    // audio analyzer that analyzes the track
    private AudioAnalyser analyser;
    
    // audio loader that loads audio track
    private AudioLoader loader;
    
    // the loaded audio track
    private AudioTrack track;

    /**
     * create an IndexerGUI object
     * @param indexer	the indexer application
     * @param index	the index created by indexer
     */
    public IndexerGUI(Indexer indexer) {
	GUIFrame = new JFrame("Indexer Application");
	tableFrame = new JFrame("Indexer Table");
	
	this.indexer = indexer;
	index = indexer.getIndex();
	analyser = new AudioAnalyser();
	loader = new AudioLoader(GUIFrame);
	table = new IndexTable(index);
	track = null;
	constructGUIFrame();
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
	
	JMenuItem loadTrack = new JMenuItem("Load Track");
	loadTrack.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		track = index.addTrack(loader, analyser);
		if (track != null) {
		    table.addTrack(track);
		    GUIFrame.getContentPane().removeAll();
		    GUIFrame.getContentPane().add(new JScrollPane(track.getView()));
		    GUIFrame.revalidate();
		    GUIFrame.repaint();
		} else {
		    GUIFrame.getContentPane().removeAll();
		    GUIFrame.revalidate();
		    GUIFrame.repaint();
		}
	    }
	});
	file.add(loadTrack);
	
	JMenuItem viewTable = new JMenuItem("View Table");
	viewTable.addActionListener(new ActionListener(){

	    @Override
	    public void actionPerformed(ActionEvent e) {
		tableFrame.setVisible(true);
	    }
	});
	file.add(viewTable);
	
	JMenuItem saveIndex = new JMenuItem("Save Index");
	saveIndex.addActionListener(new ActionListener(){

	    @Override
	    public void actionPerformed(ActionEvent e) {
		try {
		    indexer.saveIndex();
		} catch (IOException error) {
		    // ???
		}
	    }
	});
	file.add(saveIndex);
	
	JMenuItem loadIndex = new JMenuItem("Load Index");
	loadIndex.addActionListener(new ActionListener() {
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {
		try {
		    indexer.loadIndex();
		    index = indexer.getIndex();
		    table = new IndexTable(index);
		    tableFrame.repaint();
		} catch (IOException error) {
		    // ???
		}
	    }
	});
	file.add(loadIndex);
	
	JMenuItem quit = new JMenuItem("Quit");
	quit.addActionListener(new ActionListener() {
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {
		GUIFrame.dispose();
		tableFrame.dispose();
	    }
	});
	file.add(quit);
	
	JMenu trackMenu = new JMenu("Track");
	
	JMenuItem play = new JMenuItem("Play");
	play.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		index.playTrack(track.getTrackID());
	    }
	});
	trackMenu.add(play);
	
	JMenuItem zoomIn = new JMenuItem("Zoom In");
	zoomIn.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (track != null) {
		    ((AudioGraphView) track.getView()).zoom(true);
		}
	    }
	});
	trackMenu.add(zoomIn);
	
	JMenuItem zoomOut = new JMenuItem("Zoom Out");
	zoomOut.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (track != null) {
		    ((AudioGraphView) track.getView()).zoom(false);
		}
	    }
	});
	trackMenu.add(zoomOut);
	
	menuBar.add(file);
	menuBar.add(trackMenu);
	GUIFrame.setJMenuBar(menuBar);
	
	GUIFrame.pack();
	GUIFrame.setLocationByPlatform(true);
	GUIFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	GUIFrame.setVisible(true);
    }

    /**
     * construct the table frame
     */
    private void constructTableFrame() {
	tableFrame.getContentPane().add(table);
	
	tableFrame.pack();
	tableFrame.setLocationByPlatform(true);
	tableFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	tableFrame.setVisible(true);
    }
    
    /**
     * get this GUI frame
     * @return		the GUI frame of indexer application
     */
    public JFrame getFrame() {
	return GUIFrame;
    } 
}
