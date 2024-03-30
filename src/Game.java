import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game extends JPanel {
    private final int rows;
    private final int cols;
    private final int cellSize;
    private World world;
    private String[][] colors;

    public Game() {
        rows = 25;
        cols = 25;
        cellSize = 30;
        world = new World(rows, cols);

        setupGUI();

        setPreferredSize(new Dimension(cols * 40, rows * 40));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                world.runWorld();
                repaint();
            }
        });
    }
    private void setupGUI() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Plants Vs Herbivores");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new GUI(this.rows, this.cols, world));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}