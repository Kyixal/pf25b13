import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
/**
 * This enum is used by:
 * 1. Player: takes value of CROSS or NOUGHT
 * 2. Cell content: takes value of CROSS, NOUGHT, or NO_SEED.
 *
 * We also attach a display image icon (text or image) for the items.
 *   and define the related variable/constructor/getter.
 * To draw the image:
 *   g.drawImage(content.getImage(), x, y, width, height, null);
 *
 * Ideally, we should define two enums with inheritance, which is,
 *  however, not supported.
 */
public enum Seed {   // to save as "Seed.java"
    CROSS, NOUGHT, NO_SEED
}
