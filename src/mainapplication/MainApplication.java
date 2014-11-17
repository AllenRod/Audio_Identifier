package mainapplication;
import clientapplication.Matcher;
import indexerapplication.Indexer;

/**
 * Main application that holds both client and indexer
 * applications. This class also constructs GUI objects.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 3
 * 10/25/14
 */
public class MainApplication {  
    // the indexer application
    Indexer indexerApp;
    
    // the matcher application
    Matcher matchApp;
    
    /**
     * create a MainApplication object
     */
    public MainApplication() {
	indexerApp = new Indexer();
	matchApp = new Matcher(indexerApp);
    }
    
    /**
     * main method of the program
     */
    public static void main(String[] args) {
	MainApplication audioIdentifier = new MainApplication();
    }

}
