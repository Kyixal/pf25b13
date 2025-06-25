import javax.swing.*;
import java.awt.*;

public class WelcomeScreen extends JFrame {
    public WelcomeScreen() {
        setTitle("Welcome to Connect Four");
        setSize(420, 650);  // Ukuran frame lebih kecil
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        int width = getWidth();
        int height = getHeight();

        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/assets/welcome.jpg"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(scaledImage));
        background.setLayout(new BorderLayout());
        setContentPane(background);

        JLabel title = new JLabel("Welcome to Connect Four!", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        background.add(title, BorderLayout.NORTH);

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setBackground(new Color(0, 191, 165));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> {
            dispose();
            new LoginScreen();
        });
        background.add(startButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}