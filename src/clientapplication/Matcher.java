package clientapplication;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import audioframe.Audio;
import audioframe.AudioAnalyser;
import audioframe.AudioLoader;
import audioframe.Probe;
import indexerapplication.AudioTrack;
import indexerapplication.ID_TimePair;
import indexerapplication.Index;
import indexerapplication.ProbeMap;

/**
 * The match application that match the clip.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 4
 * 11/16/14
 */
public class Matcher {
    // loaded clip from client
    private Audio clip;
    
    // the clip analyzer
    private AudioAnalyser clipAnalyser;
    
    // index from indexer application
    private Index index;
    
    // the GUI frame of the application
    private MatcherGUI matcherGUI;
    
    // probes extracted from the clip to be compared
    private ArrayList<Probe> probes;
    
    // probe map from the indexer application
    private ProbeMap probeMap;
    
    /**
     * create a Matcher object
     */
    public Matcher() {
	clip = null;
	clipAnalyser = new AudioAnalyser();
	index = null;
	matcherGUI = new MatcherGUI(this);
    }
    
    /**
     * get the current loaded clip
     * @return		the current loaded clip
     */
    public Audio getClip() {
	return clip;
    }
    
    /**
     * get the index from the application
     * @return		the index from application
     */
    public Index getIndex() {
	return index;
    }
    
    /**
     * load an Audio object
     * @param loader		
     * @return
     */
    public Audio loadClip(AudioLoader loader) {
	clip = loader.load();
	if (clip != null) {
	    clipAnalyser.analyze(clip);
	    probes = clipAnalyser.getProbes();
	    return clip;
	}
	return null;
    }
    
    /**
     * load an existing index from memory, the file is
     * named index.dat
     * @param fileName		path of file
     * @throws IOException 
     * @return 			if the load process succeed or not
     */
    public boolean loadIndex(String fileName) throws IOException {
	FileInputStream fin = null;
	ObjectInputStream ois = null;
	boolean loaded = false;
	try {
	    fin = new FileInputStream(fileName);
	    ois = new ObjectInputStream(fin);
	    Object obj = ois.readObject();
	    if (obj != null) {
		index = (Index) obj;
	    }
	    JOptionPane.showMessageDialog(matcherGUI.getFrame(),
		    "Load Success");
	    loaded = true;
	} catch (IOException e) {
	    JOptionPane.showMessageDialog(matcherGUI.getFrame(), 
		    e.getMessage());
	} catch (ClassNotFoundException e) {
	    JOptionPane.showMessageDialog(matcherGUI.getFrame(), 
		    e.getMessage());
	} finally {
	    if (ois != null) {
		ois.close();
	    }
	}
	return loaded;
    }
    
    /**
     * match the clip with the tracks in indexer application
     * @return 		the result of matching in table format
     */
    public Object[][] match() {
	if (index == null) {
	    JOptionPane.showMessageDialog(matcherGUI.getFrame(), 
		    "No index loaded. Please load an index.");
	    return null;
	}
	
	// create a HitCounter object
	HitCounter counter = new HitCounter();
	
	// get the probe map and probes from indexer application
	probeMap = index.getProbeMap();
	
	// iterates through the probes from matcher application
	Iterator<Probe> it = probes.iterator();
	while (it.hasNext()) {
	    Probe current = it.next();
	    
	    // get a list of occurred track ID and time
	    ArrayList<ID_TimePair> list = probeMap.get(current);
	    if (list == null) {
		continue;
	    }
	    
	    // iterates through the pairs from indexer application
	    Iterator<ID_TimePair> listIt = list.iterator();
	    while (listIt.hasNext()) {
		ID_TimePair pair = listIt.next();
		ID_DeltaPair countPair = 
			new ID_DeltaPair(pair.getTrackID(), 
				current.getOccurTime(), 
				pair.getOccurTime());
		counter.hit(countPair);
	    }
	  
	}	
	
	ID_DeltaPair[] result = counter.sortHits();
	Object[][] report = new Object[10][4];
	for (int i = 0; i < report.length; i++) {
	    if (result[i] == null) {
		break;
	    }
	    int ID = result[i].getTrackID();
	    AudioTrack track = index.getTrack(ID);
	    if (track == null) {
		continue;
	    }
	    report[i][0] = track.getTrackID();
	    report[i][1] = track.getName();
	    report[i][2] = result[i].getDelta();
	    report[i][3] = counter.get(result[i]);
	}
	
	return report;
    }
    
    /**
     * play the current loaded clip
     */
    public void playClip() {
	if (clip != null) {
	    SwingWorker<Object, Object> playTask =
		    new SwingWorker<Object, Object>() {
		@Override
		public Object doInBackground() {
		    try {
			clip.play(0, clip.getDuration());
		    } 
		    catch (Exception e){
			System.out.println(e.getMessage());
		    }
		    return null;
		}

		@Override
		public void done() {

		}
	    };

	    playTask.execute();
	}
    }
    
    /**
     * main method of the program
     */
    public static void main(String[] args) {
	Matcher matcherApp = new Matcher();
    }
}
