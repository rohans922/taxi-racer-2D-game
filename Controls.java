/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Controls.java

Controls object, will have the controls on the right

*/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.*;

public class Controls extends JPanel implements ActionListener {

    private static final int METER_HEIGHT = 50;

    private Canvas canvas;
    private Model model;
    private Meter speed_meter;
    private Button start;
    private Button restart;
    private Toggle toggle;
    private JLabel instructions;
    private ImageIcon speed;
    private JLabel speed_label;

    private GridBagLayout gridbag;;
    private GridBagConstraints c;
    private Insets insets;
    private Boolean show;
    private String default_str;
    private String instr_str;

    public Controls (Model model, Canvas canvas) {
        // strings for instructions, enabled and disabled
        default_str = "<html><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br></html>";
        instr_str = "<html><p><strong>OBJECTIVE:</strong></p><p>You are a busy taxi driver! Stop next to customers and get them to their destinations. Avoid traffic and stop for the customer! Good luck!</p><br><p><strong>CONTROLS:</strong></p><p>Use <strong>'a'</strong> and <strong>'d'</strong> to switch lanes. Use&nbsp;<strong>shift</strong>&nbsp;to slow down or stop. Use <strong>'w'</strong> to speed up when possible, but don't max out. Use <strong>space</strong> to honk, which only works sometimes; your honk is broken!</p><p>To restart, hit <strong>enter</strong> or click restart.</p></html>";
        show = true;

        setPreferredSize(new Dimension(200, 600));

        // allows for access of canvas
        this.canvas = canvas;
        this.model = model;
        // layout management
        gridbag = new GridBagLayout();
        c = new GridBagConstraints();
        insets = new Insets(10, 10, 10, 10);

        setLayout (gridbag);

        // instantiates all objects for this panel, passing in appropriate values
        speed_meter = new Meter();
        start = new Button (model, canvas, "Start");
        restart = new Button (model, canvas, "Restart");
        toggle = new Toggle (this, canvas, "How to Play");
        instructions = new JLabel(default_str);
        speed = new ImageIcon(getClass().getResource("speed.png"));
        speed_label = new JLabel(speed);

        // adds objects to panel
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        add (speed_label, c);
        c.ipady = METER_HEIGHT;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = insets;
        add (speed_meter, c);
        insets.set(0, 10, 0, 10);
        c.insets = insets;
        c.ipady = 10;
        c.gridx = 0;
        c.gridy = 2;
        add (toggle, c);
        insets.set(2, 15, 0, 15);
        c.insets = insets;
        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 3;
        add (instructions, c);
        c.ipady = 5;
        c.gridwidth = 3;
        c.gridx = 1;
        c.gridy = 11;
        add (start, c);
        c.gridy = 12;
        add (restart, c);
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        this.setOpaque(true);
        this.setBackground(Color.LIGHT_GRAY);
    }

    public void actionPerformed(ActionEvent e){

    }

    // this runs every game_tick, called through TListener
    public void game_tick() {
        // allows for the meter to be updated constantly
        speed_meter.setValue(model.get_meter());
        speed_meter.repaint();
    }

    public void toggle_instructions() {
        // shows and hides instructions
        if (show) {
            instructions.setText(instr_str);
            toggle.setText("Hide");
            show = false;
        }
        else {
            instructions.setText(default_str);
            toggle.setText("How to Play");
            show = true;
        }
    }
}
