// Created nov 16 fri 2018
// reset && javac -d bin -cp lib/tritonus_share.jar:lib/commons-logging-1.2.jar:lib/mp3spi1.9.4.jar:lib/jl1.0.jar:lib/basicplayer3.0.jar:lib/jlayer-1.0.1.jar *.java && java -cp lib/tritonus_share.jar:lib/commons-logging-1.2.jar:lib/mp3spi1.9.4.jar:lib/jl1.0.jar:lib/basicplayer3.0.jar:lib/jlayer-1.0.1.jar:bin mypack.GUI

package mypack;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class GUI extends JFrame implements MouseListener, MouseWheelListener {

	private Ellipse2D circleOpen = new Ellipse2D.Double ( 29, 44, 142, 142 );
	private Ellipse2D circlePlay = new Ellipse2D.Double ( 50, 65, 100, 100 );
	private Ellipse2D circleVolume = new Ellipse2D.Double ( 18, 33, 165, 165 );
	private PlayTool playTool = new PlayTool ();
	private int vol = 135;
	private float volume = 0.5f;
	private boolean mousePressed = false;
	private boolean isPlaying = false;
	private boolean isResume = false;
	private String audioFile = "demo.mp3";

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
		g.fillOval ( 18, 33, 165, 165 );
		g.setColor ( Color.DARK_GRAY );
    g.fillArc ( 18, 33, 165, 165, -45, -90 );
    g.setColor ( Color.YELLOW );
    g.fillArc ( 18, 33, 165, 165, -45, vol );

    if ( mousePressed ) { g.setColor ( Color.LIGHT_GRAY ); }
    else { g.setColor ( Color.GRAY ); }

    g.fillOval ( 25, 40, 150, 150 );
    /*g.setColor ( Color.BLACK );
    Graphics2D g2 = ( Graphics2D ) g; // to display boundary contours
    g2.draw ( circlePlay );
    g2.draw ( circleOpen ); // */

		if ( mousePressed ) {
			g.setColor ( Color.GRAY );
			g.fillOval ( 50, 65, 100, 100 );
		}
		else {
			g.setColor ( Color.LIGHT_GRAY );
			g.fillOval ( 50, 65, 100, 100 );
		}
  }

  // The next 4 methods must be defined, but you won't use them.
	public void mouseReleased ( MouseEvent e ) { }
	public void mouseEntered ( MouseEvent e ) { }
	public void mouseExited ( MouseEvent e ) { }
	public void mousePressed ( MouseEvent e ) { }

	// ROUND BUTTON
	public void mouseClicked ( MouseEvent e ) {
    if ( !( !circlePlay.contains ( e.getPoint () ) && circleOpen.contains ( e.getPoint () ) ) ) {
			if ( !isPlaying ) {
				if ( !isResume ) {
					isResume = true;
					openFile ( audioFile );
					playAudio ();
				}
				else if ( isResume ) { // isResume
					isResume = true;
					playResume ();
				}
				System.out.println ( "Click Play" );
				isPlaying = true;
				mousePressed = true;
			}
			else if ( isPlaying ) { // isPlaying
				pauseAudio ();
				System.out.println ( "Click Pause" );
				isPlaying = false;
				mousePressed = false;
			}
    }
    if ( !circlePlay.contains ( e.getPoint () ) && circleOpen.contains ( e.getPoint () ) ) {
			if ( isPlaying ) {
				mousePressed = false;
				stopAudio ();
				isPlaying = false;
				System.out.println ( "Click Stop" );
			}
			else if ( isPlaying ) { // !isPlaying
				openFile ( audioFile );
				isResume = false;
				isPlaying = true;
				System.out.println ( "Click Open" );
			}
	  }
		repaint ();
	}

  void eventOutputVolume ( String eventDescription ) {
        System.out.println ( eventDescription );
  }
	// VOLUME
	public void mouseWheelMoved ( MouseWheelEvent e ) {
		String message = "";
		//if ( circleVolume.contains ( e.getPoint () ) && !circleOpen.contains ( e.getPoint () ) ) {
		if ( !circleOpen.contains ( e.getPoint () ) ) {
			int notches = e.getWheelRotation ();
			if ( notches < 0 && vol > 0 ) {
				vol -= 5;
				volume += 0.018f;
				setVolume ( volume );
				System.out.println ( volume );
				message = vol + " volume +\n";
			} else if ( vol < 270 ) {
				vol += 5;
				volume -= 0.018f;
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
	private void pauseAudio () { playTool.pauseFile ();	}
	private void playResume () { playTool.resumeFile (); }
	private void stopAudio () { playTool.stopFile (); }
	private void setPlayerVolume () {};
	private void setVolume ( float volume ) { playTool.volumeAudio ( volume ); };

	public static void main ( String [] args ) {
		new GUI ();
	}
}

/*
*/
