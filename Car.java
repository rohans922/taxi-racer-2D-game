/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Car.java

Car object, inherits Vehicle

*/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.*;

public class Car extends Vehicle {

    private Image img;

    public Car () {

    }
    public Car (String side, int x, int y) {
        try {
            img = ImageIO.read(getClass().getResource("car.gif"));
        } catch (IOException e) {
            System.out.println("Could not read image!");
        }
        this.x = x;
        this.y = y;
        setImage(img);
    }

    public int get_x () {
        return x;
    }
    public int get_y () {
        return y;
    }

    // returns the left edge of the car given an x value

}
