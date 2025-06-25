import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {
    public LoginScreen() {
        setTitle("Login to Connect Four");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel utama, pakai BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Gambar di sisi kiri
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon("src/assets/login.jpg")); // <-- Ganti dengan path gambarmu
        imageLabel.setPreferredSize(new Dimension(250, 0));

        // Panel form di sisi kanan
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(255, 228, 21));
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

        JLabel title = new JLabel("Welcome Back!");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(new Color(50, 50, 100));

        JTextField usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(300, 35));
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(300, 35));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));

        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(new Color(130, 194, 147));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setMaximumSize(new Dimension(150, 35));

        formPanel.add(title);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        formPanel.add(usernameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(passwordField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        formPanel.add(loginButton);

        // Tambahkan panel ke frame
        mainPanel.add(imageLabel, BorderLayout.WEST);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Aksi tombol login
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            try {
                String truePassword = GameMain.getPassword(username);
                if (password.equals(truePassword)) {
                    dispose(); // Tutup login
                    SwingUtilities.invokeLater(() -> {
                        JFrame frame = new JFrame(GameMain.TITLE);
                        frame.setContentPane(new GameMain());
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    });
                } else {
                    JOptionPane.showMessageDialog(this, "Password salah!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Login gagal!", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        setVisible(true);
    }
}
