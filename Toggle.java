/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Toggle.java

Toggle object, extends JToggleButton, controls instruction display

*/

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Toggle extends JToggleButton implements ActionListener {

private Controls controls;
private Canvas canvas;

    public Toggle (Controls controls, Canvas canvas, String text) {
        setText(text);
        // allows for access of canvas and controls
        this.controls = controls;
        this.canvas = canvas;
    	addActionListener (this);
    }

    public void actionPerformed (ActionEvent e) {
        String text = getText();
        if (text == "How to Play" || text == "Hide") {
            System.out.println("Toggled Instructions");
            controls.toggle_instructions();
            canvas.requestFocus();
        }
    }
}
