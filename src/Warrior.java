import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Represents a warrior on a battlefield.
 * <p>
 * This class provides methods to update and determine the warrior's position, engage in combat, and visualize the warrior on the screen.
 * </p>
 *
 * @author C2C Enrique Oti
 * @version 1.0
 */
public class Warrior {
    private static final Random rand = new Random();
    private static Graphics2D g;
    private static int screenWidth;
    private static int screenHeight;
    protected Vector330 pos;
    protected Vector330 nextPos;
    protected double speed;

    private final BufferedImage logo;

    private final int size;
    private final double courage;
    private final double strength;
    private boolean alive;
    private Army adversary;
    private Army myFriendlies;

    /**
     * Constructor for the Warrior class.
     * @param x The x-coordinate of the warrior's position.
     * @param y The y-coordinate of the warrior's position.
     * @param speed The speed of the warrior.
     * @param size The size of the warrior's logo.
     * @param logo The image logo of the warrior.
     * @param courage The courage factor of the warrior.
     * @param strength The strength factor of the warrior.
     * @param evadingArmy A boolean indicating if the warrior is evading.
     */
    public Warrior(double x, double y, double speed, int size, BufferedImage logo, double courage, double strength, boolean evadingArmy) {
        this.pos = new Vector330(x, y);
        this.speed = speed;
        this.size = size;
        this.logo = logo;

        this.courage = courage;
        this.strength = strength;
        this.alive = true;

        if (evadingArmy) {
            // Initialize as evading warrior or any specific logic if needed
        }
    }

    /**
     * Sets the graphics object for the class.
     *
     * @param g The graphics object to be set.
     */
    static void setGraphics(Graphics2D g) {
        Warrior.g = g;
    }

    /**
     * Sets screen width.
     *
     * @param width Screen width.
     */
    static void setScreenWidth(int width) {
        Warrior.screenWidth = width;
    }
    /**
     * Sets screen height.
     *
     * @param height Screen height.
     */
    static void setScreenHeight(int height) {
        Warrior.screenHeight = height;
    }
    /**
     * Gets the graphics context.
     *
     * @return Graphics context.
     */
    static Graphics2D getGraphics() {
        return g;
    }
    /**
     * Returns the strength of the warrior.
     *
     * @return A double representing the warrior's strength.
     */
    public double getStrength() {return strength;}

    /**
     * Gets screen width.
     *
     * @return Screen width.
     */
    static int getScreenWidth() {
        return screenWidth;
    }
    /**
     * Gets screen height.
     *
     * @return Screen height.
     */
    static int getScreenHeight() {
        return screenHeight;
    }
    /**
     * Gets random generator.
     *
     * @return Random generator.
     */
    static Random getRand() {
        return rand;
    }
    /**
     * Sets adversary army.
     *
     * @param enemy Adversary army.
     */
    void setAdversary(Army enemy) {
        this.adversary = enemy;
    }
    /**
     * Sets friendly army.
     *
     * @param friendlies Friendly army.
     */
    void setMyFriendlies(Army friendlies) {
        this.myFriendlies = friendlies;
    }
    /**
     * Sets next position.
     *
     * @param nextPos Next position.
     */
    void setNextPosition(Vector330 nextPos) {
        this.nextPos = nextPos;
    }
    /**
     * Gets current position.
     *
     * @return Current position.
     */
    Vector330 getPosition() {
        return pos;
    }

    /**
     * Gets courage.
     * @return Courage.
     */
    double getCourage() {
        return courage;
    }

    /**
     * Gets speed.
     * @return Speed.
     */
    double getSpeed() {
        return speed;
    }
/**
     * Gets size.
     * @return Size.
     */
    int getSize() {
        return size;
    }
/**
     * Gets logo.
     * @return Logo.
     */
    boolean isAlive() {
        return alive;
    }
/**
     * Gets adversary.
     * @return Adversary.
     */
    Warrior[] getAdversaryWarriors() {
        return adversary.getWarriors();
    }

    /**
     * Gets friendly warriors.
     * @return Friendly warriors.
     */
    Warrior[] getFriendlyWarriors() {
        return myFriendlies.getWarriors();
    }

    /**
     * Updates to next position.
     */
    void updateToNextPosition() {
        if (alive) {
            pos = nextPos;
            if (pos.getX() < 0 || pos.getX() > screenWidth || pos.getY() < 0 || pos.getY() > screenHeight) {
                alive = false;
            }
        }
    }

