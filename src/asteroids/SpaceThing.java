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
abstract class SpaceThing {
    protected PApplet canvas;
    protected int frame = 0;
    protected SpaceThing[] createable;
    protected float locationX;
    protected float locationY;
    protected float direction;
    protected float deltaX;
    protected float deltaY;
    protected float speed;
    protected float size;
    protected Rectangle2D bounds;
    protected boolean explode = false;
    boolean remove = false;

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
        frame += 1;
    }

    public void update() {
        if(bounds.getMinX() > canvas.width) {
            locationX = (float) 0.0;
        } else if(bounds.getMaxX() < 0) {
            locationX = (float) canvas.width;
        } else {
            locationX += deltaX;
        }
        if(bounds.getMinY() > canvas.height) {
            locationY = (float) 0.0;
        } else if(bounds.getMaxY() < 0) {
            locationY = (float) canvas.height;
        } else {
            locationY += deltaY;
        }

        bounds.setRect(locationX, locationY, bounds.getWidth(), bounds.getHeight());
    }

    /**
     * Initiate a collision between this and other
     * @param other
     */

    public void collide(SpaceThing other) {
        if (this instanceof Asteroid && other instanceof Ship) {
            ((Ship) other).explode();
        } else if (this instanceof Asteroid && other instanceof Bullet) {
            ((Asteroid) this).explode();
            ((Bullet) other).explode();
        } else if (this instanceof Asteroid && other instanceof Nuke) {
            ((Asteroid) this).explode();
            ((Nuke) other).explode();
        }
    }

    public void explode() {

    }

    protected void drawGhost() {
        canvas.stroke(150,150,255);
        canvas.noFill();
        canvas.rect((float) bounds.getX(), (float) bounds.getY(),
                (float) bounds.getWidth(), (float) bounds.getHeight());
    }

    public Rectangle2D getBounds() {
        return bounds;
    }
}
