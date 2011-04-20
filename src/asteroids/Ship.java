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
public class Ship extends SpaceThing {
    private boolean movingForward;
    private boolean movingBackward;
    private boolean rotatingLeft;
    private boolean rotatingRight;
    private Rectangle2D bounds;
    
    public Ship(PApplet papp) {
        super(papp);
        locationX = (float) canvas.width/2;
        locationY = (float) canvas.height/2;

        speed = (float) 3.0;
        size = 6;
        bounds = new Rectangle2D.Float(locationX, locationY, size/3*4, size*2);
    }

    @Override
    public void draw() {
        super.draw();
        if(explode) canvas.stroke(255, 0, 0);
        else canvas.stroke(255);
        canvas.fill(255);
        if (movingForward) update();
        if (rotatingLeft) this.rotate((float) 1.0);
        if (rotatingRight) this.rotate((float) -1.0);


        canvas.pushMatrix();
        canvas.translate(locationX, locationY);
        canvas.rectMode(canvas.CENTER);
        canvas.rotate(canvas.radians(direction));
        canvas.fill(0);
        canvas.triangle(0, -size, -size/3*2, size, size/3*2, size);
        if (movingForward) drawJet();
        canvas.popMatrix();
        drawGhost();
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

    public void drawGhost() {
        bounds.setRect(locationX, locationY, size/3*4, size*2);
        canvas.stroke(150,150,255);
        canvas.noFill();
        canvas.rect((float) bounds.getX(), (float) bounds.getY(),
                (float) bounds.getWidth(), (float) bounds.getHeight());
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
        createable = new SpaceThing[1];
        createable[0] = new Bullet(canvas, locationX+12*shipSin(), locationY-12*shipCos(), direction, (movingForward ? speed : (float) 0.0));
    }

    public Rectangle2D getBounds() {
        return bounds;
    }

    public void explode() {
        createable = new SpaceThing[2];
        createable[0] = new Ship(canvas);
        createable[1] = new Debris(canvas, locationX, locationY);
        remove = true;
    }


}
