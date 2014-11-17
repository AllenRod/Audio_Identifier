package indexerapplication;
import java.awt.Graphics;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * JPanel that shows the index table values.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 3
 * 10/25/14
 */
public class IndexTable extends JPanel{    
    // table to be drawn on the panel
    private JTable indexTable;
    
    // table model of the indexer
    DefaultTableModel table;
    
    /**
     * create an IndexTable object
     * @param index	index to be drawn
     */
    public IndexTable(Index index) {
	String[] name = new String[]{"Track ID", "Duration", 
		"Peaks", "Probes"};
	table = new DefaultTableModel(name, 0);
	indexTable = new JTable(table);
	indexTable.setFillsViewportHeight(true);
	add(new JScrollPane(indexTable));
	if (index.getSize() != 0) {
	    int i = 0;
	    int ID = 0;
	    while( i < index.getSize()) {
		AudioTrack track = index.getTrack(ID);
		if (track != null) {
		    addTrack(track);
		    ID++;
		    i++;
		}  else {
		    ID++;
		}
	    }
	}
    }
    
    /**
     * add a row to the track
     * @param track		track to be added
     * @param peakSize		number of peaks
     * @param probeSize		number of probes
     */
    public void addTrack(AudioTrack track) {
	table.addRow(
		new Object[]{track.getTrackID(), track.getDuration(),
			track.getPeakNum(), track.getProbeNum()});
    }
}
