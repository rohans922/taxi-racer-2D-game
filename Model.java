/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Model.java

Model object, will have most of the game within this class

Mostly involves member variables that determine speeds and locations and methods which
control them and change them according to the mechanics of the game. Then a
function is called from Canvas that allows for its paint function to run.

*/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Random;
import javax.imageio.*;

public class Model {

    private Canvas canvas;

    // constant values
    private static final int ANIMATION_DELAY = 100;
    private static final int DEFAULT = 200;
    private static final double MAX_SPEED = 3.5;
    private static final double MIN_SPEED = 1;
    private static final int AVG_METER = 30;
    private static final int MAX_INCREMENT = 15;
    private static final int LEFTROAD = 250;
    private static final int RIGHTROAD = 545;
    private static final int LEFTPERSON = 190;
    private static final int RIGHTPERSON = 550;

    // major objects
    protected Road road_one, road_two;
    protected Vehicle main_car, l_opposing, r_opposing;
    protected Person person;

    // other objects
    protected Alert honk_icon;
    protected Alert alert_icon;
    protected Alert points_icon;
    protected Alert crash_icon;
    protected Alert customer_icon;
    protected JLabel score_label;
    private Random rand;

    // game_timer constantly runs throughout the game, used for score management
    private int game_timer;

    // determines whether game is running
    private boolean start;

    // crash related variables
    private boolean crash;
    private boolean resetting;
    private int reset_count;

    // score related variables
    private String score_str;
    private int score;

    // road related variables
    private int increment, difference, stopping;
    private boolean reset;

    // acceleration related variables
    private double accel_factor, acleft, acright, speed, honk_l, honk_r, speedinc, stop;
    private boolean switch_incr = false;

    // extra graphics related variables
    private int honk_timer;
    private int alert_timer;
    private int points_timer;

    // main car related variables
    private char selection, lastsel;

    // meter calculation related variables
    private int count, stop_count;
    private double meter;
    private boolean maxed;

    private double left_incr, right_incr;
    private boolean started;

    // the person/customer related variables
    private String pside;
    private boolean is_there;
    private boolean is_in;
    private int wait;
    private int buffer;
    private int direction;
    private boolean just_off;

    // values that gets passed to other classes
    private double fixed_value;
    protected int value;

    public Model () {
        // All default values are set below this:
        game_timer = 0;

        score = 0;
        score_str = "SCORE: " + String.valueOf(score);

        increment = MAX_INCREMENT;
        difference = 1;
        stopping = 0;
        reset = false;

        start = false;
        crash = false;
        resetting = false;
        reset_count = 0;

        accel_factor = 1.0;
        acleft = 1.0;
        acright = 1.0;
        speed = 1.0;
        honk_l = 1.0;
        honk_r = 1.0;
        speedinc = 1.0;
        stop = 1;

        lastsel = 'a';

        count = 0;
        stop_count = 0;
        meter = 0;
        maxed = false;

        honk_timer = 0;
        alert_timer = 0;
        points_timer = 0;

        started = false;

        direction = 1;
        pside = "-";
        is_there = false;
        is_in = false;
        wait = 0;
        buffer = 0;
        just_off = false;

        fixed_value =  0;
        value = 0;

        // objects are instantiated with appropriate parameters
        road_one = new Road(0, -1127);
        road_two = new Road(0, -2854);
        main_car = new Car("main", 280, 290);
        l_opposing = new OCar("left", 280, -800);
        r_opposing = new OCar("right", 420, -200);
        person = new Person(LEFTPERSON, 650);
        honk_icon = new Alert("honk.png", (main_car.get_x() + 15), -300, false);
        alert_icon = new Alert("alert.gif", LEFTPERSON, -300, false);
        points_icon = new Alert("points.png", LEFTPERSON, -300, false);
        crash_icon = new Alert("crashed.png", 0, 900, false);
        customer_icon = new Alert("customer.png", 255, -250, true);
        score_label = new JLabel(score_str);
        score_label.setFont (score_label.getFont ().deriveFont (30.0f));
        score_label.setForeground(Color.WHITE);
        score_label.setBounds(600, 2, 200, 50);

        rand = new Random();
    }

    public void pass (Canvas canvas) {
        this.canvas = canvas;
        canvas.add(score_label);
    }

