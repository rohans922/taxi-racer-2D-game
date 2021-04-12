/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Vehicle.java

Vehicle object, inherits ImageIcon

*/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.*;

public class Vehicle extends ImageIcon {

    protected int x, y;
    private String side;
    private Image img;

    public Vehicle () {

    }

    public Vehicle (String side, int x, int y) {
        this.side = side;
        this.x = x;
        this.y = y;
    }


    public String get_color() {
        return " ";
    }

    public int get_speed() {
        return 0;
    }

    // called when creating a "new" car, but really just changing color/speed
    public void set_random() {

    }

    // speed randomly set
    public void set_speed() {

    }

    // getters and setters
    public int get_x () {
        return x;
    }
    public int get_y () {
        return y;
    }
    public String get_side () {
        return side;
    }
    public void set_side (String side) {
         this.side = side;
    }
    public void set_x (int x) {
        this.x = x;
    }
    public void set_y (int y) {
        this.y = y;
    }
}
