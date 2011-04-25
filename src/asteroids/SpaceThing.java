package asteroids;
import java.awt.geom.Rectangle2D;
import processing.core.*;

/**
 *
 * @author Zachary Stewart
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
    protected float distance = 0.0f;
    protected Rectangle2D bounds;
    protected boolean explode = false;
    protected boolean remove = false;
    protected final int SHIP = 1;
    protected final int ASTEROID = 2;
    protected final int BULLET = 3;
    protected final int NUKE = 4;
    protected final int MENU = 0;
    protected final int PLAYING = 1;
    protected final int GAMEOVER = 2;

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
//        drawGhost();
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
        } else if (this instanceof PowerUp && other instanceof Ship) {
            ((Ship) other).addPowerUp((PowerUp) this);
            ((PowerUp) this).remove();
        }
    }

    public void explode() {
        explode = true;
    }

    public void remove() {
        remove = true;
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
