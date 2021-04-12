/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Person.java

Person object, extends ImageIcon

*/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;

public class Person extends ImageIcon {

    private boolean in_transit;
    private Image wave;
    private Image stand;
    private String pose;
    private int x, y;

    public Person (int x, int y) {
        this.x = x;
        this.y = y;
        in_transit = false;
        try {
            wave = ImageIO.read(getClass().getResource("person.gif"));
        } catch (IOException e) {
            System.out.println("Could not read image!");
        }
        try {
            stand = ImageIO.read(getClass().getResource("person_standing.gif"));
        } catch (IOException e) {
            System.out.println("Could not read image!");
        }
        setImage(wave);
        pose = "wave";
    }

    public void pick_up () {
        in_transit = true;
    }

    public void drop_off () {
        in_transit = false;
    }

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

    // switches the images depending on pose
    public void set_pose(String pose) {
        this.pose = pose;
        if (pose == "wave")
            setImage(wave);
        if (pose == "stand")
            setImage(stand);
    }

}
