import javax.swing.*;
import java.awt.*;

public class WelcomeScreen extends JFrame {
    public WelcomeScreen() {
        setTitle("Welcome");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Gambar sebagai background
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/assets/welcome.jpg")));
        background.setLayout(new BorderLayout());
        setContentPane(background);

        // Tambahkan label dan tombol ke atas background
        JLabel title = new JLabel("Welcome to Connect Four!", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 20));
        title.setForeground(Color.WHITE); // sesuaikan dengan warna background
        background.add(title, BorderLayout.NORTH);

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 16));
        startButton.addActionListener(e -> {
            dispose(); // tutup WelcomeScreen
            new LoginScreen(); // buka login screen
        });
        background.add(startButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}
