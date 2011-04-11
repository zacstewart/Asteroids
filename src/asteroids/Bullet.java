/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package asteroids;
import processing.core.*;

/**
 *
 * @author zacstewart
 */
public class Bullet extends SpaceThing {

    private float distance = (float) 0.0;
    private float speed;
    private boolean active = false;
    private boolean explode = false;

    public Bullet(PApplet papp, float x, float y, float angle, float shipSpeed) {
        canvas = papp;
        locationX = x;
        locationY = y;
        direction = angle;
        speed = (float) 3.0 + shipSpeed;
    }

    public void draw() {
        canvas.pushMatrix();
        canvas.translate(locationX, locationY);
        canvas.rectMode(canvas.CENTER);
        canvas.rotate(canvas.radians(direction));

        if(explode) {
            canvas.stroke(255, 0, 0);
            canvas.line(0, 0, 0, 4);
            canvas.line(0, 0, 4, 0);
            canvas.line(0, 0, 0, -4);
            canvas.line(0, 0, -4, 0);
        } else {
            canvas.stroke(0, 255,0);
            canvas.line(0, 0, 0, 6);
            distance += speed;
            thrust();
        }

        canvas.popMatrix();
    }

    private void thrust() {
        if((locationX + speed * canvas.cos(canvas.radians(direction-90))) > canvas.width) {
            locationX = (float) 0.0;
        } else if ((locationX + speed * canvas.cos(canvas.radians(direction-90))) < 0) {
            locationX = (float) canvas.width;
        } else {
            locationX += speed * canvas.cos(canvas.radians(direction-90));
        }
        if((locationY + speed * canvas.sin(canvas.radians(direction-90))) > canvas.width) {
            locationY = (float) 0.0;
        } else if((locationY + speed * canvas.sin(canvas.radians(direction-90))) < 0) {
            locationY = (float) canvas.height;
        } else {
            locationY += speed * canvas.sin(canvas.radians(direction-90));
        }
    }

    public float getDistance() {
        return distance;
    }

    public void activate() {
        active = true;
    }

    public boolean isActive() {
        return active;
    }

    public void explode() {
        explode = true;
        active = false;
    }
}
