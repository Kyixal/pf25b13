import javax.swing.*;
import java.awt.*;

public class WelcomeScreen extends JFrame {
    public WelcomeScreen() {
        setTitle("Welcome to Connect-4");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Welcome to Connect-4", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.PLAIN, 16));
        startButton.addActionListener(e -> {
            dispose();
            new LoginScreen();
        });

        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);
        setVisible(true);
    }
}
