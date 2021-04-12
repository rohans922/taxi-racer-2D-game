/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Canvas.java

Canvas object, will have most of the game within this class

Mostly involves member variables that determine speeds and locations and methods which
control them and change them according to the mechanics of the game

*/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Random;
import javax.imageio.*;

public class Canvas extends JPanel implements ActionListener, KeyListener {

    private Model model;

    public Canvas (Model model) {

        this.model = model;
        setLayout(null);
        setBorder (new LineBorder(Color.RED, 2));

        // allows for a keyboard listener
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);


    }

    public void paintComponent (Graphics g) {
        // paints all the objects
        super.paintComponent(g);
        model.draw(g);
        this.setOpaque(true);
    }

    // this runs every game_tick, called through TListener

    public void actionPerformed(ActionEvent e){

    }

    // Keyboard actions
    public void keyPressed (KeyEvent e){
        int c = e.getKeyCode();
        model.key_press(c);
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {
        // when various keys are released, different actions occur
        int c = e.getKeyCode();
        model.key_release(c);
    }
}
