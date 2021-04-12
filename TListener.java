/*
Rohan Shaiva
rohan.shaiva@tufts.edu
TListener.java

TListener object, adds a listener for the timer

*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

class TListener implements ActionListener {
    private Model model;
    private Controls controls;
    private Canvas canvas;

    public TListener(Model model, Controls controls, Canvas canvas) {
        // allows for access of canvas and controls
        this.model = model;
        this.controls = controls;
        this.canvas = canvas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (model != null && canvas.isDisplayable()) {
        // call method to animate.
            model.game_tick();
            controls.game_tick();
        }
        else {
            ((Timer) e.getSource()).stop();
        }
    }
}
