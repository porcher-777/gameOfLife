import java.awt.*;
import javax.swing.*;

public class GameBoard extends JPanel {

    // ----------------------- Attributes ------------------------
    private boolean status; // if game in session = true, false otherwise
    private final Tile[][] tiles;

    // ----------------------- Constructors ----------------------

    public GameBoard() {
        super(new GridLayout(50, 50)); // Create a 50x50 grid layout
        this.status = false;
        this.tiles = new Tile[50][50];

        // Initialize and add each tile as a component
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                this.tiles[i][j] = new Tile(i, j, this.tiles);
                this.add(this.tiles[i][j]); // Add the tile to the JPanel
            }
        }
    }

    // ----------------------- Setters/Getters -------------------

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;

        // Propagate the status to all tiles
        for (Tile[] tile : tiles) {
            for (Tile tile1 : tile) {
                tile1.setRunning(status); // Update running status for each tile
            }
        }
    }

    // ----------------------- Methods ---------------------------

    // Start each tile's thread
    public void startGame() {
        for (Tile[] tile : tiles) {
            for (Tile tile1 : tile) {
                Thread tileThread = new Thread(tile1);
                tileThread.start(); // Start each tile's thread
            }
        }
    }
}
