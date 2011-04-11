/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package asteroids;
import processing.core.*;
import java.awt.*;
import java.util.ListIterator;
import java.util.Random;

/**
 *
 * @author zacstewart
 */
public class Asteroid extends SpaceThing {
    int size;
    int points;
    int[] x;
    int[] y;
    Random rand;

    public Asteroid(PApplet papp, ListIterator list) {
        canvas = papp;
        li = list;
        rand = new Random();
        locationX = rand.nextInt(canvas.width);
        locationY = rand.nextInt(canvas.height);
        direction = rand.nextInt(360);
        size = rand.nextInt(50)+50;
        speed = (float) 1.0;

    }

    public void draw() {
        canvas.stroke(255);
        canvas.ellipse(locationX, locationY, size, size);
        update();
    }

}
