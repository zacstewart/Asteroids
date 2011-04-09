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
public class Ship {
    private PApplet parent;
    private Float locationX;
    private Float locationY;
    private Float direction = (float) 90.0;
    private Float speed = (float) 2.0;
    private boolean movingForward;
    private boolean movingBackward;
    private boolean rotatingLeft;
    private boolean rotatingRight;
    
    public Ship(PApplet papp) {
        parent = papp;
        locationX = (float) parent.width/2;
        locationY = (float) parent.height/2;
    }

    public void draw() {
        parent.translate((float) 0.0, (float) 0.0);
        parent.rotate(0);
        parent.stroke(255);
        parent.fill(255);
        parent.text("Direction: " + direction, 0, 10);
        parent.text("Location: (" + locationX + "," + locationY + ")", 0, 20);
        parent.text("(0,0)", 10, 10);
        parent.line(0, 0, 0, parent.height);
        parent.line(0, 0, parent.width, 0);

        if (movingForward) thrust();
        if (rotatingLeft) this.rotate((float) 1.0);
        if (rotatingRight) this.rotate((float) -1.0);


        parent.translate(locationX, locationY);
        parent.rectMode(parent.CENTER);
        parent.rotate(parent.radians(direction));
        parent.fill(0);
        parent.triangle(0, -6, -5, 6, 5, 6);
        if (movingForward) drawJet();
        parent.translate(0, 0);
        parent.rotate(0);

    }

    private float shipCos() {
        return (float) parent.cos(parent.radians(direction));
    }

    private float shipSin() {
        return (float) -parent.sin(parent.radians(direction));
    }

    private float shipAngle() {
        return (float) parent.radians(direction);
    }

    private void drawJet() {
        parent.stroke(255,0,0);
        parent.triangle(0, 10, -2, 6, 2, 6);
    }

    public void setMovingForward(boolean moving) {
        if(moving) movingForward = true;
        else movingForward = false;
    }

    public void setRotatingLeft(boolean rotating) {
        if(rotating) rotatingLeft = true;
        else rotatingLeft = false;
    }

    public void setRotatingRight(boolean rotating) {
        if(rotating) rotatingRight = true;
        else rotatingRight = false;
    }

    private void thrust() {
        if((locationX += speed * parent.cos(parent.radians(direction-90))) > parent.width) {
            locationX = (float) 0.0;
        } else if ((locationX += speed * parent.cos(parent.radians(direction-90))) < 0) {
            locationX = (float) parent.width;
        } else {
            locationX += speed * parent.cos(parent.radians(direction-90));
        }
        if((locationY += speed * parent.sin(parent.radians(direction-90))) > parent.width) {
            locationY = (float) 0.0;
        } else if((locationY += speed * parent.sin(parent.radians(direction-90))) < 0) {
            locationY = (float) parent.height;
        } else {
            locationY += speed * parent.sin(parent.radians(direction-90));
        }
    }

    private void rotate(Float angle) {
        direction -= angle;
    }
}
