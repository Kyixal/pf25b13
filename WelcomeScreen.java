import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomeScreen extends JFrame {
    public WelcomeScreen() {
        setTitle("Welcome");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Welcome to Connect Four!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 14));
        startButton.addActionListener(e -> {
            dispose(); // tutup WelcomeScreen
            new LoginScreen(); // buka login
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        panel.add(startButton, BorderLayout.SOUTH);
        add(panel);

        setVisible(true);
    }
}
