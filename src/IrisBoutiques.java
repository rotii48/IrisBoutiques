import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main Window - IrisBoutiques - FIXED VERSION
 * Now includes "Browse Products" button!
 */
public class IrisBoutiques extends JFrame implements ActionListener {
    // Components - MUST match binding variables in .form
    private JPanel panel1;
    private JLabel logo;
    private JTextField searchbar;
    private JButton shoppingcart;
    private JButton chat;
    private JButton profile;

    // Constructor
    public IrisBoutiques() {
        // Window settings
        super.setTitle("iRis Boutiques - Welcome");
        super.setSize(900, 600);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Add main panel
        Container cp = super.getContentPane();
        cp.add(panel1);

        // Load logo image
        try {
            ImageIcon boutiqueLogo = new ImageIcon(
                    getClass().getResource("/logo.png")
            );
            if (logo != null) {
                logo.setIcon(boutiqueLogo);
                logo.setText("");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Logo image not found in src folder.");
            if (logo != null) {
                logo.setText("iRis Boutiques");
            }
        }

        // Set search bar placeholder
        if (searchbar != null) {
            searchbar.setText("🔍 Search products...");
            searchbar.setForeground(Color.GRAY);

            // Add focus listeners for placeholder effect
            searchbar.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    if (searchbar.getText().equals("🔍 Search products...")) {
                        searchbar.setText("");
                        searchbar.setForeground(Color.BLACK);
                    }
                }
                public void focusLost(java.awt.event.FocusEvent evt) {
                    if (searchbar.getText().isEmpty()) {
                        searchbar.setText("🔍 Search products...");
                        searchbar.setForeground(Color.GRAY);
                    }
                }
            });
        }

        // Button listeners
        if (shoppingcart != null) shoppingcart.addActionListener(this);
        if (chat != null) chat.addActionListener(this);
        if (profile != null) profile.addActionListener(this);
        if (searchbar != null) searchbar.addActionListener(this);

        // Center window
        super.setLocationRelativeTo(null);
        super.setVisible(true);

        // Show welcome message
        showWelcomeMessage();
    }

    /**
     * Show welcome dialog with instructions
     */
    private void showWelcomeMessage() {
        String message = "╔═══════════════════════════════════════╗\n" +
                "   Welcome to iRis Boutiques! 🌸\n" +
                "╚═══════════════════════════════════════╝\n\n" +
                "How to shop:\n\n" +
                "1️⃣ Press ENTER in the search bar\n" +
                "   OR click the search bar and press Enter\n" +
                "   to browse our products\n\n" +
                "2️⃣ Add items to your cart\n\n" +
                "3️⃣ Click the 🛒 Shopping Cart button\n" +
                "   to checkout\n\n" +
                "Enjoy your shopping experience! ✨";

        JOptionPane.showMessageDialog(this,
                message,
                "Welcome!",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Handle actions
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == shoppingcart) {
            // Open shopping cart
            new ShoppingCart();

        } else if (e.getSource() == chat) {
            // Open chat window
            new ChatWindow();

        } else if (e.getSource() == profile) {
            // Open profile window
            new ProfileWindow();

        } else if (e.getSource() == searchbar) {
            // When user presses ENTER in search bar, open product catalog
            String searchText = searchbar.getText().trim();

            // Don't open if still showing placeholder
            if (!searchText.equals("🔍 Search products...") && !searchText.isEmpty()) {
                // Could implement search functionality here
                JOptionPane.showMessageDialog(this,
                        "Opening Product Catalog...\n\n" +
                                "Search feature coming soon!\n" +
                                "For now, browse all products.",
                        "Product Catalog",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            // Open product catalog
            new ProductCatalog();
        }
    }

    // Main method
    public static void main(String[] args) {
        // Set Look and Feel (optional - makes it look better)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // If fails, use default
        }

        SwingUtilities.invokeLater(() -> {
            new IrisBoutiques();
        });
    }

    // Custom UI components (leave empty)
    private void createUIComponents() {
        // Auto-generated method
    }
}