import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;

/**
 * Main class that initializes and simulates a battle between two armies represented by logos.
 * <p>
 * The blue army is represented by the Java logo and the red army by the Python logo.
 *
 * @author C2C Enrique Oti
 * @version 1.0
 */
public class Main {

    /**
     * Size of the blue army.
     */
    private static final int BLUE_ARMY_SIZE = 250;
    /**
     * Size of the red army.
     */
    private static final int RED_ARMY_SIZE = 250;
    /**
     * Width of the window.
     */
    private static final int WINDOW_WIDTH = 800;
    /**
     * Height of the window.
     */
    private static final int WINDOW_HEIGHT = 600;

    /**
     * BufferedImage object to hold the Java logo.
     */
    private static BufferedImage rustLogo;
    /**
     * BufferedImage object to hold the Python logo.
     */
    private static BufferedImage pythonLogo;

    /**
     * Static block to initialize the logo images.
     */
    static {
        try {
            rustLogo = ImageIO.read(new File("resources/rust-logo.png"));
            pythonLogo = ImageIO.read(new File("resources/python.svg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Default constructor for the Main class.
     */
    public Main() {
        // Any initialization code for Main could go here.
    }

    /**
     * Main method to initialize and simulate the battle.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) throws InterruptedException {
        // Initialize Graphics and Screen Size
        DrawingPanel panel = new DrawingPanel(WINDOW_WIDTH, WINDOW_HEIGHT);
        Graphics2D g = panel.getGraphics();
        Army.setGraphics(g);
        Army.setScreenWidth(WINDOW_WIDTH);
        Army.setScreenHeight(WINDOW_HEIGHT);
        Warrior.setGraphics(g);
        Warrior.setScreenWidth(WINDOW_WIDTH);
        Warrior.setScreenHeight(WINDOW_HEIGHT);

        // Initialize Blue Army
        Army blueArmy = new Army(BLUE_ARMY_SIZE, Army.LEFT_SIDE, 5.0, 30, rustLogo, 100.0, 3.0, false);
        // Initialize Red Army
        Army redArmy = new Army(RED_ARMY_SIZE, Army.RIGHT_SIDE, 5.0, 30, pythonLogo, 100.0, 3.0, false);

        for (Warrior warrior : blueArmy.getWarriors()) {
            warrior.setAdversary(redArmy);  // Blue warriors should have the Red army as the adversary
            warrior.setMyFriendlies(blueArmy);
        }
        for (Warrior warrior : redArmy.getWarriors()) {
            warrior.setAdversary(blueArmy);  // Red warriors should have the Blue army as the adversary
            warrior.setMyFriendlies(redArmy);
        }

        // Initialize Battle and start
        Battle battle = new Battle(panel, g, blueArmy, redArmy, "resources/background.png");
        battle.doBattle();

        String message = "";
        if(blueArmy.count() == 0) {
            sleep(500);
            message = "Python wins!";
        } else if(redArmy.count() == 0) {
            sleep(500);
            message = "Rust wins!";
        }
        JOptionPane.showMessageDialog(null, message);
    }
}
