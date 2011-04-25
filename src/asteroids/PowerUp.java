package asteroids;
import java.awt.geom.Rectangle2D;
import processing.core.*;
import java.util.Random;

/**
 *
 * @author Zachary Stewart
 */
class PowerUp extends SpaceThing {
    private int type;
    Random rand = new Random();
    
    public PowerUp(PApplet papp, int type) {
        super(papp);
        size = 3;
        bounds = new Rectangle2D.Float(locationX, locationY, 30, 30);
        speed = 1;
        locationX = rand.nextInt(canvas.width);
        locationY = rand.nextInt(canvas.height);
        direction = rand.nextInt(361);
        deltaX = PApplet.cos(PApplet.radians(direction-90)) * speed;
        deltaY = PApplet.sin(PApplet.radians(direction-90)) * speed;
        this.type = type;
    }

    @Override
    public void draw() {
        super.draw();
        canvas.pushMatrix();
        canvas.translate(locationX, locationY);
        canvas.rectMode(PApplet.CENTER);
        canvas.noFill();
        canvas.stroke(255);
        canvas.rect(0, 0,
            (float) bounds.getWidth(), (float) bounds.getHeight());
        switch(type) {
            case NUKE:
                drawNuke();
                break;
            case SHIP:
                drawShip();
                break;
        }
        canvas.popMatrix();
        update();
        if(distance >= canvas.width) {
            remove();
        }
    }

    public void drawNuke() {
        // Tube and cone
        canvas.beginShape();
        canvas.vertex(0, -size*4);
        canvas.vertex(size/3*2, -size*3);
        canvas.vertex(size/3*2, size*3);
        canvas.vertex(-size/3*2, size*3);
        canvas.vertex(-size/3*2, -size*3);
        canvas.endShape(PApplet.CLOSE);
        // Fins
        canvas.triangle(size/3*2, size*3, size/3*2, size*3/2, size/3*2+4, size*3);
        canvas.triangle(-size/3*2, size*3, -size/3*2, size*3/2, -size/3*2-4, size*3);
    }

    public void drawShip() {
        canvas.triangle(0, -size*2, -size/3*4, size*2, size/3*4, size*2);
    }

    public int getType() {
        return type;
    }

    public boolean collides(SpaceThing other) {
        if(other instanceof Ship && bounds.intersects(((SpaceThing) other).getBounds())) {
            return true;
        } else {
            return false;
        }
    }
}
