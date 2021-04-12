README.md
Game - Name Pending

NOTE
    This assignment called for window resizing to affect the game so the elements rearrange. However, this would change the overall
    game mechanics. For instance, if the user expanded vertically, the loop road function would have to adapt but that would allow
    the player to see more and more cars, possibly making it too easy. Therefore, I locked the window size as I feel like that
    supports the game mechanics I implemented as of now.

PROGRAM DESCRIPTION
    Classes: (please make sure all are present to properly compile)
        Game.java
        Controls.java
        Canvas.java
        Model.java
        Button.java
        Toggle.java
        Vehicle.java
        Car.java
        OCar.java
        Road.java
        Alert.java
        Meter.java
        Person.java
        TListener.java

    "images" Files:(please make sure all are present to properly run)
        alert.gif
        car.gif
        carb.gif
        carg.gif
        caro.gif
        carp.gif
        carr.gif
        cart.gif
        cary.gif
        crashed.png
        customer.png
        grad.png
        honk.png
        person_standing.png
        person.gif
        points.png
        road.png
        speed.png

    Objective:
        The goal of this game is to navigate a car through traffic (other cars) and pick up and drop off customers
        without crashing into other cars. This becomes a challenge while trying to navigate between multiple cars.
        The car is capable of honking, which sends the car in front at a faster speed until you have overtaken them
        or they are off the frame. However, the honk only works on occasion, because your car is old and it is broken.
        The car is also capable of speeding up, once it is at an average speed, and it can't go past its max speed or
        the car will have to take a rest. The car can also stop, but not for long, because customers are waiting. And
        last but not least, the car can crash into other cars, resulting in the game to end. Points are awarded for
        distance covered, and 50 bonus points are awarded for successfully dropping off a customer.

    Description:
        The points system and overall objective may be modified. However, the rest of the code is implemented thoroughly
        and involves many classes which work together. The Canvas class houses the main game with elements drawn in on
        a game tick which runs every 35 milliseconds. Additionally, the Controls class allows for controls on the right.
        Both these are included in the main Game JFrame, along with the Model class. Within the Model (and Controls) class(es)
        are many objects that either display an image with some properties and member functions, or extend a Swing class from Java
        like a JButton. In the Model class, there are certain functions run every game tick that constantly recalculate positions
        based on the game mechanics, allowing for only certain movements and for the customer to be placed appropriately. This
        involves several conditionals and a lot of minute details.

SPECIAL FEATURES
    This game utilizes a lot of randomness, but also guarantees that the game is possible. This was a challenge to implement
    because of the amount of possibilities for something to not be calculated, allowing for "a cheat" and the game to not
    work correctly. This is why the Canvas class is very long and contains several if statements.

RECENT ADDITIONS
    In this patch, a Method class was introduced that stores all the major widgets and objects for the Canvas, as well as its Graphics object to paint. All the game mechanics are in this Method class instead of Canvas. Canvas simply paints by calling a function in
    the Model class and passing in the Graphics object. This allows for a more organized structure for data management.

    Additionally, all prior member variables that were in Canvas and then in Models, was changed to be properties of the objects themselves. For example, there are many x and y values that are now stored within the objects themselves, along with some other
    specific variables that allow for the visual effects. This involved adding many getters and setters and restructuring the code in
    Model to call them to retrieve information rather than using the prior unorganized variables.

HOW TO COMPILE
    Make sure there is a folder "images" in the same directory as the game and that all the image files are there
    IN COMMAND PROMPT:
        javac Game.java
        java Game

INHERITANCE HIERARCHY
Game -> JFrame
Controls -> JPanel
Canvas -> JPanel
Button -> JButton
Road -> ImageIcon
Alert -> ImageIcon
Vehicle -> ImageIcon
Car -> Vehicle
OCar -> Car
Meter -> ImageIcon
Person -> ImageIcon
Meter -> JPanel
Toggle -> JToggleButton
TListener
Model

AGGREGATION HIERARCHY
Game
--> Controls
    --> Speed JLabel
    --> Speed Meter
        --> Meter Image (for background)
    --> Instructions Toggle
        --> Canvas (to modify it)
        --> Controls (to modify it)
    --> Instructions Label
    --> Start Button
        --> Canvas (to modify it)
    --> Restart Button
        --> Canvas (to modify it)
--> Model
    --> Canvas (to modify it)
    --> Road One ImageIcon
    --> Road Two ImageIcon
    --> Main Car Car
        --> Car Image
    --> Left Other Car OCar
        --> Other Car Image
    --> Right Other Car OCar
        --> Other Car Image
    --> Person (Customer) Person
        --> Wave Image
        --> Stand Image
    --> Honk Icon ImageIcon
    --> Alert Icon ImageIcon
    --> Points Icon ImageIcon
    --> Crash Icon ImageIcon
    --> Customer Icon ImageIcon
    --> Score Label JLabel
--> Canvas
    --> Model (to access it)
--> TListener
    --> Canvas (to access it)
    --> Model (to access it)
    --> Canvas (to access it)