    public void draw (Graphics g) {
        // paints all the objects
        road_one.paintIcon(canvas, g, road_one.get_x(), road_one.get_y());
        road_two.paintIcon(canvas, g, road_two.get_x(), road_two.get_y());
        main_car.paintIcon(canvas, g, main_car.get_x(), main_car.get_y());
        l_opposing.paintIcon(canvas, g, l_opposing.get_x(), l_opposing.get_y());
        r_opposing.paintIcon(canvas, g, r_opposing.get_x(), r_opposing.get_y());
        person.paintIcon(canvas, g, person.get_x(), person.get_y());
        honk_icon.paintIcon(canvas, g, honk_icon.get_x(), honk_icon.get_y());
        alert_icon.paintIcon(canvas, g, alert_icon.get_x(), alert_icon.get_y());
        points_icon.paintIcon(canvas, g, points_icon.get_x(), points_icon.get_y());
        crash_icon.paintIcon(canvas, g, crash_icon.get_x(), crash_icon.get_y());
        if (is_in)
            customer_icon.paintIcon(canvas, g, 255, 460);
        else
            customer_icon.paintIcon(canvas, g, 255, -250);

    }

    // this runs every game_tick, called through TListener
    public void game_tick() {
        // score updated
        score_str = "SCORE: " + String.valueOf(score);
        score_label.setText(score_str);
        canvas.repaint();
        if (start) {
            game_timer++;
            if (game_timer % 5 == 0 && game_timer != 0 && stopping != 1)
                score++;
            // all functions are called that should run every game tick
            manage_stop_speed();
            manage_lr();
            manage_limits();
            loop_road(increment);
            manage_ocars(l_opposing);
            manage_ocars(r_opposing);
            manage_people();
            manage_other_graphics();
            detect_crash();
        }
        honk_icon.set_x(main_car.get_x() + 15);
        // value is scaled to fit pixel value of meter
        fixed_value =  1.5 * meter;
        value = (int)fixed_value;
    }

    public int get_meter() {
        return value;
    }

    public void start() {
        start = true;
    }

    // detects a crash, and if so, changes appropriate variables
    public void detect_crash() {
        // hit from left
        if (main_car.get_x() <= (l_opposing.get_x() + 96) && (main_car.get_y() <= (l_opposing.get_y() + 182) && (main_car.get_y() + 170) >= l_opposing.get_x()))
            crash = true;
        else if ((main_car.get_x() + 96) >= r_opposing.get_x() && (main_car.get_y() <= (r_opposing.get_y() + 182) && (main_car.get_y() + 170) >= r_opposing.get_y()))
            crash = true;
        if (crash) {
            score_label.setBounds(320, 310, 200, 50);
            alert_icon.set_y(-300);
            points_icon.set_y(-300);
            honk_icon.set_y(-300);
            crash_icon.set_y(0);
            // stops the game
            start = false;
            stop = 0;
            increment = 0;
            if (meter > 0)
                meter -= 2;
            if (meter < 0)
                meter = 0;
        }
    }

    // manages the stopping of the main car (technically the road behind it)
    public void manage_stop_speed() {
        if (resetting) {
            reset_count++;
            if (reset_count >= 30) {
                resetting = false;
                reset_count = 0;
                stop_count = 0;
                stopping = 2;
            }
        }

        if (stopping == 1 && increment >= 0) {
            switch_incr = true;
            if (meter > 0)
                meter -= 2;
            if (meter < 0)
                meter = 0;
            difference++;
            if (difference <= 10 && difference % 5 == 0) {
                increment -= 1;
            }
            else if (difference > 10 && difference % 2 == 0) {
                increment -= 5;
            }
            if (meter > 0)
                meter -= 2;
            if (meter < 0)
                meter = 0;
        }
        if (stopping == 2) {
            switch_incr = false;
            difference++;
            if (difference <= 10 && difference % 5 == 0 && increment <= MAX_INCREMENT) {
                increment += 1;
            }
            else if (difference > 10 && difference % 2 == 0 && increment <= MAX_INCREMENT) {
                increment += 5;
            }
        }

        if (increment < 0) {
            increment = 0;
        }
        if (increment == 0 && stopping != 2)
            difference = 1;
        if (increment > MAX_INCREMENT) {
            increment = MAX_INCREMENT;
            stopping = 0;
            difference = 1;
        }
    }

