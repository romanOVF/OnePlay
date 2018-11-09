// Created nov 09 fri 2018

package mypack;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

// To play sound using Clip, the process need to be alive.
// Hence, we use a Swing application.
public class Player extends JFrame {
	private static Clip clip;
	private static AudioInputStream audioIn;
	private FloatControl gainControl;
	private float volume = 0.0f;

	// Constructor
	public Player () {}

	public void openFile ( String file ) {
		try {
			// Open an audio input stream.
			File soundFile = new File ( file ); //you could also get the sound file with an URL
			audioIn = AudioSystem.getAudioInputStream ( soundFile );
			// Get a sound clip resource.
			clip = AudioSystem.getClip ();
			// Open audio clip and load samples from the audio input stream.
			clip.open ( audioIn );
			gainControl = ( FloatControl ) clip.getControl ( FloatControl.Type.MASTER_GAIN );
			gainControl.setValue ( volume ); // Reduce volume by 10 decibels.
		}
		catch ( UnsupportedAudioFileException e ) { e.printStackTrace(); }
		catch ( IOException e ) { e.printStackTrace(); }
		catch ( LineUnavailableException e ) { e.printStackTrace(); }
		}

	public Clip getClip () { return clip; }
	public FloatControl getFloatControl () { return gainControl; }
}
