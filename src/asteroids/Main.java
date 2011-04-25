package asteroids;
import processing.core.*;
import java.awt.event.*;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Zachary Stewart
 * April 25, 2011
 *
 * This class has very little logic to it. Rather, it creates the PApplet and
 * calls game methods and delegates events to the game class.
 */
public class Main extends PApplet {
    public Game game;
    public Ship ship;
    public Asteroid[] asteroids;
    public List spaceThings;
    public ListIterator li;
    Object x;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PApplet.main(new String[] {"asteroids.Main"});
    }

    @Override
    public void setup() {
        smooth();
        game = new Game(this);
        size(800, 600);
        rectMode(CENTER);
    }

    @Override
    public void draw() {
        game.draw();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        game.control("keyDown", e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        game.control("keyUp", e);
    }

    @Override
    public void mousePressed() {
        game.mouseControl();
    }

}
