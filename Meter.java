/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Meter.java

Meter object, inherits JPanel, acts as custom speed display

*/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.*;

public class Meter extends JPanel {

    private int value;
    private ImageIcon icon;
    private Image img;

    public Meter () {
        value = 0;
        try {
            img = ImageIO.read(getClass().getResource("grad.png"));
        } catch (IOException e) {
            System.out.println("Could not read image!");
        }
        icon = new ImageIcon();
        icon.setImage(img);

    }

    // allows for the value to be updated
    public void setValue(int value) {
        this.value = value;
    }

    // paints the meter using a background image, a moving rectangle, and a white border
    public void paintComponent (Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);
        this.setOpaque(true);
        icon.paintIcon(this, g, 0, 0);
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(value, 0, 180, 60);

        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(45, 0, 45, 60);

        g2.setStroke(new BasicStroke(10));
        g2.setColor(Color.WHITE);
        g2.drawRect(5, 5, 170, 50);
    }

}
