// Created nov 13 tue 2018
// reset && javac -d bin GUI.java PlayTool.java && java -cp bin mypack.GUI
// reset && javac -d bin -cp jlayer-1.0.1.jar *.java && java -cp jlayer-1.0.1.jar:bin mypack.GUI
// reset && javac -d bin -cp basicplayer3.0.jar:jlayer-1.0.1.jar *.java && java -cp basicplayer3.0.jar:jlayer-1.0.1.jar:bin mypack.GUI


package mypack;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class GUI extends JFrame implements MouseListener, MouseWheelListener {

	private int x = 25;   // leftmost pixel in circle has this x-coordinate
	private int y = 40;   // topmost  pixel in circle has this y-coordinate
	private Ellipse2D circlePlay = new Ellipse2D.Double ( x + 25, y + 25, 100, 100 );
	private Ellipse2D circleVolume = new Ellipse2D.Double ( x-7, y-7, 165, 165 );
	private PlayTool playTool = new PlayTool ();
	private int vol = 250;
	private float volume = 0.0f;
	private static boolean status = false;
	private boolean mousePressed = false;
	private boolean isPlaying = false;
	private boolean isPause = false;
	private boolean isResume = false;
	public String audioFile = "demo.mp3";

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
    openFile ( audioFile );
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
			if ( !isResume ) {
				isResume = !isResume;
				openFile ( audioFile );
				playAudio ();
			}
			else { // isResume
				isResume = !isResume;
				playResume ();
			}
			System.out.println ( "Click Play" );
			isPlaying = true;
			mousePressed = true;
		}
		else { // isPlaying
			setPause ();
			stopAudio ();
			System.out.println ( "Click Pause" );
			isPlaying = false;
			mousePressed = false;
		}
		repaint ();
    }
    else { // !( circlePlay.contains ( e.getPoint () ) )
		if ( isPlaying ) {
			mousePressed = false;
			stopAudio ();
			isPlaying = false;
			System.out.println ( "Click Stop" );
		}
		else { // !isPlaying
			isPlaying = true;
			//openFile ( audioFile );
			playAudio ();
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
				volume = -vol / 10.0f;
				setVolume ( volume );
				System.out.println ( volume );
				message = vol + " volume +\n";
			} else if ( vol < 270 ) {
				vol += 5;
				volume = -vol / 10.0f;
				setVolume ( volume );
				System.out.println ( volume );
				message = vol + " volume -\n";
			}
			repaint ();
			eventOutputVolume ( message );
		}
	}
	private void openFile ( String file ) { playTool.openFile ( audioFile ); }
	private void playAudio () { playTool.playFile ();	}
	private void pauseAudio () { playTool.setPause ();	}
	private void playResume () { playTool.playResumeFile (); }
	private void stopAudio () { playTool.stopFile (); }
	private void setPause () { playTool.setPause (); }
	private void setPlayerVolume () {};
	private void setVolume ( float volume ) { playTool.volume ( volume ); };

	public static void main ( String [] args ) {
		new GUI ();
	}
}

/*
*/
