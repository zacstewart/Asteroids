/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package asteroids;
import java.util.ListIterator;
import processing.core.*;

/**
 *
 * @author zacstewart
 */
public class SpaceThing {
    PApplet canvas;
    ListIterator li;
    float locationX;
    float locationY;
    float direction;
    float speed;
    boolean explode = false;
    boolean remove = false;

    public float deltaX() {
        return (canvas.cos(canvas.radians(direction-90)) * speed);
    }

    public float deltaY() {
        return (canvas.sin(canvas.radians(direction-90)) * speed);
    }

    public void draw() {
        System.out.println("Draw spacething");
        if (this instanceof Ship) {
            Ship s = (Ship) this;
            s.draw();
        } else if (this instanceof Asteroid) {
            Asteroid a = (Asteroid) this;
            a.draw();
        }
    }

    public void update() {
        if(this instanceof Bullet) {
            System.out.println("Updating: " + locationX + " : " + locationY + " : " + direction);
        }
        if((locationX + deltaX()) > canvas.width) {
            locationX = (float) 0.0;
        } else if((locationX + deltaX()) < 0) {
            locationX = (float) canvas.width;
        } else {
            locationX += deltaX();
        }
        if((locationY + speed * canvas.sin(canvas.radians(direction-90))) > canvas.width) {
            locationY = (float) 0.0;
        } else if((locationY + speed * canvas.sin(canvas.radians(direction-90))) < 0) {
            locationY = (float) canvas.height;
        } else {
            locationY += speed * canvas.sin(canvas.radians(direction-90));
        }
    }

    public void collide(SpaceThing other) {
        if (this instanceof Ship && other instanceof Asteroid) {
            explode = true;
        }
    }

    public boolean collides(SpaceThing other) {
        if(this == other) return false; //no self-collisions!
        if(this.locationX < other.locationX+50 && this.locationX > other.locationX-50
                && this.locationY < other.locationY+50 && this.locationY > other.locationY-50) {
            collide(other);
            return true;
        }
        return false;
    }
}
