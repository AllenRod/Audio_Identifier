package clientapplication;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import audioframe.Audio;
import audioframe.AudioAnalyser;
import audioframe.AudioLoader;
import audioframe.Probe;
import indexerapplication.Indexer;
import indexerapplication.ProbeMap;

/**
 * The match application that match the clip.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 3
 * 11/16/14
 */
public class Matcher {
    // loaded clip from client
    private Audio clip;
    
    // the clip analyzer
    private AudioAnalyser clipAnalyser;
    
    // indexer from indexer application
    private Indexer indexer;
    
    // the GUI frame of the application
    private MatcherGUI matcherGUI;
    
    // the ArrayList for matching purpose
    private HashMap<ID_TimeDiffPair, Integer> matchMap;
    
    // probes extracted from the clip to be compared
    private ArrayList<Probe> probes;
    
    // probe map from the indexer application
    private ProbeMap probeMap;
    
    /**
     * create a Matcher object
     * @param indexer	the indexer application
     */
    public Matcher(Indexer indexer) {
	clip = null;
	clipAnalyser = new AudioAnalyser();
	this.indexer = indexer;
	matcherGUI = new MatcherGUI(this);
	matchMap = new HashMap<>();
    }
    
    /**
     * get the current loaded clip
     * @return		the current loaded clip
     */
    public Audio getClip() {
	return clip;
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
     * match the clip with the tracks in indexer application
     */
    public void match() {
	matchMap.clear();
	
	int hits = 0;
	int hitsID = 0;
	int diff = 0;
	
	// get the probe map and probes from indexer application
	probeMap = indexer.getIndex().getProbeMap();
	Probe[] indexerProbes = probeMap.getProbes();
	// iterates through the probes from matcher application
	Iterator<Probe> it = probes.iterator();
	while (it.hasNext()) {
	    Probe current = it.next();
	    
	    // iterates through the probes from indexer application
	    for (int i = 0; i < indexerProbes.length; i++) {
		if (current.compareTo(indexerProbes[i])) {
		    int trackID = probeMap.get(indexerProbes[i]).getTrackID();
		    int currentTime = current.getOccurTime();
		    int trackTime = probeMap.get(indexerProbes[i]).getOccurTime();
		    int timeDiff;
		    if (trackTime >= currentTime) {
			timeDiff = trackTime - currentTime;
		    } else {
			timeDiff = currentTime - trackTime;
		    }
		    ID_TimeDiffPair pair = new ID_TimeDiffPair(trackID, timeDiff);
		    if (!matchMap.containsKey(pair)) {
			matchMap.put(pair, 1);
		    } else {
			int num = matchMap.get(pair);
			if (num + 1 >= hits) {
			    hits = num + 1;
			    hitsID = trackID;
			    diff = timeDiff;
			}
			matchMap.put(pair, num + 1);
		    }
		}
	    }
	}	
	reportMatchResult(hits, hitsID, diff);
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
			clip.play();
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
     * report result of the match
     */
    public void reportMatchResult(int hits, int trackID, int time) {
	int min = time / 60;
	int sec = time % 60;
	String secStr = "";
	if (sec > 10) {
	    secStr += sec;
	} else {
	    secStr += "0" + sec;
	}
	String result = "Maximum " + hits + " hits on track ID " + trackID + 
		" at time " + min + ":" + secStr + "\n";
	JOptionPane.showMessageDialog(matcherGUI.getFrame(), result);
    }
}
