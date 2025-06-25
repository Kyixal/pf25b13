import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Connect-4 (Tic-Tac-Toe 4-in-a-line) with simple OO and graphics
 */
public class TTTGraphics extends JFrame {
    private static final long serialVersionUID = 1L;

    public static final int ROWS = 6;
    public static final int COLS = 7;

    public static final int CELL_SIZE = 120;
    public static final int BOARD_WIDTH = CELL_SIZE * COLS;
    public static final int BOARD_HEIGHT = CELL_SIZE * ROWS;
    public static final int GRID_WIDTH = 10;
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;
    public static final int CELL_PADDING = CELL_SIZE / 5;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 8;

    public static final Color COLOR_BG = Color.WHITE;
    public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
    public static final Color COLOR_GRID = Color.LIGHT_GRAY;
    public static final Color COLOR_CROSS = new Color(211, 45, 65);
    public static final Color COLOR_NOUGHT = new Color(76, 181, 245);
    public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

    public enum State { PLAYING, DRAW, CROSS_WON, NOUGHT_WON }
    private State currentState;

    public enum Seed { CROSS, NOUGHT, NO_SEED }
    private Seed currentPlayer;
    private Seed[][] board;

    private GamePanel gamePanel;
    private JLabel statusBar;

    public TTTGraphics() {
        initGame();

        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int col = mouseX / CELL_SIZE;

                if (currentState == State.PLAYING) {
                    if (col >= 0 && col < COLS) {
                        for (int row = ROWS - 1; row >= 0; row--) {
                            if (board[row][col] == Seed.NO_SEED) {
                                board[row][col] = currentPlayer;
                                currentState = stepGame(currentPlayer, row, col);
                                if (currentState == State.PLAYING) {
                                    currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                                }
                                break;
                            }
                        }
                    }
                } else {
                    newGame();
                }
                repaint();
            }
        });

        statusBar = new JLabel("       ");
        statusBar.setFont(FONT_STATUS);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));
        statusBar.setOpaque(true);
        statusBar.setBackground(COLOR_BG_STATUS);

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(gamePanel, BorderLayout.CENTER);
        cp.add(statusBar, BorderLayout.PAGE_END);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setTitle("Connect-4");
        setVisible(true);

        newGame();
    }

    public void initGame() {
        board = new Seed[ROWS][COLS];
    }

    public void newGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board[row][col] = Seed.NO_SEED;
            }
        }
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
    }

    public State stepGame(Seed player, int selectedRow, int selectedCol) {
        if (hasWon(player, selectedRow, selectedCol)) {
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        }

        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (board[row][col] == Seed.NO_SEED) {
                    return State.PLAYING;
                }
            }
        }

        return State.DRAW;
    }

    public boolean hasWon(Seed theSeed, int rowSelected, int colSelected) {
        int count;

        // Check row
        count = 0;
        for (int col = 0; col < COLS; ++col) {
            if (board[rowSelected][col] == theSeed) {
                count++;
                if (count == 4) return true;
            } else {
                count = 0;
            }
        }

        // Check column
        count = 0;
        for (int row = 0; row < ROWS; ++row) {
            if (board[row][colSelected] == theSeed) {
                count++;
                if (count == 4) return true;
            } else {
                count = 0;
            }
        }

        // Check main diagonal
        int startRow = rowSelected;
        int startCol = colSelected;
        while (startRow > 0 && startCol > 0) {
            startRow--;
            startCol--;
        }
        count = 0;
        while (startRow < ROWS && startCol < COLS) {
            if (board[startRow][startCol] == theSeed) {
                count++;
                if (count == 4) return true;
            } else {
                count = 0;
            }
            startRow++;
            startCol++;
        }

        // Check anti-diagonal
        startRow = rowSelected;
        startCol = colSelected;
        while (startRow > 0 && startCol < COLS - 1) {
            startRow--;
            startCol++;
        }
        count = 0;
        while (startRow < ROWS && startCol >= 0) {
            if (board[startRow][startCol] == theSeed) {
                count++;
                if (count == 4) return true;
            } else {
                count = 0;
            }
            startRow++;
            startCol--;
        }

        return false;
    }

    class GamePanel extends JPanel {
        private static final long serialVersionUID = 1L;

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(COLOR_BG);

            g.setColor(COLOR_GRID);
            for (int row = 1; row < ROWS; ++row) {
                g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDTH_HALF, BOARD_WIDTH - 1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
            }
            for (int col = 1; col < COLS; ++col) {
                g.fillRoundRect(CELL_SIZE * col - GRID_WIDTH_HALF, 0, GRID_WIDTH, BOARD_HEIGHT - 1, GRID_WIDTH, GRID_WIDTH);
            }

            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    int x1 = col * CELL_SIZE + CELL_PADDING;
                    int y1 = row * CELL_SIZE + CELL_PADDING;
                    if (board[row][col] == Seed.CROSS) {
                        g2d.setColor(COLOR_CROSS);
                        int x2 = (col + 1) * CELL_SIZE - CELL_PADDING;
                        int y2 = (row + 1) * CELL_SIZE - CELL_PADDING;
                        g2d.drawLine(x1, y1, x2, y2);
                        g2d.drawLine(x2, y1, x1, y2);
                    } else if (board[row][col] == Seed.NOUGHT) {
                        g2d.setColor(COLOR_NOUGHT);
                        g2d.drawOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
                    }
                }
            }

            if (currentState == State.PLAYING) {
                statusBar.setForeground(Color.BLACK);
                statusBar.setText((currentPlayer == Seed.CROSS) ? "X's Turn" : "O's Turn");
            } else if (currentState == State.DRAW) {
                statusBar.setForeground(Color.RED);
                statusBar.setText("It's a Draw! Click to play again");
            } else if (currentState == State.CROSS_WON) {
                statusBar.setForeground(Color.RED);
                statusBar.setText("'X' Won! Click to play again");
            } else if (currentState == State.NOUGHT_WON) {
                statusBar.setForeground(Color.RED);
                statusBar.setText("'O' Won! Click to play again");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TTTGraphics();
            }
        });
    }
}