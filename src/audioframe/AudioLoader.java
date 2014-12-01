package audioframe;
import java.io.*;
import java.net.URL;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

/**
 * Loader that loads audio file
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 4
 * 10/25/14
 */
public class AudioLoader {
    // file chooser of the loader
    JFileChooser fileChooser = new JFileChooser();
    
    // the loaded audio
    Audio currentAudio;
    
    // the parent JFrame
    JFrame frame;
    
    /**
     * Create an AudioLoader object
     * @param	frame	the frame that calls the load option
     */
    public AudioLoader(JFrame frame) {
	this.frame = frame;
    }
    
    /**
     * return the loaded audio from file chooser
     * @return	the selected and loaded audio
     */
    public Audio load() { 
	JFileChooser chooser = new JFileChooser();
	chooser.setDialogTitle("Select Audio File");
	chooser.setDialogType(JFileChooser.OPEN_DIALOG);
	chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
	    private AudioFileFormat.Type[] types = AudioSystem
		    .getAudioFileTypes();

	    public boolean accept(File f) {
		String name = f.getName();
		return (f.isDirectory() || supportedType(name));
	    }

	    private boolean supportedType(String name) {
		for (int i = 0; i < types.length; i++) {
		    if (name.endsWith(types[i].getExtension()))
			return true;
		}
		return false;
	    }

	    public String getDescription() {
		String descrip = "";
		for (int i = 0; i < types.length; i++) {
		    if (i != 0) {
			descrip += "; " + types[i];
		    } else {
			descrip += types[i];
		    }
		}
		return descrip;
	    }
	});
	int ret = chooser.showOpenDialog(frame);
	if (ret == JFileChooser.APPROVE_OPTION) {
	    try {
		File f = chooser.getSelectedFile();
		AudioInputStream inStream = AudioSystem.getAudioInputStream(f);
		currentAudio = Audio.fromStream(inStream, f.getName());
		inStream.close();
		return currentAudio;
	    } catch (IOException x) {
		error(x.getMessage());
	    } catch (UnsupportedAudioFileException x) {
		error(x.getMessage());
	    } catch (Throwable x) {
		error(x.getMessage());
		x.printStackTrace();
	    }
	}
	return null;
    }
    
    /**
     * load an Audio with the given file path
     * @param filePath		path of the file
     * @return			the loaded audio
     */
    public Audio loadFromFile(URL filePath) {
	try {
	    AudioInputStream inStream = AudioSystem.getAudioInputStream(filePath);
	    currentAudio = Audio.fromStream(inStream, filePath.getPath());
	    inStream.close();
	    return currentAudio;
	} catch (UnsupportedAudioFileException x) {
	    error(x.getMessage());
	    x.printStackTrace();
	} catch (IOException x) {
	    error(x.getMessage());
	    x.printStackTrace();
	}
	return null;
    }
    
    /**
     * show error message
     * @param errorMsg	the error message
     */
    public void error(String errorMsg) {
	JOptionPane.showMessageDialog(frame, errorMsg, 
		"Error Message", 0);
    }
}
