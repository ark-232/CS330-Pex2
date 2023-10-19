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

class EvadingWarrior extends Warrior {
    private static final int lookRange = 100; // Example value, this can be changed

    public EvadingWarrior(double x, double y, double speed, int size, Color color, double courage, double strength) {
        super(x, y, speed, size, color, courage, strength, true);
    }

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

