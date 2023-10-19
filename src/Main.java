import javax.swing.*;
import java.awt.*;

public class Main {

    private static final int BLUE_ARMY_SIZE = 500;
    private static final int RED_ARMY_SIZE = 250;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    public Main() {
        // Any initialization code for Main could go here.
    }

    public static void main(String[] args) {
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
        Army blueArmy = new Army(BLUE_ARMY_SIZE, Army.LEFT_SIDE, 10.0, 10, Color.BLUE, 100.0, 2.0, false);
        // Initialize Red Army
        Army redArmy = new Army(RED_ARMY_SIZE, Army.RIGHT_SIDE, 10.0, 10, Color.RED, 100.0, 3.0, true);


        for (Warrior warrior : blueArmy.getWarriors()) {
            warrior.setAdversary(redArmy);  // Blue warriors should have the Red army as the adversary
            warrior.setMyFriendlies(blueArmy);
        }
        for (Warrior warrior : redArmy.getWarriors()) {
            warrior.setAdversary(blueArmy);  // Red warriors should have the Blue army as the adversary
            warrior.setMyFriendlies(redArmy);
        }

        // Initialize Battle and start
        Battle battle = new Battle(panel, g, blueArmy, redArmy);
        battle.doBattle();

        String message = "";
        if(blueArmy.count() == 0) {
            message = "Red Army wins!";
        } else if(redArmy.count() == 0) {
            message = "Blue Army wins!";
        }
        JOptionPane.showMessageDialog(null, message);
    }
}
