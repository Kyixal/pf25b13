import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class GameMain extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final String TITLE = "Connect Four";
    public static final Color COLOR_BG = Color.WHITE;
    public static final Color COLOR_BG_STATUS = new Color(255, 254, 245);
    public static final Color COLOR_CROSS = new Color(130, 194, 147);
    public static final Color COLOR_NOUGHT = new Color(250, 214, 147);
    public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

    private final GameMode mode;
    private Board board;
    private State currentState;
    private Seed currentPlayer;
    private int crossScore = 0;
    private int noughtScore = 0;

    private JLabel statusBar;
    private JLabel scoreLabel;

    public GameMain(GameMode mode) {
        this.mode = mode;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int col = mouseX / Cell.SIZE;

                if (currentState == State.PLAYING) {
                    if (col >= 0 && col < Board.COLS) {
                        for (int row = Board.ROWS - 1; row >= 0; row--) {
                            if (board.cells[row][col].content == Seed.NO_SEED) {
                                currentState = board.stepGame(currentPlayer, row, col);

                                if (currentState == State.CROSS_WON) {
                                    crossScore++;
                                    updateScoreLabel();
                                    SoundEffect.DONE.play();
                                } else if (currentState == State.NOUGHT_WON) {
                                    noughtScore++;
                                    updateScoreLabel();
                                    SoundEffect.DONE.play();
                                }

                                SoundEffect.TOKEN.play();

                                currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;

                                repaint();
                                break;
                            }
                        }
                    }
                } else {
                    newGame();
                    repaint();
                }
            }
        });

        // Status bar
        statusBar = new JLabel();
        statusBar.setFont(FONT_STATUS);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setOpaque(true);
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

        // Score bar
        scoreLabel = new JLabel("Score - Player 1: 0 | Player 2: 0");
        scoreLabel.setFont(FONT_STATUS);
        scoreLabel.setBackground(COLOR_BG_STATUS);
        scoreLabel.setOpaque(true);
        scoreLabel.setHorizontalAlignment(JLabel.RIGHT);
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(statusBar, BorderLayout.WEST);
        bottomPanel.add(scoreLabel, BorderLayout.EAST);
        bottomPanel.setBackground(COLOR_BG_STATUS);
        bottomPanel.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, 30));

        setLayout(new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30));
        setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));

        initGame();
        newGame();
    }

    public void initGame() {
        board = new Board();
    }

    public void newGame() {
        board.newGame();
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
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score - Player 1: " + crossScore + " | Player 2: " + noughtScore);
    }

    public static String getPassword(String username) throws ClassNotFoundException {
        String user_password = "";
        try {
            String url = "jdbc:mysql://mysql-buatfp1-anandamutiara2506-1e2c.c.aivencloud.com:26799/tictactoedb?sslmode=require";
            String user = "avnadmin";
            String pass = "AVNS_CfZzLVtdgXNe1MCW1Gt";
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT password FROM user WHERE username = '" + username + "'");
            if (rs.next()) {
                user_password = rs.getString("password");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user_password;
    }

    public static void main(String[] args) {
        SoundEffect.BGM.loop();
        SwingUtilities.invokeLater(() -> new WelcomeScreen());
    }
}