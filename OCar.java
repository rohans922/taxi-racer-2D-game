/*
Rohan Shaiva
rohan.shaiva@tufts.edu
OCar.java

OCar object, inherits Car, these are all the opposing cars

*/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Random;
import javax.imageio.*;


public class OCar extends Car {

    // allows for random color to be selected
    private String random_color;
    private String[] COLORS = {"carb.gif", "carg.gif", "caro.gif", "carp.gif", "carr.gif", "cart.gif", "cary.gif"};
    private Random rand;
    private int rand_num_one;
    private int rand_num_two;
    private String side;
    private int speed;
    private Image img;


    public OCar (String side, int x, int y) {
        rand = new Random();
        this.x = x;
        this.y = y;
        this.side = side;
        set_random();
    }

    public String get_random() {
        rand_num_one = rand.nextInt(COLORS.length - 1) + 0;
        return COLORS[rand_num_one];
    }

    public String get_color() {
        return random_color;
    }

    public String get_side() {
        return side;
    }

    public int get_speed() {
        return speed;
    }

    public int get_x () {
        return x;
    }
    public int get_y () {
        return y;
    }

    // called when creating a "new" car, but really just changing color/speed
    public void set_random() {
        random_color = get_random();
        try {
            img = ImageIO.read(getClass().getResource(random_color));
        } catch (IOException e) {
            System.out.println("Could not read image!");
        }
        setImage(img);
        set_speed();
    }

    // speed randomly set
    public void set_speed() {
        rand_num_two = rand.nextInt(5) + 0;
        if (rand_num_two == 0)
            speed = 2;
        else if (rand_num_two == 1)
            speed = 3;
        else
            speed = 4;
    }
}
