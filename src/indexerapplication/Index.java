package indexerapplication;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import audioframe.AudioLoader;
import audioframe.Probe;
/**
 * Index of audio tracks. Index holds a list of
 * tracks and a mapping that takes probes to
 * trackID and time pair.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 2
 * 10/25/14
 */
public class Index implements Serializable{
    // used in serialization
    private static final long serialVersionUID = 3066367604849583368L;

    // list of folders
    private ArrayList<Folder> folders;
    
    // ID of the index
    private int indexID;
    
    // audio loader that loads audio track
    private AudioLoader loader;
    
    // probe map that takes probes as keys and 
    // trackID and time pair as values
    private ProbeMap probeMap;
    
    // size of tracks in the index
    private int size;
    
    // audio tracks that are loaded
    private ArrayList<AudioTrack> tracks;
    
    /**
     * create an Index object
     * @param indexID	ID of the index
     */
    public Index(int indexID) {
	
    }
    
    /**
     * add a folder with the given name
     * @param name	name of the folder
     */
    public void addFolder(String name) {
    }
    
    /**
     * load and add a track into the index
     */
    public void addTrack() {
	
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
	return null;
    }
      
    /**
     * play the track with the given track ID
     * @param trackID	ID of the track that needs 
     * 			to be played
     */
    public void playTrack(int trackID) {
	
    }
    
    /**
     * put the extracted probes into probe map
     * @param probes	probes extracted from the track
     */
    public void putInProbeMap(ArrayList<Probe> probes) {
	
    }
    
    /**
     * remove the track with the given track ID
     * @param trackID	ID of the track that needs
     * 			to be removed
     */
    public void removeTrack(int trackID) {
	
    }
    
    /**
     * Folder that can be created in an index to 
     * hold specific audio tracks.
     * 
     * @author Jiajie Li
     * CSE 260 PRJ 2
     * 10/25/14
     */
    class Folder {
	// audio tracks that are loaded into the folder
	private ArrayList<AudioTrack> folderTracks;
	
	// number of tracks in the folder
	private int folderSize;    
	
	/**
	 * create a folder with the name
	 * @param String	name of the folder
	 */
	public Folder(String name) {
	    
	}
	
	/**
	 * load and add a track into the folder, add
	 * to the index as well
	 */
	public void addTrack() {

	}

	/**
	 * remove the track with the given track ID
	 * @param trackID	ID of the track that needs
	 * 			to be removed
	 */
	public void removeTrack(int trackID) {

	}
    }
}
