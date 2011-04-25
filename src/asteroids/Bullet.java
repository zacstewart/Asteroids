package asteroids;
import java.awt.geom.Rectangle2D;
import processing.core.*;

/**
 *
 * @author Zachary Stewart
 */
class Bullet extends SpaceThing {
    private boolean active = false;

    public Bullet(PApplet papp, float x, float y, float direction, float initSpeed) {
        super(papp);
        locationX = x;
        locationY = y;
        this.direction = direction;
        this.speed = initSpeed + 6;
        size = 1;
        bounds = new Rectangle2D.Float(locationX, locationY, size, size);
        deltaX = PApplet.cos(PApplet.radians(direction-90)) * speed;
        deltaY = PApplet.sin(PApplet.radians(direction-90)) * speed;
    }

    @Override
    public void draw() {
        super.draw();
        canvas.pushMatrix();
        canvas.translate(locationX, locationY);
        canvas.rectMode(PApplet.CENTER);
        canvas.rotate(PApplet.radians(direction));

        if(distance >= canvas.width) {
            explode = true;
            remove = true;
        }

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
            update();
        }

        canvas.popMatrix();
    }

    @Override
    public void update() {
        distance += speed;
        super.update();
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

    @Override
    public void explode() {
        explode = true;
        remove = true;
    }
}
