import java.awt.*;
import javax.swing.*;

public class Tile extends JPanel implements Runnable {

    // ----------------------- Attributes ------------------------
    private boolean alive;
    private boolean running = true;
    private final int row, col;
    private final Tile[][] grid; // Reference to the grid of all tiles

    // ----------------------- Constructors ----------------------

    public Tile(int row, int col, Tile[][] grid) {
        this.alive = Math.random() < 0.5;
        this.setPreferredSize(new Dimension(10, 10)); // Set tile size
        this.row = row;
        this.col = col;
        this.grid = grid;
    }

    // ----------------------- Setters/Getters -------------------

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
        repaint(); // Repaint the tile whenever its state changes
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    // ----------------------- Methods ---------------------------

    // Count the number of live neighbors
    private int countLiveNeighbors() {
        int liveNeighbors = 0;

        // Loop through the 3x3 grid surrounding this tile (including itself)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int neighborRow = this.row + i;
                int neighborCol = this.col + j;

                // Check if the neighbor is within bounds and not the current tile
                if (neighborRow >= 0 && neighborRow < this.grid.length &&
                        neighborCol >= 0 && neighborCol < this.grid[0].length &&
                        !(i == 0 && j == 0)) {

                    // Check if the neighboring tile is alive
                    if (this.grid[neighborRow][neighborCol].isAlive()) {
                        liveNeighbors++;
                    }
                }
            }
        }

        return liveNeighbors;
    }

    private void updateTileState() {
        int liveNeighbors = countLiveNeighbors();

        // Apply Game of Life rules
        if (this.alive && (liveNeighbors < 2 || liveNeighbors > 3)) {
            setAlive(false); // Underpopulation or overpopulation
        } else if (!this.alive && liveNeighbors == 3) {
            setAlive(true); // Reproduction
        }
    }

    @Override
    public void run() {
        while (this.running) {
            updateTileState(); // Each tile updates itself
            try {
                Thread.sleep(100); // Control how often the tile updates
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.alive) {
            g.setColor(Color.BLACK); // Alive tiles are black
        } else {
            g.setColor(Color.WHITE); // Dead tiles are white
        }
        g.fillRect(0, 0, getWidth(), getHeight());

        // Optional: Draw grid lines
        g.setColor(Color.GRAY);
        g.drawRect(0, 0, getWidth(), getHeight());
    }
}
