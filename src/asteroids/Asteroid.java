/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package asteroids;
import processing.core.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 *
 * @author zacstewart
 */
public class Asteroid extends SpaceThing {
    int points;
    int generation;
    int color = canvas.color(255);
    int[] x;
    int[] y;
    int[] avgLocation;
    Random rand;
    Polygon poly;

    public Asteroid(PApplet papp, float initX, float initY, int initGeneration) {
        super(papp);
        locationX = initX;
        locationY = initY;
        generation = initGeneration;
        init();
    }
    
    public Asteroid(PApplet papp) {
        super(papp);
        generation = 3;
        init();
    }
    
    private void init() {
        rand = new Random();
        direction = rand.nextInt(360);
        switch (generation) {
            case 3:
                size = rand.nextInt(10)+90;
                break;
            case 2:
                size = rand.nextInt(10)+40;
                break;
            case 1:
                size = rand.nextInt(10)+15;
                break;
        }
        speed = (float) 2.0;
        points = rand.nextInt(5)+10;
        x = new int[points];
        y = new int[points];
        for (int i=0; i<points; i++) {
            x[i] = (int) ((locationX+ rand.nextInt(20)-10 + size
                    * PApplet.cos(PApplet.radians(360/points*i))));
            y[i] = (int) ((locationY + rand.nextInt(20)-10 + size
                    * PApplet.sin(PApplet.radians(360/points*i))));
        }
        poly = new Polygon(x, y, points);
    }

    @Override
    public void update() {
        avgLocation = avgLocation();
        int dx;
        int dy;

        if(avgLocation[0] > canvas.width) {
            dx = -canvas.width;
        } else if(avgLocation[0] < 0) {
            dx = canvas.width;
        } else {
            dx = (int) (speed*deltaX());
        }

        if(avgLocation[1] > canvas.height) {
            dy = -canvas.height;
        } else if(avgLocation[1] < 0) {
            dy = canvas.height;
        } else {
            dy = (int) (speed*deltaY());
        }
        poly.translate(dx, dy);
        super.update();
    }

    @Override
    public void draw() {
        super.draw();
        canvas.noFill();
        if(explode) canvas.stroke(255,0,0);
        else canvas.stroke(color);
        canvas.beginShape();
        for(int i=0; i<poly.npoints; i++) {
            canvas.vertex(poly.xpoints[i], poly.ypoints[i]);
        }
        canvas.endShape(PApplet.CLOSE);
        update();
    }


    public int[] avgLocation() {
        int sumX = 0;
        int sumY = 0;
        for(int i=0; i<poly.npoints; i++) {
            sumX += poly.xpoints[i];
            sumY += poly.ypoints[i];
        }
        int[] location = new int[2];
        location[0] = sumX/poly.npoints;
        location[1] = sumY/poly.npoints;
        return location;
    }


    /**
     * This checks for Asteroid collisions with Ships and Bullets.
     * It does a terrible job of it, too. If I had more time I'd
     * find a better intersect checker.
     * @param other
     * @return
     */
    public boolean collides(Object other) {
        if(other instanceof Bullet && poly.contains(((Bullet) other).locationX, ((Bullet) other).locationY)) {
            return true;
        } else if (other instanceof Ship && poly.intersects(((Ship) other).getBounds())) {
            color = canvas.color(255, 0, 255);
            return true;
        } else if (other instanceof Rectangle2D && poly.intersects(((Rectangle2D) other))) {
            color = canvas.color(255, 0, 255);
            return true;
        } else {
            color = canvas.color(255);
            return false;
        }
    }

    public boolean intersectsPoint(int xPoint, int yPoint) {
        System.out.println("Checking asteroid at point " + xPoint + " " + yPoint);
        return poly.contains(xPoint, yPoint);
    }

    @Override
    public void explode() {
        if(generation > 1) {
            createable = new SpaceThing[2];
            createable[0] = new Asteroid(
                    canvas,
                    avgLocation[0],
                    avgLocation[1],
                    generation-1);
            createable[1] = new Asteroid(canvas,
                    avgLocation[0],
                    avgLocation[1],
                    generation-1);
            remove = true;
        } else {
            remove = true;
        }
    }

}
