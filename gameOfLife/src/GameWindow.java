
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener {

    // ----------------------- Attributes ------------------------
    private final JButton start;
    private final JButton stop;
    private final JPanel mainPanel;
    private final JPanel buttonPanel;
    private final GameBoard game;
    private Thread gameThread;

    // ----------------------- Constructors ----------------------

    public GameWindow() {

        // -------------------- JFrame initialisation --------------------------
        super();
        this.setSize(515, 600);
        this.setTitle("Game of Life");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // -------------------- Components initialisation ----------------------
        this.start = new JButton("Start");
        this.stop = new JButton("Stop");
        this.mainPanel = new JPanel(new BorderLayout());
        this.buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.game = new GameBoard();

        // -------------------- Action Listener --------------------------

        this.start.addActionListener(this::actionPerformed);
        this.stop.addActionListener(this::actionPerformed);

        // -------------------- Layout management --------------------------

        this.mainPanel.add(this.game, BorderLayout.CENTER);
        this.buttonPanel.add(this.start);
        this.buttonPanel.add(this.stop);
        this.mainPanel.add(this.buttonPanel, BorderLayout.SOUTH);

        this.add(this.mainPanel);
        this.setVisible(true);
    }

    // ----------------------- Setters/Getters -------------------
    // ----------------------- Methods ---------------------------
    // ----------------------- Override Methods ------------------

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Start".equals(e.getActionCommand())) {
            if (gameThread == null || !gameThread.isAlive()) {
                game.setStatus(true);
                game.startGame(); // Start individual tile threads
            } else {
                game.setStatus(true); // Resume the game if it's paused
            }
        }

        if ("Stop".equals(e.getActionCommand())) {
            this.game.setStatus(false); // Pause the game
        }
    }
}
