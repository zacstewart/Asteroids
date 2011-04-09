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

    Ship ship;

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
        background(10);
        rectMode(CENTER);
        ship = new Ship(this);
    }

    @Override
    public void draw() {
        background(10);
        ship.draw();
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
