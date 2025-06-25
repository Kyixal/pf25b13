import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class GameMain extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final String TITLE = "Connect-4";
    public static final Color COLOR_BG = Color.WHITE;
    public static final Color COLOR_BG_STATUS = new Color(255, 254, 245);
    public static final Color COLOR_CROSS = new Color(130, 194, 147);
    public static final Color COLOR_NOUGHT = new Color(250, 214, 147);
    public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

    private Board board;
    private State currentState;
    private Seed currentPlayer;
    private int crossScore = 0;
    private int noughtScore = 0;
    private JLabel statusBar;
    private JLabel scoreLabel;

    public GameMain() {
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int col = mouseX / Cell.SIZE;

                if (currentState == State.PLAYING) {
                    if (col >= 0 && col < Board.COLS) {
                        for (int row = Board.ROWS - 1; row >= 0; row--) {
                            if (board.cells[row][col].content == Seed.NO_SEED) {
                                // Update board[][] and return the new game state after the move
                                currentState = board.stepGame(currentPlayer, row, col);

                                // Update score
                                if (currentState == State.CROSS_WON) {
                                    crossScore++;
                                    updateScoreLabel();
                                } else if (currentState == State.NOUGHT_WON) {
                                    noughtScore++;
                                    updateScoreLabel();
                                }
                                if (currentState == State.PLAYING) {
                                    SoundEffect.TOKEN.play();
                                } else {
                                    SoundEffect.DONE.play();
                                }
                                // Switch player
                                currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                                break;
                            }
                        }
                    }
                } else {
                    newGame();
                }
                // Refresh the drawing canvas
                repaint();  // Callback paintComponent()
            }
        });

        statusBar = new JLabel();
        statusBar.setFont(FONT_STATUS);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setOpaque(true);
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

        // Score label
        scoreLabel = new JLabel("Score - Player 1: 0 | Player 2: 0");
        scoreLabel.setFont(FONT_STATUS);
        scoreLabel.setBackground(COLOR_BG_STATUS);
        scoreLabel.setOpaque(true);
        scoreLabel.setHorizontalAlignment(JLabel.RIGHT);
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

        // Wrap both status bar and score in a horizontal panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(statusBar, BorderLayout.WEST);
        bottomPanel.add(scoreLabel, BorderLayout.EAST);
        bottomPanel.setBackground(COLOR_BG_STATUS);
        bottomPanel.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, 30));

        this.setLayout(new BorderLayout());
        this.add(bottomPanel, BorderLayout.SOUTH);

        //This ensures the game does not get covered by the bottom panel.
        super.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30));

        initGame();
        newGame();
    }

    public void initGame() {
        board = new Board();
    }

    public void newGame() {
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                board.cells[row][col].content = Seed.NO_SEED;
            }
        }
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(COLOR_BG);
        board.paint(g);

        if (currentState == State.PLAYING) {
            statusBar.setForeground(Color.BLACK);
            statusBar.setText((currentPlayer == Seed.CROSS) ? "Player 1's Turn" : "Player 2's Turn");
        } else if (currentState == State.DRAW) {
            statusBar.setForeground(Color.BLACK);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == State.CROSS_WON) {
            statusBar.setForeground(Color.BLACK);
            statusBar.setText("Player 1 Won! Click to play again.");
        } else if (currentState == State.NOUGHT_WON) {
            statusBar.setForeground(Color.BLACK);
            statusBar.setText("Player 2 Won! Click to play again.");
        }

        if (currentState == State.CROSS_WON || currentState == State.NOUGHT_WON) {
            Point start = board.getWinStart();
            Point end = board.getWinEnd();
            if (start != null && end != null) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(6));

                int x1 = start.x * Cell.SIZE + Cell.SIZE / 2;
                int y1 = start.y * Cell.SIZE + Cell.SIZE / 2;
                int x2 = end.x * Cell.SIZE + Cell.SIZE / 2;
                int y2 = end.y * Cell.SIZE + Cell.SIZE / 2;

                g2.drawLine(x1, y1, x2, y2);
            }
        }
    }

    public static String getPassword(String username) throws ClassNotFoundException {
        String user_password = "";
        String host = "mysql-buatfp1-anandamutiara2506-1e2c.c.aivencloud.com";
        String port = "26799";
        String databaseName = "tictactoedb";
        String userName = "avnadmin";
        String password = "AVNS_CfZzLVtdgXNe1MCW1Gt";

        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?sslmode=require",
                userName, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT password from user where username = '" + username + "'")
        ) {
            while (resultSet.next()) {
                user_password = resultSet.getString("password");
            }
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
        return user_password;
    }

    public static void main(String[] args) {
        SoundEffect.BGM.loop(); // play background music

        // Show welcome screen first
        SwingUtilities.invokeLater(() -> new WelcomeScreen());
    }

    // Call out LoginScreen
    public static void launchGameBoard() {
        JFrame frame = new JFrame(TITLE);
        frame.setContentPane(new GameMain());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // center window
        frame.setVisible(true);
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score - Player 1: " + crossScore + " | Player 2: " + noughtScore);
    }
}