    /**
     * Draws the warrior.
     */
    void draw() {
        if (alive) {
            g.drawImage(logo, (int) (pos.getX() - size / 2.0), (int) (pos.getY() - size / 2.0), size, size, null);
        }
    }

/**
     * Determines next position.
     */
    private Vector330 getAverageFriendliesPosition() {
        double avgX = 0;
        double avgY = 0;
        int count = 0;
        for (Warrior friendly : this.getFriendlyWarriors()) {
            if (!friendly.isAlive() || friendly == this) {
                continue;
            }
            avgX += friendly.getPosition().getX();
            avgY += friendly.getPosition().getY();
            count++;
        }
        return new Vector330(avgX / count, avgY / count);
    }

    /**
     * Determines next position.
     */
    private Vector330 getSeparationForce() {
        Vector330 force = new Vector330(0, 0);
        for (Warrior friendly : this.getFriendlyWarriors()) {
            if (friendly == this || !friendly.isAlive()) continue;
            double distance = this.pos.distanceTo(friendly.getPosition());
            if (distance < this.size * 2) {
                Vector330 repel = this.pos.subtract(friendly.getPosition());
                repel = repel.normalize();
                repel = repel.multiply(1.0 / distance);
                force = force.add(repel);
            }
        }
        return force;
    }

    /**
     * Determines next position.
     */
    void determineNextPosition() {
        Warrior nearestAdversary = findNearestAdversary();
        Vector330 separationForce = getSeparationForce();

        if (nearestAdversary != null) {
            Vector330 toAdversary = nearestAdversary.getPosition().subtract(this.pos).normalize();
            if (nearestAdversary.getStrength() > 2 * this.getStrength()) {
                toAdversary = toAdversary.multiply(-1); // Run away if adversary is too strong
            }
            toAdversary = toAdversary.add(separationForce).normalize();
            this.nextPos = this.pos.add(toAdversary.multiply(this.speed));
        } else {
            // If no adversary found, move based on separation force
            this.nextPos = this.pos.add(separationForce.multiply(this.speed));
        }
    }


/**
     * Fights.
     */
    void fight() {
        //dying by ghosts
        for (Warrior enemy : this.getAdversaryWarriors()) {
            //copilot here
            if (!enemy.isAlive()) {
                continue; // Skip dead warriors
            }
            if (this.pos.distanceTo(enemy.getPosition()) < this.size) {
                if (rand.nextDouble() < 0.05 * this.getStrength() / enemy.getStrength()) {
                    enemy.alive = false;
                }
                if (rand.nextDouble() < 0.05 * enemy.getStrength() / this.getStrength()) {
                    this.alive = false;
                }
            }
        }
    }

/**
     * Finds nearest adversary.
     * @return Nearest adversary.
     */
    Warrior findNearestAdversary() {
        double minDistance = Double.MAX_VALUE;
        Warrior nearest = null;
        for (Warrior enemy : this.getAdversaryWarriors()) {
            if (!enemy.isAlive()) {
                continue; // Skip dead warriors
            }
            double distance = this.pos.distanceTo(enemy.getPosition());
            if (distance < minDistance) {
                minDistance = distance;
                nearest = enemy;
            }
        }
        return nearest;
    }
}

/**
 * Represents an evading warrior on a battlefield.
 *
 */
class EvadingWarrior extends Warrior {
    private static final int lookRange = 100; // Example value, this can be changed

    public EvadingWarrior(double x, double y, double speed, int size, BufferedImage logo, double courage, double strength) {
        super(x, y, speed, size, logo, courage, strength, true); // Here, the constructor call uses logo instead of color
    }

    /**
     * Determines next position.
     */
    @Override
    void determineNextPosition() {
        Warrior closestEnemy = findNearestAdversary();
        int nearbyEnemies = countNeighbors(getAdversaryWarriors(), lookRange);
        int nearbyFriendlies = countNeighbors(getFriendlyWarriors(), lookRange);

        // If there are more enemies than friendlies in the specified range, evade the nearest enemy
        if (nearbyEnemies > nearbyFriendlies && closestEnemy != null) {
            Vector330 escapeDirection = this.pos.subtract(closestEnemy.getPosition()).normalize();
            this.nextPos = this.pos.add(escapeDirection.multiply(this.speed));
        } else {
            // If no overwhelming enemies are close, move as per a regular warrior
            super.determineNextPosition();
        }
    }

    /**
     * Counts neighbors.
     * @param others Others.
     * @param distance Distance.
     * @return Number of neighbors.
     */
    private int countNeighbors(Warrior[] others, int distance) {
        int count = 0;
        for (Warrior w : others) {
            if (w.getPosition().distanceTo(getPosition()) <= distance) {
                count++;
            }
        }
        return count;
    }
}

