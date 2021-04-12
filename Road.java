/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Alert.java

Road object, inherits ImageIcon

*/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.*;

public class Road extends ImageIcon {

    private int x, y;
    private double incr;
    private Image img;

    public Road (int x, int y) {
        try {
            img = ImageIO.read(getClass().getResource("road.png"));
        } catch (IOException e) {
            System.out.println("Could not read image!");
        }
        setImage(img);
        this.x = x;
        this.y = y;
    }

    // getters and setters
    public int get_x () {
        return x;
    }
    public int get_y () {
        return y;
    }
    public void set_x (int x) {
        this.x = x;
    }
    public void set_y (int y) {
        this.y = y;
    }
    public double get_incr () {
        return incr;
    }
    public void set_incr (double incr) {
        this.incr = incr;
    }
}
