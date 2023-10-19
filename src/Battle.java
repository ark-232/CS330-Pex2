import java.awt.*;
import javax.swing.JOptionPane;

public class Battle {

    private static final int TIME_STEP = 100;
    private boolean pause = false;
    private final DrawingPanel panel;
    private final Graphics2D g;
    private final Army blueArmy;
    private final Army redArmy;

    public Battle(DrawingPanel panel, Graphics2D g, Army blueArmy, Army redArmy) {
        this.panel = panel;
        this.g = g;
        this.blueArmy = blueArmy;
        this.redArmy = redArmy;

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());

        blueArmy.draw();
        redArmy.draw();
    }

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
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, panel.getWidth(), panel.getHeight());

                // Display army counts
                g.setColor(Color.BLUE);
                g.drawString("Blue Army: " + blueArmy.count(), 10, 15);

                g.setColor(Color.RED);
                g.drawString("Red Army: " + redArmy.count(), panel.getWidth() - 100, 15);

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

