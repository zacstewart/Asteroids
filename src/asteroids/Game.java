/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package asteroids;

import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import processing.core.PApplet;

/**
 *
 * This class will be used to create, update and monitor all game objects
 */
public class Game {
    public PApplet canvas;
    public List spaceThings;
    public List createables;
    public ListIterator li;
    public ListIterator createablesLi;
    public Ship ship;
    public int level;
    int asteroidsRemaining;
    public int shipsRemaining;
    /**
     *
     * @param papp Main PApplet of game.
     *
     */
    public Game(PApplet papp) {
        canvas = papp;
        spaceThings = new LinkedList();
        createables = new LinkedList();
        shipsRemaining = 3;
        initLevel(1);
    }
    
    public void initLevel(int newLevel) {
        level = newLevel;
//        createThing("ship");
        for(int i=0; i<level; i++) {
            createThing("asteroid");
        }
    }

    /**
     * Creates Ships, Asteroids, Bullets, etc and adds them to the main
     * object list.
     * @param thing type of SpaceThing. Ship, Asteroid or Bullet
     */
    public void createThing(String thing) {
        li = spaceThings.listIterator();
        if(thing.equals("ship")) {
            li.add(new Ship(canvas));
        } else if (thing.equals("asteroid")) {
            li.add(new Asteroid(canvas));
        }
    }

    /**
     * Removes thing from object list.
     * @param thing
     */
    public void destroyThing(SpaceThing thing) {
        li = spaceThings.listIterator();
        li.remove();
    }
    



    public void draw() {
        asteroidsRemaining = 0;
        canvas.background(0);
        loopList();
        if(asteroidsRemaining <= 0) {
            initLevel(level+1);
        }

        // This should go in a hud method
        canvas.stroke(255);
        canvas.fill(255);
        canvas.text("Level: " + level, 10, 20);
        canvas.text("Ships: " + shipsRemaining, 10, 40);
    }

    /**
     * Lots going on in here. Main control center for game. Sets the ship
     * var to the current Ship, removes dead objects, adds items in each objects
     * createable array to the object list and clears said array. Monitors
     * collisions via getCollision() and then enacts the appropriate collision.
     */
    public void loopList() {
        li = spaceThings.listIterator();
        createablesLi = createables.listIterator();
        while(li.hasNext()) {
            Object x = li.next();
            if(x instanceof Ship) {
                ship = (Ship) x;
            } else if(x instanceof Asteroid) {
                asteroidsRemaining += 1;
            }
            if(x instanceof SpaceThing) {
                SpaceThing s = (SpaceThing) x;
                if (s.remove) {
                    if(s instanceof Ship) {
                        shipsRemaining -= 1;
                    }
                    li.remove();
                } else {
                    s.draw();
                    if(s.createable != null) {
                        for(SpaceThing createable : s.createable) {
                            createablesLi.add(createable);
                        }
                        s.createable = null;
                    }

                    SpaceThing collidingObject = getCollision(s);
                    if(collidingObject instanceof SpaceThing){
                        s.collide(collidingObject);
                        if(s.createable != null) {
                            for(SpaceThing createable : s.createable) {
                                createablesLi.add(createable);
                            }
                            s.createable = null;
                        }
                    }
                }
            }
        }

        insertCreateables();
    }


    /**
     * This loops through the createables and adds them to the object list.
     * if the ship's spawn place is blocked by an asteroid, it waits until
     * next iteration.
     */
    public void insertCreateables() {
        li = spaceThings.listIterator();
        createablesLi = createables.listIterator();
        while(createablesLi.hasNext()) {
            Object createable = createablesLi.next();
            if(createable instanceof Ship) {
                if(!collisionsAtPoint(canvas.width/2,canvas.height/2)) {
                    li.add(createable);
                    createablesLi.remove();
                }
            } else {
                li.add(createable);
                createablesLi.remove();
            }
        }
    }

    public boolean collisionsAtPoint(int xPoint, int yPoint) {
        Rectangle2D ghostShip = new Rectangle2D.Float(canvas.width/2, canvas.height/2, 20, 20);
        ListIterator it = spaceThings.listIterator();
        while(it.hasNext()) {
            Object x = it.next();
            if(x instanceof Asteroid) {
                Asteroid object = (Asteroid) x;
                if(object.collides(ghostShip)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Nested ListIterator loops to check for collisions between object and
     * all other items in the object list. Returns whatever SpaceThing is
     * colliding with object.
     * @param object
     * @return SpaceThing
     */
    public SpaceThing getCollision(SpaceThing object) {
        ListIterator it = spaceThings.listIterator();
        while(it.hasNext()) {
            Object x = it.next();
            if(object instanceof Asteroid && x instanceof Ship) {
                if(((Asteroid) object).collides(x)) {
                    return (SpaceThing) x;
                }
            }
        }
        return null;
    }


    /**
     * Key monitor. Maps directional and space keys to corresponding ship
     * methods.
     * @param type
     * @param e
     */
    public void control(String type, KeyEvent e) {
        if(type.equals("keyDown")) {
            switch(e.getKeyCode()) {
                case 38:
                    ship.setMovingForward(true);
                    break;
                case 37:
                    ship.setRotatingLeft(true);
                    break;
                case 39:
                    ship.setRotatingRight(true);
                    break;
                case 32:
                    ship.firePrimary();
                    break;
                default:
                    break;
            }
        } else if(type.equals("keyUp")) {
            switch(e.getKeyCode()) {
                case 38:
                    ship.setMovingForward(false);
                    break;
                case 37:
                    ship.setRotatingLeft(false);
                    break;
                case 39:
                    ship.setRotatingRight(false);
                    break;
                default:
                    break;
            }
        }
    }

}
