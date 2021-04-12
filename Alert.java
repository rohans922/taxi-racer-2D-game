/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Alert.java

Alert object, inherits ImageIcon

*/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.*;

public class Alert extends ImageIcon {

    private int x, y;
    private boolean show;
    private Image img;

    public Alert (String location, int x, int y, boolean show) {
        try {
            img = ImageIO.read(getClass().getResource(location));
        } catch (IOException e) {
            System.out.println("Could not read image!");
        }
        setImage(img);
        this.x = x;
        this.y = y;
        System.out.println(x);
        System.out.println(y);
        this.show = show;
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
    public boolean get_show () {
        return show;
    }
    public void set_show (boolean show) {
        this.show = show;
    }
}
