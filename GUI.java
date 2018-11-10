// Created nov 10 sat 2018
// http://www.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
// https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/FloatControl.Type.html
// https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/FloatControl.html
// javac -d bin GUI.java Player.java && java -cp bin mypack.GUI

package mypack;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class GUI extends JFrame implements MouseListener, MouseWheelListener {

	private int x = 25;   // leftmost pixel in circle has this x-coordinate
	private int y = 40;   // topmost  pixel in circle has this y-coordinate
	private boolean mousePressed = false;
	private Ellipse2D circlePlay = new Ellipse2D.Double ( x + 25, y + 25, 100, 100 );
	public int vol = 250;
	public float volume = 0.011f;
	private Ellipse2D circleVolume = new Ellipse2D.Double ( x-7, y-7, 165, 165 );
	public static boolean status = false;
	private Player player = new Player ();
	private boolean isPlaying = false;
	private boolean isPause = false;

  public GUI () {
		setTitle ( "One Play" );
    setSize ( 200, 200 );
    setLocation ( 300, 300 );
    setBackground ( Color.DARK_GRAY );
    setDefaultCloseOperation ( EXIT_ON_CLOSE );
    setVisible ( true );
    addMouseListener ( this );
    addMouseWheelListener ( this );
		setPlayerVolume ();
    openFile ( "audio.wav" );
  }

  // paint is called automatically when program begins, when window is
  // refreshed and  when repaint() is invoked
  public void paint ( Graphics g ) {
	g.setColor ( Color.RED );
	g.fillOval ( x-7, y-7, 165, 165 );
	g.setColor ( Color.DARK_GRAY );
    g.fillArc ( x-7, y-7, 165, 165, -45, -90 );
    g.setColor ( Color.YELLOW );
    g.fillArc ( x-7, y-7, 165, 165, -45, vol );

    if ( mousePressed ) { g.setColor ( Color.LIGHT_GRAY ); }
    else { g.setColor ( Color.GRAY ); }

    g.fillOval ( x, y, 150, 150 );
    g.setColor ( Color.BLACK );
    Graphics2D g2 = ( Graphics2D ) g;
    g2.draw ( circlePlay );
  }

  // The next 4 methods must be defined, but you won't use them.
	public void mouseReleased ( MouseEvent e ) { }
	public void mouseEntered ( MouseEvent e ) { }
	public void mouseExited ( MouseEvent e ) { }
	public void mousePressed ( MouseEvent e ) { }

	// ROUND BUTTON
	public void mouseClicked ( MouseEvent e ) {
    if ( circlePlay.contains ( e.getPoint () ) ) {
		if ( !isPlaying ) {
			loopFile ();
			System.out.println ( "Click Play" );
			isPlaying = true;
			mousePressed = true;
		}
		else {
			stopFile ();
			System.out.println ( "Click Pause" );
			isPlaying = false;
			mousePressed = false;
		}
		repaint ();
    }
    else {
		if ( isPlaying ) {
			mousePressed = false;
			stopFile ();
			closeFile ();
			isPlaying = false;
			System.out.println ( "Click Stop" );
		}
		else {
			isPlaying = true;
			openFile ( "audio.wav" );
			System.out.println ( "Click Open" );
		}
		repaint ();
	  }
	}

  void eventOutputVolume ( String eventDescription ) {
        System.out.println ( eventDescription );
  }
	// VOLUME
	public void mouseWheelMoved ( MouseWheelEvent e ) {
		String message = "";
		if ( circleVolume.contains ( e.getPoint () ) ) {
			int notches = e.getWheelRotation ();
			if ( notches < 0 && vol > 0 ) {
				vol -= 5;
				if ( volume > 1.99f ) volume = 1.99f;
				else volume += 0.0397f;
				setVolume ();
				System.out.println ( volume );
				System.out.println ( (float) (Math.log(volume) / Math.log(10.0) * 20.0) );
				message = vol + " volume +\n";
			} else if ( vol < 270 ) {
				vol += 5;
				if ( volume < 0.05f ) volume = 0.01f;
				else volume -= 0.0397f;
				setVolume ();
				System.out.println ( volume );
				System.out.println ( (float) (Math.log(volume) / Math.log(10.0) * 20.0) );
				message = vol + " volume -\n";
			}
			repaint ();
			eventOutputVolume ( message );
		}
	}
	public void setPlayerVolume () { player.setVolume ( volume ); }
	void openFile ( String file ) { player.openFile ( "audio.wav" ); }
	void playFile () { player.getClip ().start (); }
	void loopFile () { player.getClip ().loop ( Clip.LOOP_CONTINUOUSLY ); } // 0, 1, ... int
	void stopFile () { player.getClip ().stop (); }
	void closeFile () { player.getClip ().close (); }
	void setVolume () {
			player.getFloatControl ().setValue ( ( float ) ( Math.log ( volume ) / Math.log ( 10.0 ) * 20.0 ) );
	}
	float getValueVolume () { return player.getFloatControl ().getValue (); }
	float getMaxValueVolume () { return player.getFloatControl ().getMaximum (); }
	float getMinValueVolume () { return player.getFloatControl ().getMinimum (); }

	public static void main ( String [] args ) {
		new GUI ();
	}
}

/*
*/