    // manages the left and right movements of the car, and if nothing is pressed
    public void manage_lr() {

        if (selection == 'a') {
            if (main_car.get_x() > (LEFTROAD + 30)) {
                main_car.set_x(main_car.get_x() - 10);
            }
        }
        if (selection == 'd') {
            if ((main_car.get_x() + 96) < (RIGHTROAD - 30)) {
                main_car.set_x(main_car.get_x() + 10);
            }
        }
        if (selection == 'X' && stop != 0 && !crash) {

            if (lastsel == 'a' && main_car.get_x() > (LEFTROAD + 30)) {
                main_car.set_x(main_car.get_x() - 10);
            }
            if (lastsel == 'd' && (main_car.get_x() + 96) < (RIGHTROAD - 30)) {
                main_car.set_x(main_car.get_x() + 10);
            }
        }
        if (lastsel == 'a' && main_car.get_x() > (LEFTROAD + 30) && main_car.get_x() < (LEFTROAD + 100) && honk_r < 0) {
            honk_r = 1.5;
        }
        if (lastsel == 'd' && (main_car.get_x() + 96) < (RIGHTROAD - 30) && (main_car.get_x() + 96) > (RIGHTROAD - 100) && honk_l < 0) {
            honk_l = 1.5;
        }
    }

    // manages the limitations on various values and constrains them
    public void manage_limits() {
        if (meter == 120)
            maxed = true;
        if (speed == MAX_SPEED) {
            speedinc = 1.5;
            if (honk_l == -1.5) {
                honk_l = 0.5;
            }
            if (honk_r == -1.5) {
                honk_r = 0.5;
            }
            count++;
            if (count >= 100) {
                speedinc = 1;
                speed = MIN_SPEED;
            }
            if (!maxed)
                meter += 1;
        }
        if (speed == MIN_SPEED)
        {
            speedinc = 1;
            if (!maxed && meter > 30)
                meter -= 1;

        }
        if (meter < AVG_METER && stopping != 1)
            meter += 0.25;
        if (maxed && meter > AVG_METER) {
            meter -= 0.5;
        }
        if (meter == 30)
            maxed = false;
        if (stopping == 1) {
            stop_count++;
            if (stop_count >= 45) {
                stopping = 2;
            }
        }
    }

    // manages the other cars and their movement
    public void manage_ocars(Vehicle ocar_move) {
        int ocar_incr = ocar_move.get_speed();
        // determines what happens when they move off the screen
        // left
        if (ocar_move.get_side() == "left") {
            if (ocar_move.get_y() >= 0)
                started = true;
            if (ocar_move.get_y() > 650 || (ocar_move.get_y() > 584 && stopping == 1)) {
                int rand_start = rand.nextInt(800) + DEFAULT;
                rand_start *= -1;
                ocar_move.set_y(rand_start);
                ocar_move.set_random();
                honk_l = 1;
            }
            if (ocar_move.get_y() < -500 && started) {
                if (honk_l != 1)
                    honk_l = 1;
                acleft = 0;
                int rand_start = rand.nextInt(800) + DEFAULT;
                rand_start *= -1;
                ocar_move.set_y(rand_start);
                ocar_move.set_random();
                reset(false);
            }
            left_incr = (ocar_incr * stop * acleft * speed * honk_l);
            if (switch_incr) {
                ocar_move.set_y(ocar_move.get_y() + (int)((left_incr + MAX_INCREMENT - (MAX_INCREMENT / 5)) * -1));
            }
            else {
                ocar_move.set_y(ocar_move.get_y() + (int)left_incr);
            }
        }
        // right
        if (ocar_move.get_side() == "right") {
            if (ocar_move.get_x() > 650 || (ocar_move.get_y() > 584 && stopping == 1)) {
                int rand_start = rand.nextInt(800) + DEFAULT;
                rand_start *= -1;
                ocar_move.set_y(rand_start);
                ocar_move.set_random();
                honk_r = 1;
            }
            if (ocar_move.get_y() < -500) {
                if (honk_r != 1)
                    honk_r = 1;
                acright = 0;
                int rand_start = rand.nextInt(800) + DEFAULT;
                rand_start *= -1;
                ocar_move.set_y(rand_start);
                ocar_move.set_random();
                reset(false);
            }
            right_incr = (ocar_incr * stop * acright * speed * honk_r);
            if (switch_incr) {
                ocar_move.set_y(ocar_move.get_y() + (int)((right_incr + MAX_INCREMENT - (MAX_INCREMENT / 5)) * -1));
            }
            else {
                ocar_move.set_y(ocar_move.get_y() + (int)right_incr);
            }
        }
    }

