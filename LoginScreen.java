import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginScreen extends JFrame {
    public LoginScreen() {
        setTitle("Login");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            try {
                if (password.equals(GameMain.getPassword(username))) {
                    dispose();
                    new TTTGraphics(); // masuk ke papan Connect-4 GUI
                } else {
                    JOptionPane.showMessageDialog(this, "Wrong password!");
                }
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error.");
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(userLabel); panel.add(usernameField);
        panel.add(passLabel); panel.add(passwordField);
        panel.add(new JLabel("")); panel.add(loginButton);

        add(panel);
        setVisible(true);
    }
}
