import javax.swing.*;

public class DrawingPanel {
    private JFrame frame;

    public DrawingPanel(int width, int height) {
        frame = new JFrame("Battle Simulation");
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
