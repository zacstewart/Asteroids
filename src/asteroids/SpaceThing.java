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
public class SpaceThing {
    PApplet canvas;
    float locationX;
    float locationY;
    float direction;
    float speed;

    public float deltaX() {
        return (canvas.cos(canvas.radians(direction-90)) * speed);
    }

    public float deltaY() {
        return (canvas.sin(canvas.radians(direction-90)) * speed);
    }

    public void update() {
        if(this instanceof Bullet) {
            System.out.println("Updating: " + locationX + " : " + locationY + " : " + direction);
        }
        if((locationX + deltaX()) > canvas.width) {
            locationX = (float) 0.0;
        } else if ((locationX + deltaX()) < 0) {
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
}
