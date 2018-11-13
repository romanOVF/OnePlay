// Created nov 13 tue 2018

package mypack;

import java.io.*;
import javax.sound.sampled.*;
import javax.sound.sampled.Line.Info;
import javax.swing.*;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.*;
import javazoom.jl.player.advanced.PlaybackListener;
import javazoom.jl.decoder.*;
import javazoom.jlgui.basicplayer.*;
import javazoom.jl.player.JavaSoundAudioDevice;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;


public class PlayTool {
	private FileInputStream fileInStream;
	private AdvancedPlayer playMP3;
	private float volume = 0.0f;
	private Thread playThread;
	String audioFile = "";
	long totalLength = 0;
	long pause = 0;
	boolean statusResume = false;
	BufferedInputStream bufferedInStream;
	Info source;

	// Constructor
	public PlayTool () {
		//source = Port.Info.SPEAKER;
    //source = Port.Info.LINE_OUT;
    source = Port.Info.HEADPHONE;
	}

	public void volume ( float volume ) {
		System.out.println ( "Hallo " + volume );
		if ( AudioSystem.isLineSupported ( source ) ) {
			try {
				if ( source != null )	{
					Port outline = ( Port ) AudioSystem.getLine ( source );
					outline.open ();
					FloatControl volControl = ( FloatControl ) outline.getControl ( FloatControl.Type.VOLUME );
					float newGain = Math.min ( Math.max ( volume, volControl.getMinimum () ), volControl.getMaximum () );
					volControl.setValue ( newGain );
				}
			}
			catch ( Exception e ) { System.out.println ( "h-m?" ); }
		}
		else System.out.println ( "source not supported, sorry!" );
	}

	Runnable runnablePlay = new Runnable () {
	  @Override
	  public void run () {
	    try {
	      fileInStream = new FileInputStream ( audioFile );
				bufferedInStream = new BufferedInputStream ( fileInStream );
	      playMP3 = new AdvancedPlayer ( bufferedInStream );
	      if ( !statusResume ) {	totalLength = fileInStream.available (); }
				else { fileInStream.skip ( totalLength - pause );	}
				//playMP3.setGain ( -30.0 );
				//volControl.setValue ( -30.0 );
	      playMP3.play ();
	    }
	    catch ( Exception e ) {}
	  }
	};

	public void openFile ( String audioFile ) {
		this.audioFile = audioFile;
	}
	public AdvancedPlayer getPlayer () { return playMP3; }
	public FileInputStream getFileInStream () { return fileInStream; }
	public Thread getPlayThread () { return playThread; }

	public void playFile () {
		if ( pause != 0 ) {
			new Thread ( runnablePlay ).start ();
		}
		else {
			try { fileInStream.skip ( totalLength ); }
			catch ( Exception e ) {}
			new Thread ( runnablePlay ).start ();
		}
	}
	public void playResumeFile () {
		statusResume = true;
		new Thread ( runnablePlay ).start ();
	}

	public void setPause () {
		try { pause = fileInStream.available (); }
		catch ( Exception e ) {}
		System.out.println ( "pause frame: " + pause );
	}
	public void stopFile () { playMP3.close (); }
}