    // manages the people and how/ they should be created - wait/buffer variables control the small animations
    public void manage_people() {
        if (person.get_y() < 650 && person.get_y() > -700) {
            person.set_y(person.get_y() + (int)road_one.get_incr());
        }
        if (person.get_y() > 610) {
            person.set_pose("wave");
            is_there = false;
            if (just_off) {
                just_off = false;
                wait = 0;
            }
        }

        if (!is_there) {
            int rand_create = rand.nextInt(150) + 0;
            if (rand_create == 5) {
                System.out.println("Created Person");
                alert_icon.set_show(true);
                person.set_y(-500);
                int rand_side = rand.nextInt(2) + 0;
                if (rand_side == 0) {
                    pside = "left";
                    person.set_x(LEFTPERSON);
                    alert_icon.set_x(LEFTPERSON - 15);
                    direction = 1;
                }
                if (rand_side == 1) {
                    pside = "right";
                    person.set_x(RIGHTPERSON);
                    alert_icon.set_x(RIGHTPERSON + 15);
                    direction = -1;
                }
                is_there = true;
            }
        }

        if (is_in) {
            if (stopping != 1)
                buffer++;
            if (buffer >= 100) {
                int rand_create = rand.nextInt(100) + 0;
                if (rand_create == 5) {
                    System.out.println("Dropped off Person");
                    points_icon.set_show(true);
                    score += 50;
                    points_icon.set_y(-500);
                    if (lastsel == 'a') {
                        person.set_x(LEFTPERSON);
                        points_icon.set_x(LEFTPERSON);
                    }
                    if (lastsel == 'd') {
                        person.set_x(RIGHTPERSON);
                        points_icon.set_x(RIGHTPERSON);
                    }
                    person.set_y(main_car.get_y());
                    is_in = false;
                    just_off = true;
                    buffer = 0;
                    wait = 0;
                }
            }
        }

        if (person.get_y() >= 265 && person.get_y() <= 400 && ((lastsel == 'a' && pside == "left") || (lastsel == 'd' && pside == "right"))) {
            if (stopping == 1 && !just_off) {
                wait++;
            }
            if (stopping == 2 && is_in) {
                wait = 0;
            }
            if (stopping == 2 && !is_in) {
                wait = 0;
                person.set_pose("wave");
            }
            if (wait >= 15) {
                person.set_pose("stand");
            }
            if (wait > 20 && wait <= 22) {
                person.set_x(person.get_x() + (2 * direction));
            }
            if (wait > 22 && wait <= 24) {
                person.set_x(person.get_x() + (3 * direction));
            }
            if (wait > 24 && wait <= 26) {
                person.set_x(person.get_x() + (4 * direction));
            }
            if (wait > 26 && wait <= 28) {
                person.set_x(person.get_x() + (5 * direction));
            }
            if (wait >= 30) {
                person.set_y(-700);
                is_in = true;
            }
        }

    }

    // manages all the other graphics
    public void manage_other_graphics() {
        if (honk_icon.get_show()) {
            honk_icon.set_y(main_car.get_y() - 65);
            honk_timer++;
            if (honk_timer > 20) {
                honk_timer = 0;
                honk_icon.set_y(-300);
                honk_icon.set_show(false);
            }
        }
        if (alert_icon.get_show()) {
            alert_icon.set_y(20);
            alert_timer++;
            if (alert_timer > 50) {
                alert_timer = 0;
                alert_icon.set_y(-300);
                alert_icon.set_show(false);
            }
        }
        if (points_icon.get_show()) {
            points_icon.set_y(main_car.get_y() + 30);
            points_timer++;
            if (points_timer > 70) {
                points_timer = 0;
                points_icon.set_y(-300);
                points_icon.set_show(false);
            }
        }
    }

    // loops the road when the image is no longer on screen
    public void loop_road(int increment) {
        road_one.set_incr(increment * (speedinc) * stop);
        road_two.set_incr(increment * (speedinc) * stop);
        road_one.set_y(road_one.get_y() + (int)road_one.get_incr());
        road_two.set_y(road_two.get_y() + (int)road_two.get_incr());
        if (road_one.get_y() > 600)
            road_one.set_y(road_two.get_y() - 1727);
        if (road_two.get_y() > 600)
            road_two.set_y(road_one.get_y() - 1727);





    }

