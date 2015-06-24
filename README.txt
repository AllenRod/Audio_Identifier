Upload UML diagrams into folder UML_Diagrams. All files were created with Violet UML Editor, and saved as HTML files.
UML diagrams were created during designing phases, and they only reflect early thoughts and ideas.

Used external library jtransform-2.3.jar by Piotr Wendykier of Emory University. Used Fast Fourier Transform to analyze 
signals and create power spectrum of the signal's frequency.

This project can process a small library of audio tracks and construct small database for identification, correctly 
identify the track to which a short clip belongs and the time offset from the beginning of the track at which the clip 
starts, and provide some GUI tools that allow the user to experiment with variations on the basic scheme used to index 
the audio tracks.

Indexer Application: Create library and index audio files
	Run Indexer.java
	
Matcher Application: Match single audio file with index in library
	Run Matcher.java
	
Accept audio files in Wave, Au, and Aiff format.
