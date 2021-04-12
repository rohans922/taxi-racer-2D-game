/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Button.java

Button object, inherits JButton

*/

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Button extends JButton implements ActionListener {

private Model model;
private Canvas canvas;

    public Button (Model model, Canvas canvas, String text)
    {
        setText(text);
        // allows for access of canvas
        this.model = model;
        this.canvas = canvas;
    	addActionListener (this);
    }

    public void actionPerformed (ActionEvent e) {
        String text = getText();
        if (text == "Restart") {
            System.out.println("Restarting");
            model.reset(true);
        }
        if (text == "Start") {
            System.out.println("Started");
            model.start();
            this.setEnabled(false);
        }
        canvas.requestFocus();
    }
}
