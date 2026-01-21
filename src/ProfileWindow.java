import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ProfileWindow - User profile form
 * Can create .form later if needed
 */
public class ProfileWindow extends JFrame implements ActionListener {
    private JTextField txtName, txtEmail, txtPhone, txtAddress;
    private JButton btnSave;

    public ProfileWindow() {
        super.setTitle("User Profile - iRis Boutiques");
        super.setSize(400, 300);
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Create panel with GridLayout
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add components
        panel.add(new JLabel("Name:"));
        txtName = new JTextField("Sample User");
        panel.add(txtName);

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField("user@example.com");
        panel.add(txtEmail);

        panel.add(new JLabel("Phone:"));
        txtPhone = new JTextField("012-3456789");
        panel.add(txtPhone);

        panel.add(new JLabel("Address:"));
        txtAddress = new JTextField("Kuala Lumpur, Malaysia");
        panel.add(txtAddress);

        btnSave = new JButton("Save Profile");
        btnSave.addActionListener(this);
        panel.add(new JLabel());  // Empty cell
        panel.add(btnSave);

        Container cp = super.getContentPane();
        cp.add(panel);

        super.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSave) {
            String name = txtName.getText();
            String email = txtEmail.getText();

            if (name.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Name and Email are required!",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String message = "Profile Updated!\n\n";
            message += "Name: " + name + "\n";
            message += "Email: " + email + "\n";
            message += "Phone: " + txtPhone.getText() + "\n";
            message += "Address: " + txtAddress.getText();

            JOptionPane.showMessageDialog(this,
                    message,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ProfileWindow();
        });
    }
}