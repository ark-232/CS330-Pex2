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
 * The blue army is represented by the Rust logo and the red army by the Python logo.
 *
 <h3>Features:</h3>
 * <ul>
 *   <li><strong>Complex Agro System:</strong> Warriors aren't just mindless sprites; they engage the nearest foe. And if ambushed? They retaliate. Multiple assailants? They prioritize the closest.</li>
 *   <li><strong>Tactical Movement:</strong> Witness the art of war as units tactically attract and repel, ensuring coordinated maneuvers on the battlefield.</li>
 *   <li><strong>Evade & Route:</strong> It's not all about brute force. Units can evade, and groups can tactically route adversaries, driving them off the terrain.</li>
 *   <li><strong>Visual Delight:</strong> The battleground isn't just any drab arena. It boasts a custom background image crafted with precision. And our warriors? Custom unit images that stand out.</li>
 * </ul>
 *
 * <h3>Documentation Credits:</h3>
 * <p>Background artistry achieved with the magic of Dalle. Coding aided by the wisdom of CoPilot. Dr. Hadfield lent his expertise on the captivating fighting logic on October 19.</p>
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
