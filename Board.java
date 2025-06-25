import java.awt.*;
/**
 * The Board class models the ROWS-by-COLS game board.
 */


public class Board {
    // Define named constants
    public static final int ROWS = 6;  // ROWS x COLS cells (Connect Four)
    public static final int COLS = 7;
    // Define named constants for drawing
    public static final int CANVAS_WIDTH = Cell.SIZE * COLS;  // the drawing canvas
    public static final int CANVAS_HEIGHT = Cell.SIZE * ROWS;
    public static final Color COLOR_GRID = new Color(101, 92, 158);


    // Define properties (package-visible)
    /** Composes of 2D array of ROWS-by-COLS Cell instances */
    Cell[][] cells;

    private Point winStart = null;
    private Point winEnd = null;

    public Point getWinStart() { return winStart; }
    public Point getWinEnd() { return winEnd; }

    /** Constructor to initialize the game board */
    public Board() {
        initGame();
    }

    /** Initialize the game objects (run once) */
    public void initGame() {
        cells = new Cell[ROWS][COLS]; // allocate the array
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                // Allocate element of the array
                cells[row][col] = new Cell(row, col);
                // Cells are initialized in the constructor
            }
        }
    }

    /** Reset the game board, ready for new game */
    public void newGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].newGame(); // clear the cell content
            }
        }
    }

    /**
     *  The given player makes a move on (selectedRow, selectedCol).
     *  Update cells[selectedRow][selectedCol]. Compute and return the
     *  new game state (PLAYING, DRAW, CROSS_WON, NOUGHT_WON).
     */
    public State stepGame(Seed player, int selectedRow, int selectedCol) {
        // Update game board
        cells[selectedRow][selectedCol].content = player;

        // Compute and return the new game state
        if (hasWon(player, selectedRow, selectedCol)) {
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        } else {
            // Check for DRAW (all cells occupied) or PLAYING
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    if (cells[row][col].content == Seed.NO_SEED) {
                        return State.PLAYING; // still have empty cells
                    }
                }
            }
            return State.DRAW; // no empty cell, it's a draw
        }
    }

    /**
     * Check whether the player has 4 in a line at (rowSelected, colSelected)
     */
    public boolean hasWon(Seed player, int rowSelected, int colSelected) {
        int count;

        // Check row
        count = 0;
        for (int col = 0; col < COLS; ++col) {
            if (cells[rowSelected][col].content == player) {
                count++;
                if (count == 1) winStart = new Point(col, rowSelected);
                if (count == 4) {
                    winEnd = new Point(col, rowSelected);
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // Check column
        count = 0;
        for (int row = 0; row < ROWS; ++row) {
            if (cells[row][colSelected].content == player) {
                count++;
                if (count == 1) winStart = new Point(colSelected, row);
                if (count == 4) {
                    winEnd = new Point(colSelected, row);
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // Check diagonal (\)
        int startRow = rowSelected;
        int startCol = colSelected;
        while (startRow > 0 && startCol > 0) {
            startRow--;
            startCol--;
        }
        count = 0;
        while (startRow < ROWS && startCol < COLS) {
            if (cells[startRow][startCol].content == player) {
                count++;
                if (count == 1) winStart = new Point(startCol, startRow);
                if (count == 4) {
                    winEnd = new Point(startCol, startRow);
                    return true;
                }
            } else {
                count = 0;
            }
            startRow++;
            startCol++;
        }

        // Check anti-diagonal (/)
        startRow = rowSelected;
        startCol = colSelected;
        while (startRow > 0 && startCol < COLS - 1) {
            startRow--;
            startCol++;
        }
        count = 0;
        while (startRow < ROWS && startCol >= 0) {
            if (cells[startRow][startCol].content == player) {
                count++;
                if (count == 1) winStart = new Point(startCol, startRow);
                if (count == 4) {
                    winEnd = new Point(startCol, startRow);
                    return true;
                }
            } else {
                count = 0;
            }
            startRow++;
            startCol--;
        }

        return false;
    }

    /** Paint itself on the graphics canvas, given the Graphics context */
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Gambar background papan
        g2d.setColor(COLOR_GRID);
        g2d.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

        //Draw circle cells
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                int holePadding = 6; // tambahkan sedikit padding
                int holeX = col * Cell.SIZE+holePadding;
                int holeY = row * Cell.SIZE+holePadding;
                int holeSize = Cell.SIZE-2*holePadding;

                g2d.setColor(Color.WHITE);
                g2d.fillOval(holeX, holeY, holeSize, holeSize);
            }
        }

        // Draw all the cells
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].paint(g);  // ask the cell to paint itself
            }
        }
    }
}
