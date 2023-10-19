import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * Represents a battle between two armies on a drawing panel.
 *
 * @author C2C Enrique Oti
 * @version 1.0
 */
public class Battle {

    /** The time delay between each step of the battle. */
    private static final int TIME_STEP = 100;

    /** Indicates whether the battle is currently paused. */
    private boolean pause = false;

    /** The panel on which the battle is drawn. */
    private final DrawingPanel panel;

    /** The Graphics2D object used to render the battle. */
    private final Graphics2D g;

    /** The first army involved in the battle, colored blue. */
    private final Army blueArmy;

    /** The second army involved in the battle, colored red. */
    private final Army redArmy;

    /** The background image for the battle. */
    private BufferedImage background;

    /**
     * Initializes a new Battle with specified parameters.
     *
     * @param panel The panel on which the battle will be drawn.
     * @param g The Graphics2D object used for rendering.
     * @param blueArmy The first army, colored blue.
     * @param redArmy The second army, colored red.
     */
    public Battle(DrawingPanel panel, Graphics2D g, Army blueArmy, Army redArmy, String backgroundPath) {
        this.panel = panel;
        this.g = g;
        this.blueArmy = blueArmy;
        this.redArmy = redArmy;

        try {
            // Load the background image from the given path
            background = ImageIO.read(new File(backgroundPath));
        } catch (IOException e) {
            System.err.println("Failed to load background image!");
            e.printStackTrace();
        }

        // Draw the background image
        g.drawImage(background, 0, 0, panel.getWidth(), panel.getHeight(), null);

        blueArmy.draw();
        redArmy.draw();
    }

    /**
     * Executes the battle simulation between the two armies.
     * The battle continues until one army is defeated or the user intervenes.
     */
    public void doBattle() {
        JOptionPane.showMessageDialog(null, "Click OK to start the battle!");

        while (true) {
            if (panel.mouseClickHasOccurred(DrawingPanel.LEFT_BUTTON)) {
                break;  // End the simulation on a left click
            }

            if (panel.keyHasBeenHit(' ')) {
                pause = !pause;  // Toggle pause state on spacebar press
            }

            if (blueArmy.count() == 0 || redArmy.count() == 0) {
                break; // Exit the battle loop when one army is defeated
            }

            if (!pause) {
                // Instead of filling with light gray, draw the background image
                g.drawImage(background, 0, 0, panel.getWidth(), panel.getHeight(), null);

                // Display army counts
                g.setColor(Color.BLACK);
                g.drawString("RUST: " + blueArmy.count(), 10, 15);

                g.setColor(Color.BLACK);
                g.drawString("PYTHON: " + redArmy.count(), panel.getWidth() - 100, 15);

                blueArmy.determineNextPositions();
                redArmy.determineNextPositions();

                blueArmy.updateToNextPosition();
                redArmy.updateToNextPosition();

                blueArmy.fight();
                redArmy.fight();

                blueArmy.draw();
                redArmy.draw();

                panel.sleep(TIME_STEP);
                panel.copyGraphicsToScreen();
            }
        }
    }

}
