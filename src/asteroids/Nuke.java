package asteroids;
import java.awt.geom.Rectangle2D;
import processing.core.*;

/**
 *
 * @author Zachary Stewart
 */
class Nuke extends SpaceThing {
    private boolean active = false;
    private float destinationX;
    private float destinationY;
    private SpaceThing target;
    private int explosion = 1;
    private boolean explosionExpanding = true;

    public Nuke(PApplet papp, float x, float y, float direction, float destinationX, float destinationY) {
        super(papp);
        locationX = x;
        locationY = y;
        this.direction = direction;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.speed = speed + 4;
        size = 2;
        speed = 4;
        bounds = new Rectangle2D.Float(locationX, locationY, size*2, size*2);
    }

    public Nuke(PApplet papp, float x, float y, float direction, SpaceThing target) {
        super(papp);
        locationX = x;
        locationY = y;
        this.direction = direction;
        this.target = target;
        this.speed = speed + 4;
        size = 3;
        speed = 4;
        bounds = new Rectangle2D.Float(locationX, locationY, size*2, size*2);
    }

    @Override
    public void draw() {
        super.draw();
        canvas.pushMatrix();
        canvas.translate(locationX, locationY);
        canvas.rectMode(PApplet.CENTER);
        canvas.rotate(PApplet.radians(direction));

        if(distance == canvas.width*2 || bounds.contains(getDestinationX(), getDestinationY())) {
            explode();
        }

        if(explode) {
            // Nuclear blast! Expands, contracts and removes.
            canvas.stroke(255,0,0);
            canvas.noFill();
            if(explosion <= 0) {
                remove = true;
            }
            if(explosionExpanding) {
                if(explosion <= 150) {
                    explosion += 5;
                } else {
                    explosionExpanding = false;
                }
            } else {
                explosion -= 1;
            }
            bounds.setRect(locationX, locationY, explosion, explosion);
            canvas.ellipse(0, 0, explosion, explosion);
            canvas.fill(255,0,0);
        } else {
            canvas.stroke(255);
            canvas.noFill();
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
            // Jet
            canvas.stroke(255,0,0);
            canvas.triangle(0, size*3+11, size/3*2, size*3+1, -size/3*2, size*3+1);

            distance += speed;
            update();
        }

        canvas.popMatrix();
    }

    @Override
    public void update() {
        distance += speed;
        // Whoa. Asteroid target tracking! Actually not the great. Just adjusts
        // direction to the average of direction and the asteroid location tangent.
        direction = ((PApplet.degrees(PApplet.atan2(
                locationY - getDestinationY(),
                locationX - getDestinationX()
                )-90)%360)+direction)/2;
        deltaX = PApplet.cos(PApplet.radians(direction-90)) * speed;
        deltaY = PApplet.sin(PApplet.radians(direction-90)) * speed;
        super.update();

    }

    /**
     * The follow two methods return the coordinates for Nuke target,
     * whether space or Asteroid
     * @return
     */
    public float getDestinationX() {
        if(target instanceof SpaceThing) {
            return (float) target.getBounds().getCenterX();
        } else {
            return destinationX;
        }
    }

    public float getDestinationY() {
        if(target instanceof SpaceThing) {
            return (float) target.getBounds().getCenterY();
        } else {
            return destinationY;
        }
    }

    /**
     * how far the missile has traveled.
     * @return
     */

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
    }
}
