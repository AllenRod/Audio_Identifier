package indexerapplication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

/**
 * Indexer that is able to manage list of 
 * indexes.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 4
 * 10/25/14
 */
public class Indexer {
    // current index
    private Index index;
    
    // GUI of indexer
    private IndexerGUI frame;
    
    
    /**
     * create an Indexer object
     */
    public Indexer() {
	index = new Index();
	frame = new IndexerGUI(this);
    }
    
    /**
     * get the current index 
     * @return		current index
     */
    public Index getIndex() {
	return index;
    }
    
    /**
     * load an existing index from memory, the file is
     * named index.dat
     * @param fileName		path of file
     * @throws IOException 
     * @return 			the loading process is success or not
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
	    JOptionPane.showMessageDialog(frame.getFrame(), "Load Success");
	    loaded = true;
	} catch (IOException e) {
	    JOptionPane.showMessageDialog(frame.getFrame(), e.getMessage());
	} catch (ClassNotFoundException e) {
	    JOptionPane.showMessageDialog(frame.getFrame(), e.getMessage());
	} finally {
	    if (ois != null) {
		ois.close();
	    }
	}
	return loaded;
    }
    
    /**
     * save the index on memory, the file is named 
     * index.dat
     * @param fileName		path of file
     * @throws IOException 
     */
    public void saveIndex(String fileName) throws IOException {
	FileOutputStream fout = null;
	ObjectOutputStream oos = null;
	try {
	    fout = new FileOutputStream(fileName);
	    oos = new ObjectOutputStream(fout);
	    oos.writeObject(index);
	    JOptionPane.showMessageDialog(frame.getFrame(), "Save Success");
	} catch (FileNotFoundException e) {
	    JOptionPane.showMessageDialog(frame.getFrame(), e.getMessage());
	} catch (IOException e) {
	    JOptionPane.showMessageDialog(frame.getFrame(), e.getMessage());
	} finally {
	    if (oos != null) {
		oos.close();
	    }
	}
    }
    
    /**
     * main method of the program
     */
    public static void main(String[] args) {
	Indexer indexerApp = new Indexer();
    }
}
