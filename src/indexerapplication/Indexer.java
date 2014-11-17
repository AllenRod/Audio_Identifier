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
 * CSE 260 PRJ 3
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
     * @throws IOException 
     */
    public void loadIndex() throws IOException {
	FileInputStream fin = null;
	ObjectInputStream ois = null;
	try {
	    fin = new FileInputStream("index.dat");
	    ois = new ObjectInputStream(fin);
	    Object obj = ois.readObject();
	    if (obj != null) {
		index = (Index) obj;
	    }
	    JOptionPane.showMessageDialog(frame.getFrame(), "Load Success");
	} catch (IOException e) {
	    JOptionPane.showMessageDialog(frame.getFrame(), e.getMessage());
	} catch (ClassNotFoundException e) {
	    JOptionPane.showMessageDialog(frame.getFrame(), e.getMessage());
	} finally {
	    if (ois != null) {
		ois.close();
	    }
	}
    }
    
    /**
     * save the index on memory, the file is named 
     * index.dat
     * @throws IOException 
     */
    public void saveIndex() throws IOException {
	FileOutputStream fout = null;
	ObjectOutputStream oos = null;
	try {
	    fout = new FileOutputStream("index.dat");
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
}
