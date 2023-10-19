import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Army {

    // Fields
    public static final int LEFT_SIDE = 1;
    public static final int RIGHT_SIDE = 2;
    public static final int TOP_SIDE = 3;
    public static final int BOTTOM_SIDE = 4;

    private static Graphics2D g;
    private static int screenWidth;
    private static int screenHeight;
    private static final Random rand = new Random();

    private final Warrior[] warriors;
    private final double speed;
    private final int size;
    private final Color color;
    private int leftX;
    private int rightX;
    private int topY;
    private int bottomY;

    // Constructor
    public Army(int number, int placement, double speed, int size, Color color, double courage, double strength, boolean evadingArmy) {
        if (screenWidth == 0 || screenHeight == 0) {
            throw new RuntimeException("Screen width and height not set");
        }
        this.speed = speed;
        this.size = size;
        this.color = color;
        warriors = new Warrior[number];
        setPlacementParameters(placement);

        int battlefieldLine = screenWidth / 2;
        int spread = 200; // Width on either side of the battlefield line where warriors can initially be placed.

        for (int i = 0; i < number; i++) {
            int x;
            if (placement == LEFT_SIDE) {
                x = rand.nextInt(spread) + (battlefieldLine - spread);
            } else {
                x = rand.nextInt(spread) + battlefieldLine;
            }
            int y = rand.nextInt(bottomY - topY) + topY;
            if (evadingArmy) {
                warriors[i] = new EvadingWarrior(x, y, speed, size, color, courage, strength);
            } else {
                warriors[i] = new Warrior(x, y, speed, size, color, courage, strength, false);
            }
        }

    }

    // Methods
    static void setGraphics(Graphics2D g) {
        Army.g = g;
    }

    static void setScreenWidth(int width) {
        Army.screenWidth = width;
    }

    static void setScreenHeight(int height) {
        Army.screenHeight = height;
    }

    static Graphics2D getGraphics() {
        return g;
    }

    static int getScreenWidth() {
        return screenWidth;
    }

    static int getScreenHeight() {
        return screenHeight;
    }

    Warrior[] getWarriors() {
        return warriors;
    }

    int count() {
        int aliveCount = 0;
        for (Warrior w : warriors) {
            if (w.isAlive()) aliveCount++;
        }
        return aliveCount;
    }

    void determineNextPositions() {
        for (Warrior w : warriors) {
            w.determineNextPosition();
        }
    }

    void updateToNextPosition() {
        for (Warrior w : warriors) {
            w.updateToNextPosition();
        }
    }

    void fight() {
        for (Warrior w : warriors) {
            if (w.isAlive()) {
                w.fight();
            }
        }
    }

    void draw() {
        for (Warrior w : warriors) {
            if (w.isAlive()) {
                w.draw();
            }
        }
    }

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