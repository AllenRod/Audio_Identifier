package indexerapplication;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import audioframe.Audio;
import audioframe.AudioAnalyser;
import audioframe.AudioGraphView;
import audioframe.AudioLoader;

/**
 * GUI of the Indexer application.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 4
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
	
	JMenuItem removeTrack = new JMenuItem("Remove Track");
	removeTrack.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		try {
		    String idStr = 
			    JOptionPane.showInputDialog(GUIFrame, 
				    "Pleas input the ID value");
		    int ID = Integer.parseInt(idStr);
		    if (index.removeTrack(ID)) {
			table.removeTrack(ID);
		    } else {
			JOptionPane.showMessageDialog(GUIFrame,
				"Track not found. Please enter a valid value");
		    }
		} catch(NumberFormatException err) {
		    JOptionPane.showMessageDialog(GUIFrame, 
			    "Invalid input. Please try again");
		}
	    }
	});
	file.add(removeTrack);
	
	
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
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Save Audio Index");
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		int ret = chooser.showSaveDialog(GUIFrame);
		if (ret == JFileChooser.APPROVE_OPTION) {
		    try {
			String fileName = chooser.getSelectedFile().getPath();
			indexer.saveIndex(fileName);
		    } catch (IOException x) {
			JOptionPane.showMessageDialog(GUIFrame, x.getMessage());
		    } catch (Throwable x) {
			JOptionPane.showMessageDialog(GUIFrame, x.getMessage());
			x.printStackTrace();
		    }
		}
	    }
	});
	file.add(saveIndex);
	
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
			if (!indexer.loadIndex(fileName)) 
			    return;			  
			index = indexer.getIndex();
			table = new IndexTable(index);
			tableFrame.getContentPane().removeAll();
			tableFrame.getContentPane().add(table);
			tableFrame.revalidate();
			tableFrame.repaint();
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
	
	JMenuItem quit = new JMenuItem("Quit");
	quit.addActionListener(new ActionListener() {
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {
		GUIFrame.dispose();
		tableFrame.dispose();
		System.exit(0);
	    }
	});
	file.add(quit);
	
	JMenu trackMenu = new JMenu("Track");
	
	JMenuItem play = new JMenuItem("Play");
	play.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (track != null) {
		    index.playTrack(track.getTrackID());
		}
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
	
	JMenuBar menuBar = new JMenuBar();
	JMenu menu = new JMenu("Track");
	
	JMenuItem select = new JMenuItem("Select");
	select.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		int row = table.getTable().getSelectedRow();
		if (row < 0) {
		    JOptionPane.showMessageDialog(tableFrame, 
			    "Please selected one row");
		    return;
		}
		track = index.getTrack(
			(int)table.getTable().getValueAt(row, 0));
		if (track != null) {
		    GUIFrame.getContentPane().removeAll();
		    GUIFrame.getContentPane().add(new JScrollPane(track.getView()));
		    GUIFrame.revalidate();
		    GUIFrame.repaint();
		}
	    }
	    
	});
	menu.add(select);
	
	menuBar.add(menu);
	tableFrame.setJMenuBar(menuBar);
	
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
