// Created nov 07 wed 2018
// javac -d bin GUI.java && java -cp bin mypack.GUI

package mypack;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class GUI extends JFrame implements MouseListener, MouseWheelListener {

	private int x = 25;   // leftmost pixel in circle has this x-coordinate
	private int y = 40;   // topmost  pixel in circle has this y-coordinate
	private boolean mousePressed = false;
	private Ellipse2D circlePlay = new Ellipse2D.Double ( x + 25, y + 25, 100, 100 );
	public int vol = 250;
	private Ellipse2D circleVolume = new Ellipse2D.Double ( x-7, y-7, 165, 165 );

  public GUI () {
	setTitle ( "One Play" );
    setSize ( 200, 200 );
    setLocation ( 300, 300 );
    setBackground ( Color.DARK_GRAY );
    setDefaultCloseOperation ( EXIT_ON_CLOSE );
    setVisible ( true );
    addMouseListener ( this );
    addMouseWheelListener ( this );
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

  public void mouseClicked ( MouseEvent e ) {
    if ( circlePlay.contains ( e.getPoint () ) ) {
      System.out.println ( "Click Gray circle" );
      mousePressed = !mousePressed;
      repaint ();
    } // */
  }

  void eventOutput ( String eventDescription ) {
        System.out.println ( eventDescription );
    }

	public void mouseWheelMoved ( MouseWheelEvent e ) {
        String message = "";
        if ( circleVolume.contains ( e.getPoint () ) ) {
			int notches = e.getWheelRotation ();
        if ( notches < 0 && vol > 0 ) {
			vol -= 5;
            message = vol + " Mouse wheel moved UP\n";
        } else if ( vol < 270 ) {
			vol += 5;
            message = vol + " Mouse wheel moved DOWN\n";
        }
        repaint ();
        eventOutput( message );
		}
    }

  public static void main ( String [] args ) {
    new GUI ();
  }
}

/*
*/
