/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Game.java

This class includes the main function and acts as a runner.

*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Game extends JFrame {

    // Delay is the delay between each frame
    private static final int DELAY = 35;

    protected Canvas canvas;
    protected Controls controls;
    protected Model model;

    public static void main (String [] args) {
	new Game ();

    }

    public Game () {
    	// Window setup
    	setLocation (100, 100);
    	setSize (1000, 600);
        // setResizable(false);
    	setDefaultCloseOperation (EXIT_ON_CLOSE);
    	setLayout (new BorderLayout());

        // these two elements make up the main JFrame
        model = new Model ();
        canvas = new Canvas (model);
        model.pass(canvas);
        controls = new Controls(model, canvas);

        // this is the listener that allows for a timer to run, creating a game tick
        TListener listener = new TListener(model, controls, canvas);

        // adds to JFrame
    	add (canvas, BorderLayout.CENTER);
        add (controls, BorderLayout.EAST);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setVisible (true);

        // creates the Timer object itself
        new Timer(DELAY, listener).start();

    }

}
