import java.awt.*;
import javax.swing.*;

/**
 * The Cell class models each individual cell of the game board.
 */
public class Cell {
    public static final int SIZE = 90;  // Ukuran cell
    public static final int PADDING = 5;

    public int row, col;
    public Seed content;

    // Gambar untuk CROSS dan NOUGHT
    private static final Image xImg = new ImageIcon("src/assets/x.png")
            .getImage().getScaledInstance(SIZE - 2 * PADDING - 10, SIZE - 2 * PADDING - 10, Image.SCALE_SMOOTH);
    private static final Image oImg = new ImageIcon("src/assets/o.png")
            .getImage().getScaledInstance(SIZE - 2 * PADDING - 10, SIZE - 2 * PADDING - 10, Image.SCALE_SMOOTH);

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.content = Seed.NO_SEED;
    }

    public void newGame() {
        content = Seed.NO_SEED;
    }

    public void paint(Graphics g) {
        int x = col * SIZE;
        int y = row * SIZE;

        // Lingkaran putih transparan sebagai lubang (sedikit padding agar mirip Connect Four asli)
        g.setColor(new Color(255, 255, 255, 190));
        g.fillOval(x + PADDING, y + PADDING, SIZE - 2 * PADDING, SIZE - 2 * PADDING);

        // Gambar simbol jika sudah diisi
        if (content == Seed.CROSS) {
            g.drawImage(xImg, x + PADDING + 5, y + PADDING + 5, null);
        } else if (content == Seed.NOUGHT) {
            g.drawImage(oImg, x + PADDING + 5, y + PADDING + 5, null);
        }
    }
}