package indexerapplication;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.SwingWorker;

import audioframe.Audio;
import audioframe.AudioAnalyser;
import audioframe.AudioLoader;
import audioframe.Probe;
/**
 * Index of audio tracks. Index holds a list of
 * tracks and a mapping that takes probes to
 * trackID and time pair.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 4
 * 10/25/14
 */
public class Index implements Serializable{    
    // list of folders
    //private ArrayList<Folder> folders;
    
    // probe map that takes probes as keys and 
    // trackID and time pair as values
    private ProbeMap probeMap;
    
    // the current ID
    private int currentID;
    
    // size of tracks in the index
    private int size;
   
    // audio tracks that are loaded
    private ArrayList<AudioTrack> tracks;
    
    /**
     * create an Index object
     */
    public Index() {
	//folders = new ArrayList<>();
	probeMap = new ProbeMap();
	currentID = 0;
	size = 0;
	tracks = new ArrayList<>();
    }
    
    /**
     * add a folder with the given name
     * @param name	name of the folder
     */
    public void addFolder(String name) {
    }
    
    /**
     * load and add a track into the index
     * @param		the audio loader
     * @return 		the audio track that is loaded
     */
    public AudioTrack addTrack(AudioLoader loader, AudioAnalyser trackAnalyser) {
	Audio audio = loader.load();
	if (audio == null) {
	    return null;
	}
	AudioTrack track = new AudioTrack(currentID, audio);
	if (track != null) {
	    trackAnalyser.analyze(track);
	    putInProbeMap(trackAnalyser.getProbes(), track);
	    tracks.add(track);
	    currentID++;
	    size++;
	}
	return track;
    }
    
    /**
     * check if the track exists by the given trackID
     * @param trackID		ID to check
     * @return			if the index has the track
     */
    private boolean hasTrack(int trackID) {
	if (trackID < 0) {
	    return false;
	}
	return true;
    }
    
    /**
     * get the probe map from the index
     * @return	the probe map from the index
     */
    public ProbeMap getProbeMap() {
	return probeMap;
    }
    
    /**
     * get the size of the index
     * @return	numbers of tracks in the index
     */
    public int getSize() {
	return size;
    }
    
    /**
     * get the track with the given track ID
     * @param trackID	ID of the wanted track
     * @return		track with the given ID
     */
    public AudioTrack getTrack(int trackID) {
	if (!hasTrack(trackID)) {
	    return null;
	}
	Iterator<AudioTrack> it = tracks.iterator();
	while (it.hasNext()) {
	    AudioTrack thisTrack = it.next();
	    if (thisTrack.getTrackID() == trackID) {
		return thisTrack;
	    }
	}
	return null;
    }
      
    /**
     * play the track with the given track ID
     * @param trackID	ID of the track that needs 
     * 			to be played
     */
    public void playTrack(int trackID) {
	final AudioTrack playTrack = getTrack(trackID);
	if (playTrack != null) {
	    SwingWorker<Object, Object> playTask =
		    new SwingWorker<Object, Object>() {
		@Override
		public Object doInBackground() {
		    try {
			playTrack.play(0, playTrack.getDuration());
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
     * put the extracted probes into probe map
     * @param probes	probes extracted from the track
     * @param track	track that has the probes extracted from
     */
    public void putInProbeMap(ArrayList<Probe> probes, 
	    AudioTrack track) {
	Iterator<Probe> it = probes.iterator();
	while (it.hasNext()) {
	    Probe current = it.next();
	    ID_TimePair pair = 
		    new ID_TimePair(track.getTrackID(), current.getOccurTime());
	    probeMap.putInList(current, pair);
	}
    }
    
    /**
     * remove the track with the given track ID
     * @param trackID	ID of the track that needs
     * 			to be removed
     * @return 		if the removal of track is successful
     */
    public boolean removeTrack(int trackID) {
	if (!hasTrack(trackID)) {
	    return false;
	}
	Iterator<AudioTrack> it = tracks.iterator();
	while (it.hasNext()) {
	    AudioTrack thisTrack = it.next();
	    if (thisTrack.getTrackID() == trackID) {
		it.remove();
		probeMap.removeByID(trackID);
		size--;
		return true;
	    }
	}
	return false;
    }
    
    /**
     * Folder that can be created in an index to 
     * hold specific audio tracks.
     * 
     * @author Jiajie Li
     * CSE 260 PRJ 3
     * 10/25/14
     *
    class Folder {
	// audio tracks that are loaded into the folder
	private ArrayList<AudioTrack> folderTracks;
	
	// number of tracks in the folder
	private int folderSize;    
	
	// name of the folder
	private String name;
	
	/**
	 * create a folder with the name
	 * @param String	name of the folder
	 *
	public Folder(String name) {
	    folderTracks = new ArrayList<>();
	    folderSize = 0;
	    this.name = name;
	}
	
	/**
	 * load and add a track into the folder, add
	 * to the index as well
	 *
	public void addTrack(AudioTrack track) {
	    folderTracks.add(track);
	    folderSize++;
	}

	/**
	 * remove the track with the given track ID
	 * @param trackID	ID of the track that needs
	 * 			to be removed
	 *
	public void removeTrack(int trackID) {
	    
	}
    }*/
}
