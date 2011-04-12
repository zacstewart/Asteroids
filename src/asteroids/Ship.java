/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package asteroids;
import processing.core.*;
import java.util.ListIterator;

/**
 *
 * @author zacstewart
 */
public class Ship extends SpaceThing {
    private boolean movingForward;
    private boolean movingBackward;
    private boolean rotatingLeft;
    private boolean rotatingRight;
    private boolean shieldUp = true;
    private Bullet[] bullets;
    
    public Ship(PApplet papp, ListIterator list) {
        canvas = papp;
        li = list;
        locationX = (float) canvas.width/2;
        locationY = (float) canvas.height/2;
        speed = (float) 3.0;
    }

    public void draw() {
        if(explode) canvas.stroke(255, 0, 0);
        else canvas.stroke(255);
        canvas.fill(255);
//        canvas.text("Direction: " + direction, 0, 10);
//        canvas.text("Location: (" + locationX + "," + locationY + ")", 0, 20);

        if (movingForward) update();
        if (rotatingLeft) this.rotate((float) 1.0);
        if (rotatingRight) this.rotate((float) -1.0);


        canvas.pushMatrix();
        canvas.translate(locationX, locationY);
        canvas.rectMode(canvas.CENTER);
        canvas.rotate(canvas.radians(direction));
        canvas.fill(0);
        canvas.triangle(0, -6, -5, 6, 5, 6);
        if (movingForward) {

        }
        if (movingForward) drawJet();
        if (shieldUp) drawShield();
        canvas.popMatrix();

//        if(bullets != null) {
//            for (Bullet bullet : bullets) {
//              if(bullet instanceof Bullet) {
//                  if(bullet.getDistance() >= canvas.width) {
//                      bullet.explode();
//                      bullet.draw();
//                      bullet = null;
//                  } else {
//                    bullet.draw();
//                  }
//               }
//           }
//        }
    }

    private float shipCos() {
        return (float) canvas.cos(canvas.radians(direction));
    }

    private float shipSin() {
        return (float) canvas.sin(canvas.radians(direction));
    }

    private float shipAngle() {
        return (float) canvas.radians(direction);
    }

    private void drawJet() {
        canvas.stroke(255,0,0);
        canvas.triangle(0, 12, -2, 7, 2, 7);
    }

    public void drawShield() {
        canvas.stroke(150,150,255);
        canvas.noFill();
        canvas.ellipse(0, 0+2, 35, 35);
    }

    private void rotate(Float angle) {
        direction -= speed * angle;
        direction = direction % 360;
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

    public void firePrimary() {
        if(bullets == null) {
            bullets = new Bullet[10];
        }
        for(int i=0; i<bullets.length; i++) {
            if(bullets[i] == null || bullets[i].isActive() != true) {
                bullets[i] = new Bullet(canvas, locationX+12*shipSin(), locationY-12*shipCos(), direction, (movingForward ? speed : (float) 0.0));
                bullets[i].activate();
                li.add(bullets[i]);
                break;
            }
        }
    }


}
