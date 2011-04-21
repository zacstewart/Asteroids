/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package asteroids;
import java.awt.geom.Rectangle2D;
import processing.core.*;

/**
 *
 * @author zacstewart
 */
public class SpaceThing {
    PApplet canvas;
    int frame = 0;
    SpaceThing[] createable;
    float locationX;
    float locationY;
    float direction;
    float speed;
    float size;
    boolean explode = false;
    boolean remove = false;
    protected Rectangle2D bounds;

    public SpaceThing(PApplet papp) {
        canvas = papp;
    }

    public float deltaX() {
        return (PApplet.cos(PApplet.radians(direction-90)) * speed);
    }

    public float deltaY() {
        return (PApplet.sin(PApplet.radians(direction-90)) * speed);
    }

    public void draw() {
    }

    public void update() {
        if((locationX + deltaX()) > canvas.width) {
            locationX = (float) 0.0;
        } else if((locationX + deltaX()) < 0) {
            locationX = (float) canvas.width;
        } else {
            locationX += deltaX();
        }
        if((locationY + speed * PApplet.sin(PApplet.radians(direction-90))) > canvas.height) {
            locationY = (float) 0.0;
        } else if((locationY + speed * PApplet.sin(PApplet.radians(direction-90))) < 0) {
            locationY = (float) canvas.height;
        } else {
            locationY += speed * PApplet.sin(PApplet.radians(direction-90));
        }
    }

    /**
     * Initiate a collision between this and other
     * @param other
     */

    public void collide(SpaceThing other) {
        if (this instanceof Asteroid && other instanceof Ship) {
            Ship s = (Ship) other;
            s.explode();
        } else if (this instanceof Asteroid && other instanceof Bullet) {
            Asteroid a = (Asteroid) this;
            a.explode();
            Bullet b = (Bullet) other;
            b.explode();
        }
    }

    public void explode() {

    }
}
