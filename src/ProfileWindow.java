import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class ProfileWindow extends JFrame implements ActionListener {
    private JTextField txtName, txtEmail, txtPhone, txtAddress;
    private JButton btnSave;
    
    private static final String PROFILE_FILE = "user_profile.txt";
    
    // Static variables to store user profile (accessible by other classes)
    private static String userName = "Sample User";
    private static String userEmail = "user@example.com";
    private static String userPhone = "012-3456789";
    private static String userAddress = "Kuala Lumpur, Malaysia";
    
    public ProfileWindow() {
        super.setTitle("User Profile - iRis Boutiques");
        super.setSize(400, 350);
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Create panel with GridLayout
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Load existing profile
        loadProfile();
        
        // Add components
        panel.add(new JLabel("Name:"));
        txtName = new JTextField(userName);
        panel.add(txtName);
        
        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField(userEmail);
        panel.add(txtEmail);
        
        panel.add(new JLabel("Phone:"));
        txtPhone = new JTextField(userPhone);
        panel.add(txtPhone);
        
        panel.add(new JLabel("Address:"));
        txtAddress = new JTextField(userAddress);
        panel.add(txtAddress);
        
        panel.add(new JLabel(""));  // Empty cell
        
        btnSave = new JButton("Save Profile");
        btnSave.setBackground(new Color(129, 199, 132));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnSave.addActionListener(this);
        panel.add(btnSave);
        
        // Info label
        JLabel lblInfo = new JLabel("ℹ️ Profile will be used during checkout", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(Color.GRAY);
        panel.add(lblInfo);
        
        Container cp = super.getContentPane();
        cp.add(panel);
        
        super.setVisible(true);
    }
    
    /**
     * Load profile from file
     */
    private void loadProfile() {
        try {
            File file = new File(PROFILE_FILE);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                
                userName = reader.readLine();
                userEmail = reader.readLine();
                userPhone = reader.readLine();
                userAddress = reader.readLine();
                
                reader.close();
                System.out.println("✅ Profile loaded from file");
            }
        } catch (IOException e) {
            System.err.println("⚠️ Could not load profile: " + e.getMessage());
        }
    }
    
    /**
     * Save profile to file
     */
    private void saveProfile() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(PROFILE_FILE));
            
            writer.println(userName);
            writer.println(userEmail);
            writer.println(userPhone);
            writer.println(userAddress);
            
            writer.close();
            System.out.println("✅ Profile saved to file");
        } catch (IOException e) {
            System.err.println("❌ Could not save profile: " + e.getMessage());
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSave) {
            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();
            String address = txtAddress.getText().trim();
            
            // Validation
            if (name.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Name and Email are required!",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid email address!",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Update static variables
            userName = name;
            userEmail = email;
            userPhone = phone;
            userAddress = address;
            
            // Save to file
            saveProfile();
            
            // Success message
            String message = "✅ Profile Updated Successfully!\n\n";
            message += "Name: " + name + "\n";
            message += "Email: " + email + "\n";
            message += "Phone: " + phone + "\n";
            message += "Address: " + address + "\n\n";
            message += "This information will be used during checkout.";
            
            JOptionPane.showMessageDialog(this,
                    message,
                    "Profile Saved",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // ============= STATIC GETTERS (for use in other classes) =============
    
    public static String getUserName() {
        // Load from file if not loaded yet
        if (userName.equals("Sample User")) {
            loadProfileStatic();
        }
        return userName;
    }
    
    public static String getUserEmail() {
        if (userEmail.equals("user@example.com")) {
            loadProfileStatic();
        }
        return userEmail;
    }
    
    public static String getUserPhone() {
        if (userPhone.equals("012-3456789")) {
            loadProfileStatic();
        }
        return userPhone;
    }
    
    public static String getUserAddress() {
        if (userAddress.equals("Kuala Lumpur, Malaysia")) {
            loadProfileStatic();
        }
        return userAddress;
    }
    
    /**
     * Static method to load profile (called by other classes)
     */
    private static void loadProfileStatic() {
        try {
            File file = new File(PROFILE_FILE);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                
                userName = reader.readLine();
                userEmail = reader.readLine();
                userPhone = reader.readLine();
                userAddress = reader.readLine();
                
                reader.close();
            }
        } catch (IOException e) {
            System.err.println("⚠️ Could not load profile: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ProfileWindow();
        });
    }
}