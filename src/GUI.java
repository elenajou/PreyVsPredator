import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI extends JPanel {
    private final int rows;
    private final int cols;
    private final int cellSize;
    private World world;
    private String[][] colors;

    public GUI(int rows, int cols, World world) {
        this.rows = rows;
        this.cols = cols;
        this.cellSize = 25;
        this.world = world;

        setPreferredSize(new Dimension(cols * 40, rows * 40));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                world.runWorld();
                repaint();
            }
        });
    }

    public Dimension getPreferredSize() {
        return new Dimension(cols * cellSize, rows * cellSize);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        getResidents(world);

        super.paintComponent(g);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * cellSize;
                int y = row * cellSize;
                // Draw the cell based on its state
                if ((world.getWorld())[row][col].getResident() == null) {
                    g.setColor(Color.WHITE);
                } else if ((world.getWorld())[row][col].getResident().getColor().equals("GREEN")) {
                    g.setColor(Color.GREEN);
                } else if ((world.getWorld())[row][col].getResident().getColor().equals("YELLOW")) {
                    g.setColor(Color.YELLOW);
                } else if ((world.getWorld())[row][col].getResident().getColor().equals("RED")) {
                    g.setColor(Color.RED);
                } else if ((world.getWorld())[row][col].getResident().getColor().equals("BLUE")) {
                    g.setColor(Color.BLUE);
                }
                g.fillRect(x, y, cellSize, cellSize);

                // Draw the grid lines
                g.setColor(Color.BLACK);
                g.drawRect(x, y, cellSize, cellSize);
            }
        }
    }

    public void getResidents(World w) {
        colors = new String[rows][cols];
        for (int x = 0; x < w.getWidth(); x++) {
            for (int y = 0; y < w.getHeight(); y++) {
                LivingThing res = (w.getWorld())[x][y].getResident();
                colors[x][y] = (res != null) ? res.getColor() : "WHITE";
            }
        }
    }
}