    // called to either reset the game completely (force == true) or when a car moves off screen (force == false)
    public void reset(boolean force) {
        if (r_opposing.get_y() < -500) {
            r_opposing.set_y(-200);
            honk_r = 1.0;
            acright = 1;
        }
        if (l_opposing.get_y() < -500) {
            l_opposing.set_y(-470);
            honk_l = 1.0;
            acleft = 1;
        }
        if (force) {
            // sets all default values back
            person.set_pose("wave");
            score = 0;
            score_str = "SCORE: " + String.valueOf(score);
            l_opposing.set_random();
            r_opposing.set_random();
            road_one.set_x(0);
            road_one.set_y(-1127);
            road_two.set_x(0);
            road_two.set_y(-2854);
            increment = MAX_INCREMENT;
            difference = 1;
            stopping = 0;
            reset = false;

            crash = false;
            resetting = false;
            reset_count = 0;

            accel_factor = 1.0;
            acleft = 1.0;
            acright = 1.0;
            speed = 1.0;
            honk_l = 1.0;
            honk_r = 1.0;
            speedinc = 1.0;
            stop = 1;

            lastsel = 'a';
            main_car.set_x(280);
            main_car.set_y(290);

            count = 0;
            stop_count = 0;
            meter = 0;
            maxed = false;

            honk_icon.set_show(false);
            honk_timer = 0;
            honk_icon.set_y(-300);
            alert_icon.set_show(false);
            alert_timer = 0;
            alert_icon.set_x(LEFTPERSON);
            alert_icon.set_y(-300);
            points_icon.set_show(false);
            points_timer = 0;
            points_icon.set_x(LEFTPERSON);
            points_icon.set_y(-300);
            crash_icon.set_y(900);

            l_opposing.set_x(280);
            l_opposing.set_y(-800);
            r_opposing.set_x(420);
            r_opposing.set_y(-200);

            started = false;

            person.set_x(LEFTPERSON);
            person.set_y(650);
            direction = 1;
            pside = "-";
            is_there = false;
            is_in = false;
            wait = 0;
            buffer = 0;
            just_off = false;

            fixed_value =  0;
            value = 0;

            // starts the game again
            start = true;
            score_label.setBounds(600, 2, 200, 50);
        }
    }

    public void actionPerformed(ActionEvent e){

    }

    // Keyboard actions
    public void key_press (int c){
        if (start) {
            if (c == KeyEvent.VK_A && !crash && increment >= 10) {
                lastsel = 'a';
                selection = 'a';
            }
            if (c == KeyEvent.VK_D && !crash && increment >= 10) {
                lastsel = 'd';
                selection = 'd';
            }
            if (c == KeyEvent.VK_SHIFT && !crash && stop != 0) {
                stopping = 1;
            }
            if (c == KeyEvent.VK_W && !crash && stop != 0 && meter >= AVG_METER && !maxed) {
                speed = MAX_SPEED;
            }
            if (c == KeyEvent.VK_SPACE) {
                int honk_chance = rand.nextInt(4) + 0;
                if (honk_chance == 0) {
                    System.out.println("Honk!");
                    honk_icon.set_show(true);
                    if (lastsel == 'a' && acleft != 0 && l_opposing.get_y() > -300) {
                        honk_l = -1.5;
                    }
                    if (lastsel == 'd' && acleft != 0 && r_opposing.get_y() > -300) {
                        honk_r = -1.5;
                    }
                }
                else
                    System.out.println("No Honk!");
            }
            if (c == KeyEvent.VK_ENTER) {
                reset(true);
            }
        }
        if (c == KeyEvent.VK_ENTER && crash) {
            reset(true);
        }
    }

    public void key_release(int c) {
        if (start) {

            if (c == KeyEvent.VK_A) {
                selection = 'X';
            }
            if (c == KeyEvent.VK_D) {
                selection = 'X';
            }
            if (c == KeyEvent.VK_SHIFT) {
                if (!crash && !resetting) {
                    stop_count = 0;
                    stopping = 2;
                    acleft = 1;
                    acright = 1;
                }
            }
            if (c == KeyEvent.VK_W) {
                count = 0;
                speed = MIN_SPEED;
                if (honk_l == 0.5) {
                    honk_l = -1.5;
                }
                if (honk_r == 0.5) {
                    honk_r = -1.5;
                }
            }
        }
    }
}
