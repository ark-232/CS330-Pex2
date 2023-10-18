import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Warrior {
    private static final Random rand = new Random();
    private static Graphics2D g;
    private static int screenWidth;
    private static int screenHeight;
    protected Vector330 pos;
    protected Vector330 nextPos;
    protected double speed;

    private final int size;
    private final Color color;
    private final double courage;
    private final double strength;
    private boolean alive;
    private Army adversary;
    private Army myFriendlies;

    public Warrior(double x, double y, double speed, int size, Color color, double courage, double strength, boolean evadingArmy) {
        this.pos = new Vector330(x, y);
        this.speed = speed;
        this.size = size;
        this.color = color;
        this.courage = courage;
        this.strength = strength;
        this.alive = true;
        if (evadingArmy) {
            // Initialize as evading warrior or any specific logic if needed
        }
    }


    static void setGraphics(Graphics2D g) {
        Warrior.g = g;
    }

    static void setScreenWidth(int width) {
        Warrior.screenWidth = width;
    }

    static void setScreenHeight(int height) {
        Warrior.screenHeight = height;
    }

    static Graphics2D getGraphics() {
        return g;
    }

    public double getStrength() {return strength;}


    static int getScreenWidth() {
        return screenWidth;
    }

    static int getScreenHeight() {
        return screenHeight;
    }

    static Random getRand() {
        return rand;
    }

    void setAdversary(Army enemy) {
        this.adversary = enemy;
    }

    void setMyFriendlies(Army friendlies) {
        this.myFriendlies = friendlies;
    }

    void setNextPosition(Vector330 nextPos) {
        this.nextPos = nextPos;
    }

    Vector330 getPosition() {
        return pos;
    }

    double getCourage() {
        return courage;
    }

    double getSpeed() {
        return speed;
    }

    int getSize() {
        return size;
    }

    boolean isAlive() {
        return alive;
    }

    Warrior[] getAdversaryWarriors() {
        return adversary.getWarriors();
    }

    Warrior[] getFriendlyWarriors() {
        return myFriendlies.getWarriors();
    }

    void updateToNextPosition() {
        if (alive) {
            pos = nextPos;
            if (pos.getX() < 0 || pos.getX() > screenWidth || pos.getY() < 0 || pos.getY() > screenHeight) {
                alive = false;
            }
        }
    }

    void draw() {
        if (alive) {
            g.setColor(color);
            g.fillOval((int) (pos.getX() - size / 2.0), (int) (pos.getY() - size / 2.0), size, size);
        }
    }

    void determineNextPosition() {
        Warrior nearestAdversary = findNearestAdversary();
        if (nearestAdversary != null) {
            double directionX = nearestAdversary.getPosition().getX() - this.pos.getX();
            double directionY = nearestAdversary.getPosition().getY() - this.pos.getY();

            // Normalize direction vector (to get unit direction)
            double magnitude = Math.sqrt(directionX * directionX + directionY * directionY);
            directionX /= magnitude;
            directionY /= magnitude;

            // Move towards the enemy by a step of "speed"
            double newX = this.pos.getX() + directionX * this.speed;
            double newY = this.pos.getY() + directionY * this.speed;

            this.nextPos = new Vector330(newX, newY);
        } else {
            // If no adversary found, move randomly (this shouldn't really happen, but it's a fallback)
            double newX = this.pos.getX() + (rand.nextInt(3) - 1) * this.speed;
            double newY = this.pos.getY() + (rand.nextInt(3) - 1) * this.speed;
            this.nextPos = new Vector330(newX, newY);
        }
    }


    void fight() {
        for (Warrior enemy : this.getAdversaryWarriors()) {
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


    Warrior findNearestAdversary() {
        double minDistance = Double.MAX_VALUE;
        Warrior nearest = null;
        for (Warrior enemy : this.getAdversaryWarriors()) {
            double distance = this.pos.distanceTo(enemy.getPosition());
            if (distance < minDistance) {
                minDistance = distance;
                nearest = enemy;
            }
        }
        return nearest;
    }

}

class EvadingWarrior extends Warrior {
    private static final int lookRange = 100; // Example value, this can be changed

    public EvadingWarrior(double x, double y, double speed, int size, Color color, double courage, double strength) {
        super(x, y, speed, size, color, courage, strength, true);
    }

    @Override
    void determineNextPosition() {
        // The evading warrior tries to move away from close enemies.
        Warrior closestEnemy = findNearestAdversary();

        if (closestEnemy != null && this.pos.distanceTo(closestEnemy.getPosition()) < lookRange) {
            double newX = this.pos.getX() - (closestEnemy.getPosition().getX() - this.pos.getX()) * this.speed;
            double newY = this.pos.getY() - (closestEnemy.getPosition().getY() - this.pos.getY()) * this.speed;
            this.nextPos = new Vector330(newX, newY);
        } else {
            // If no enemies are close, move randomly.
            super.determineNextPosition();
        }
    }

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
