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
    private Army adversary;
    private final double speed;
    private final int size;
    private final Color color;
    private int leftX;
    private int rightX;
    private int topY;
    private int bottomY;

    // Constructor
    public Army(int number, int placement, double speed, int size, Color color,
                double courage, double strength, boolean evadingArmy) {
        if(screenWidth == 0 || screenHeight == 0) {
            throw new RuntimeException("Screen width and height not set");
        }
        this.speed = speed;
        this.size = size;
        this.color = color;
        warriors = new Warrior[number];
        setPlacementParameters(placement);

        for (int i = 0; i < number; i++) {
            int x = rand.nextInt(rightX - leftX) + leftX;
            int y = rand.nextInt(bottomY - topY) + topY;
            warriors[i] = new Warrior(x, y, speed, size, color, courage, strength, evadingArmy);

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

    void setAdversary(Army adversary) {
        this.adversary = adversary;
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
            w.fight();
        }
    }

    void draw() {
        for (Warrior w : warriors) {
            w.draw();
        }
    }

    private void setPlacementParameters(int placement) {
        switch (placement) {
            case LEFT_SIDE:
                leftX = (int) (0.1 * screenWidth);
                rightX = (int) (0.2 * screenWidth);
                topY = (int) (0.3 * screenHeight);
                bottomY = (int) (0.7 * screenHeight);
                break;
            case RIGHT_SIDE:
                leftX = (int) (0.8 * screenWidth);
                rightX = (int) (0.9 * screenWidth);
                topY = (int) (0.3 * screenHeight);
                bottomY = (int) (0.7 * screenHeight);
                break;
            case TOP_SIDE:
                leftX = (int) (0.3 * screenWidth);
                rightX = (int) (0.7 * screenWidth);
                topY = (int) (0.1 * screenHeight);
                bottomY = (int) (0.2 * screenHeight);
                break;
            case BOTTOM_SIDE:
                leftX = (int) (0.3 * screenWidth);
                rightX = (int) (0.7 * screenWidth);
                topY = (int) (0.8 * screenHeight);
                bottomY = (int) (0.9 * screenHeight);
                break;

        }
    }
}
