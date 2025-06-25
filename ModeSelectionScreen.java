import javax.swing.*;
import java.awt.*;

public class ModeSelectionScreen extends JFrame {
    public ModeSelectionScreen() {
        setTitle("Select Game Mode");
        setSize(420, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Background Image
        ImageIcon bgIcon = new ImageIcon("src/assets/mode selection.jpg");
        Image scaledImg = bgIcon.getImage().getScaledInstance(420, 650, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(scaledImg));
        background.setLayout(new GridBagLayout());

        // Panel Tombol
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(2, 1, 0, 20));
        buttonPanel.setPreferredSize(new Dimension(200, 120));

        JButton multiplayerButton = new JButton("Multiplayer");

        // Styling
        Font font = new Font("Arial", Font.BOLD, 18);

        multiplayerButton.setFont(font);
        multiplayerButton.setBackground(new Color(101, 92, 158));
        multiplayerButton.setForeground(Color.WHITE);
        multiplayerButton.setFocusPainted(false);

        buttonPanel.add(multiplayerButton);
        background.add(buttonPanel);
        setContentPane(background);

        multiplayerButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                GameMain gamePanel = new GameMain(GameMode.MULTIPLAYER);
                JFrame frame = new JFrame(GameMain.TITLE);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(gamePanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });
        });

        setVisible(true);

    }

}