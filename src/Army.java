import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Represents an army consisting of multiple warriors.
 * This army is placed on a battlefield and can be positioned on different sides of the screen.
 *
 * @author C2C Enrique Oti
 * @version 1.0
 */
public class Army {

    /** Constants representing different sides of the battlefield for placement. */
    public static final int LEFT_SIDE = 1;
    /** Constants representing different sides of the battlefield for placement. */
    public static final int RIGHT_SIDE = 2;
    /** Constants representing different sides of the battlefield for placement. */

    public static final int TOP_SIDE = 3;
    /** Constants representing different sides of the battlefield for placement. */

    public static final int BOTTOM_SIDE = 4;

    /**
     * The Graphics2D object used for rendering.
     */
    private static Graphics2D g;
    /**
     * The width of the screen.
     */
    private static int screenWidth;
    /**
     * The height of the screen.
     */
    private static int screenHeight;
    /**
     * The Random object used for random number generation.
     */
    private static final Random rand = new Random();

    private final Warrior[] warriors;
    /**
     * The speed of each warrior.
     */
    private final double speed;
    /**
     * The size of each warrior.
     */
    private final int size;
    /**
     * The logo/image representing each warrior.
     */
    private final BufferedImage logo;
    /**
     * The x-coordinate of the left side of the battlefield.
     */
    private int leftX;
    /**
     * The x-coordinate of the right side of the battlefield.
     */
    private int rightX;
    /**
     * The y-coordinate of the top side of the battlefield.
     */
    private int topY;
    /**  The y-coordinate of the bottom side of the battlefield. */
    /**
     * The y-coordinate of the bottom side of the battlefield.
     */
    private int bottomY;

    /**
     * Initializes an Army with specified parameters.
     *
     * @param number The number of warriors in this army.
     * @param placement The placement side on the battlefield.
     * @param speed The speed of each warrior.
     * @param size The size of each warrior.
     * @param logo The logo/image representing each warrior.
     * @param courage The courage factor of each warrior.
     * @param strength The strength factor of each warrior.
     * @param evadingArmy If the army consists of evading warriors.
     */
    public Army(int number, int placement, double speed, int size, BufferedImage logo, double courage, double strength, boolean evadingArmy) {
        if (screenWidth == 0 || screenHeight == 0) {
            throw new RuntimeException("Screen width and height not set");
        }
        this.speed = speed;
        this.size = size;
        this.logo = logo;
        warriors = new Warrior[number];
        setPlacementParameters(placement);

        int battlefieldLine = screenWidth / 2;
        int spread = 200;

        for (int i = 0; i < number; i++) {
            int x;
            if (placement == LEFT_SIDE) {
                x = rand.nextInt(spread) + (battlefieldLine - spread);
            } else {
                x = rand.nextInt(spread) + battlefieldLine;
            }
            int y = rand.nextInt(bottomY - topY) + topY;
            if (evadingArmy) {
                warriors[i] = new EvadingWarrior(x, y, speed, size, logo, courage, strength);
            } else {
                warriors[i] = new Warrior(x, y, speed, size, logo, courage, strength, false);
            }
        }
    }

    // Static methods to set and get the shared Graphics2D and screen dimensions

    /**
     * Sets the Graphics2D object for rendering.
     * @param g The Graphics2D object.
     */
    static void setGraphics(Graphics2D g) {
        Army.g = g;
    }

    /**
     * Sets the screen width.
     * @param width The width of the screen.
     */
    static void setScreenWidth(int width) {
        Army.screenWidth = width;
    }

    /**
     * Sets the screen height.
     * @param height The height of the screen.
     */
    static void setScreenHeight(int height) {
        Army.screenHeight = height;
    }

    /**
     * Returns the Graphics2D object.
     * @return The Graphics2D object.
     */
    static Graphics2D getGraphics() {
        return g;
    }

    /**
     * Returns the screen width.
     * @return The width of the screen.
     */
    static int getScreenWidth() {
        return screenWidth;
    }

    /**
     * Returns the screen height.
     * @return The height of the screen.
     */
    static int getScreenHeight() {
        return screenHeight;
    }

    /**
     * Returns the array of warriors.
     * @return The warriors in the army.
     */
    Warrior[] getWarriors() {
        return warriors;
    }

    /**
     * Counts and returns the number of alive warriors.
     * @return The number of alive warriors.
     */
    int count() {
        int aliveCount = 0;
        for (Warrior w : warriors) {
            if (w.isAlive()) aliveCount++;
        }
        return aliveCount;
    }

    /** Determines the next positions for each warrior. */
    void determineNextPositions() {
        for (Warrior w : warriors) {
            w.determineNextPosition();
        }
    }

    /** Updates the position of each warrior to their next determined position. */
    void updateToNextPosition() {
        for (Warrior w : warriors) {
            w.updateToNextPosition();
        }
    }

    /** Commands each warrior to engage in a fight. */
    void fight() {
        for (Warrior w : warriors) {
            if (w.isAlive()) {
                w.fight();
            }
        }
    }

    /** Draws each warrior on the battlefield. */
    void draw() {
        for (Warrior w : warriors) {
            if (w.isAlive()) {
                w.draw();
            }
        }
    }

    /**
     * Sets the placement parameters based on the given side of the battlefield.
     * @param placement The side of the battlefield for placement.
     */
    private void setPlacementParameters(int placement) {
        switch (placement) {
            case LEFT_SIDE:
                leftX = 0;
                rightX = screenWidth / 4;
                topY = 0;
                bottomY = screenHeight;
                break;
            case RIGHT_SIDE:
                leftX = 3 * screenWidth / 4;
                rightX = screenWidth;
                topY = 0;
                bottomY = screenHeight;
                break;
            case TOP_SIDE:
                leftX = 0;
                rightX = screenWidth;
                topY = 0;
                bottomY = screenHeight / 4;
                break;
            case BOTTOM_SIDE:
                leftX = 0;
                rightX = screenWidth;
                topY = 3 * screenHeight / 4;
                bottomY = screenHeight;
                break;
            default:
                throw new IllegalArgumentException("Unknown placement");
        }
    }
}
