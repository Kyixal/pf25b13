import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginScreen extends JFrame {
    public LoginScreen() {
        setTitle("Login to Connect Four");
        setSize(420, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ====== Background Image ======
        ImageIcon bgIcon = new ImageIcon("src/assets/login.jpg");
        Image scaledImage = bgIcon.getImage().getScaledInstance(420, 650, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(scaledImage));
        backgroundLabel.setLayout(new BorderLayout());
        setContentPane(backgroundLabel);

        // ====== Panel form transparan di bawah ======
        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));

        JLabel title = new JLabel("Welcome Back!", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(new Color(55, 71, 79));

        JTextField usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(300, 35));
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        usernameField.setBackground(new Color(255, 253, 231));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(300, 35));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        passwordField.setBackground(new Color(255, 253, 231));

        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(new Color(0, 191, 165));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setMaximumSize(new Dimension(150, 35));

        formPanel.add(Box.createVerticalGlue());
        formPanel.add(title);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(usernameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(passwordField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(loginButton);
        formPanel.add(Box.createVerticalGlue());

        backgroundLabel.add(formPanel, BorderLayout.SOUTH);

        // ====== Aksi Login ======
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.trim().isEmpty() || password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username dan Password tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String truePassword = GameMain.getPassword(username);
                if (password.equals(truePassword)) {
                    dispose(); // Tutup login
                    SwingUtilities.invokeLater(() -> new ModeSelectionScreen());
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