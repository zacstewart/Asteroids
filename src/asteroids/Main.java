/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;
import processing.core.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author zacstewart
 */
public class Main extends PApplet implements KeyListener, ActionListener {
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
        // TODO code application logic here
        PApplet.main(new String[] {"--present", "asteroids.Main"});
    }

    @Override
    public void setup() {
        game = new Game(this);
        System.out.println(game.canvas);
        size(600, 600);
        rectMode(CENTER);
        game.createThing("ship");
        for(int i=0; i<3; i++) {
            game.createThing("asteroid");
        }
    }

    /**
     * Main game loop. draws all objects within spaceThings ArrayList,
     * monitors collisions and removes dead objects.
     *
     * Perhaps all these should be in one loop to save resources.
     */
    @Override
    public void draw() {
        game.draw();
//        while(li.hasNext()) {
//            x = li.next();
//            if(x instanceof Ship) {
//                Ship s = (Ship) x;
//                if(s.remove) {
//                    li.remove();
//                    ship = new Ship(this, li);
//                    li.add(ship);
//                }
//                s.draw();
//            }
//            if(x instanceof Asteroid) {
//                Asteroid a = (Asteroid) x;
//                a.draw();
//            }
//        }
//        for(Asteroid asteroid : asteroids) {
//            if(asteroid instanceof Asteroid) {
//                asteroid.draw();
//           }
//        }
//        monitorCollisions(spaceThings);
    }

    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void monitorCollisions(List spaceThings) {
        ListIterator it1 = spaceThings.listIterator();
        while(it1.hasNext()) {
            Object x = it1.next();
            if(x instanceof SpaceThing) {
                SpaceThing s = (SpaceThing) x;
                ListIterator it2 = spaceThings.listIterator();
                while(it2.hasNext()) {
                    Object y = it2.next();
                    if(y instanceof SpaceThing) {
                        SpaceThing s2 = (SpaceThing) y;
                        if(s.collides(s2)) {
                            System.out.println("Collision!");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.println("key:" + e.getKeyCode());
        game.control(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case 38:
                ship.setMovingForward(false);
                break;
            case 37:
                ship.setRotatingLeft(false);
                break;
            case 39:
                ship.setRotatingRight(false);
                break;
            default:
                break;
        }
    }




}
