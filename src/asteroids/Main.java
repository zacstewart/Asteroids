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

/**
 *
 * @author zacstewart
 */
public class Main extends PApplet implements KeyListener, ActionListener {

    public Ship ship;
    public Asteroid[] asteroids;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        PApplet.main(new String[] {"--present", "asteroids.Main"});
    }

    @Override
    public void setup() {
        size(600, 600);
        rectMode(CENTER);
        ship = new Ship(this);
        asteroids = new Asteroid[2];
        for(int i=0; i<asteroids.length; i++) {
            asteroids[i] = new Asteroid(this);
        }
    }

    @Override
    public void draw() {
        background(10,10,100);
        ship.draw();
        for(Asteroid asteroid : asteroids) {
            if(asteroid instanceof Asteroid) {
                asteroid.draw();
           }
        }
    }

    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("key:" + e.getKeyCode());
        switch(e.getKeyCode()) {
            case 38:
                ship.setMovingForward(true);
                break;
            case 37:
                ship.setRotatingLeft(true);
                break;
            case 39:
                ship.setRotatingRight(true);
                break;
            case 32:
                ship.firePrimary();
                break;
            default:
                break;
        }
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
