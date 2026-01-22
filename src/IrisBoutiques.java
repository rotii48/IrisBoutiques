import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class IrisBoutiques extends JFrame implements ActionListener {
    // Components from .form
    private JPanel panel1;
    private JLabel logo;
    private JTextField searchbar;
    private JButton shoppingcart;
    private JButton chat;
    private JButton profile;
    
    private JPanel contentPanel;

    public IrisBoutiques() {
        super.setTitle("iRis Boutiques - Welcome");
        super.setSize(900, 600);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Add main panel
        Container cp = super.getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(panel1, BorderLayout.NORTH);  // Header at top
        
        // ADD WELCOME CONTENT PANEL
        contentPanel = createWelcomeContent();
        cp.add(contentPanel, BorderLayout.CENTER);

        // Load logo
        try {
            ImageIcon boutiqueLogo = new ImageIcon(getClass().getResource("/logo.png"));
            if (logo != null) {
                logo.setIcon(boutiqueLogo);
                logo.setText("");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Logo image not found");
            if (logo != null) {
                logo.setText("iRis Boutiques");
            }
        }

        // Search bar placeholder
        if (searchbar != null) {
            searchbar.setText("🔍 Search products...");
            searchbar.setForeground(Color.GRAY);
            
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

        super.setLocationRelativeTo(null);
        super.setVisible(true);
        
        // SHOW WELCOME DIALOG WITH EXIT OPTION
        showWelcomeDialog();
    }



    private void showWelcomeDialog() {
        // Custom dialog panel
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Welcome message
        JLabel lblWelcome = new JLabel("Welcome to iRis Boutiques!");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Instructions
        JLabel lblInstructions = new JLabel("<html><br><b>How to shop:</b><br><br>" +
                "1️⃣ Click the search bar and browse our products<br><br>" +
                "2️⃣ Add items to your cart<br><br>" +
                "3️⃣ Click the Shopping Cart button<br>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;to checkout<br><br>" +
                "Enjoy your shopping experience!</html>");
        lblInstructions.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblInstructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        dialogPanel.add(lblWelcome);
        dialogPanel.add(lblInstructions);
        
        // Custom button options
        Object[] options = {"OK - Let's Shop!", "Exit"};
        
        int choice = JOptionPane.showOptionDialog(
            this,
            dialogPanel,
            "Welcome!",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        // Handle user choice
        if (choice == 1 || choice == JOptionPane.CLOSED_OPTION) {
            // User clicked Exit or closed dialog
            int confirmExit = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit?\n\nYou'll miss our amazing products!",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (confirmExit == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else {
                // User changed mind, show welcome dialog again
                showWelcomeDialog();
            }
        }
    }


    private JPanel createWelcomeContent() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Try to load and draw background image
                try {
                    ImageIcon bgImage = new ImageIcon(getClass().getResource("/background.png"));
                    g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    // If image not found, use default cream color
                    setBackground(new Color(255, 250, 240));
                }
            }
        };
        
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBackground(new Color(255, 250, 240)); // Cream color fallback
        panel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // === TOP: WELCOME MESSAGE ===
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);
        
        JLabel welcomeTitle = new JLabel("Welcome to iRis Boutiques!");
        welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        welcomeTitle.setForeground(new Color(218, 112, 214));
        welcomeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitle = new JLabel("Your Fashion Destination");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitle.setForeground(new Color(105, 105, 105));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        topPanel.add(welcomeTitle);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(subtitle);
        topPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // === CENTER: FEATURES GRID ===
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 30, 30));
        centerPanel.setOpaque(false);
        
        // Feature 1: New Arrivals
        JPanel feature1 = createFeatureCard(
            "New Arrivals",
            "Discover our latest",
            "clothing collection",
            new Color(255, 182, 193),
            "new_arrivals"
        );
        
        // Feature 2: Best Sellers
        JPanel feature2 = createFeatureCard(
            "Best Sellers",
            "Shop our most",
            "popular items",
            new Color(255, 218, 185),
            "best_sellers"
        );
        
        // Feature 3: Special Offers
        JPanel feature3 = createFeatureCard(
            "Special Offers",
            "Up to 25% off",
            "selected items",
            new Color(221, 160, 221),
            "special_offers"
        );
        
        // Feature 4: Free Shipping
        JPanel feature4 = createFeatureCard(
            "Free Shipping",
            "On orders above",
            "RM100",
            new Color(176, 224, 230),
            "free_shipping"
        );
        
        centerPanel.add(feature1);
        centerPanel.add(feature2);
        centerPanel.add(feature3);
        centerPanel.add(feature4);

        // === BOTTOM: CALL TO ACTION ===
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        bottomPanel.setOpaque(false);
        
        JButton btnBrowse = new JButton("Browse All Products");
        btnBrowse.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnBrowse.setBackground(new Color(255, 105, 180));
        btnBrowse.setForeground(Color.WHITE);
        btnBrowse.setPreferredSize(new Dimension(250, 50));
        btnBrowse.setFocusPainted(false);
        btnBrowse.setBorderPainted(false);
        btnBrowse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBrowse.addActionListener(e -> new ProductCatalog());
        
        JLabel infoLabel = new JLabel("Or use the search bar above to find specific items");
        infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        infoLabel.setForeground(new Color(105, 105, 105));
        
        bottomPanel.add(btnBrowse);
        bottomPanel.add(infoLabel);

        // === ASSEMBLE EVERYTHING ===
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }


    private JPanel createFeatureCard(String title, String line1, String line2, Color bgColor, String filterType) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblLine1 = new JLabel(line1);
        lblLine1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblLine1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblLine2 = new JLabel(line2);
        lblLine2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblLine2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(lblTitle);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(lblLine1);
        card.add(lblLine2);
        
        // Make card clickable with filter
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(bgColor);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (filterType.equals("free_shipping")) {
                    JOptionPane.showMessageDialog(IrisBoutiques.this,
                        "Free Shipping Feature!\n\n" +
                        "Add items worth RM100 or more to your cart\n" +
                        "and enjoy FREE delivery!\n\n" +
                        "The shipping fee will be automatically waived\n" +
                        "when you checkout with RM100+ in your cart.",
                        "Free Shipping Info",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    new ProductCatalog(filterType);
                }
            }
        });
        
        return card;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == shoppingcart) {
            new ShoppingCart();
            
        } else if (e.getSource() == chat) {
            new ChatWindow();
            
        } else if (e.getSource() == profile) {
            new ProfileWindow();
            
        } else if (e.getSource() == searchbar) {
            // FIX: Check if user actually typed something
            String searchText = searchbar.getText().trim();
            
            // Ignore if still placeholder or empty
            if (searchText.equals("🔍 Search products...") || searchText.isEmpty()) {
                // Just open product catalog when clicking search bar
                new ProductCatalog();
                return;
            }
            
            // Search for product (case-insensitive, checks both name and category)
            Product[] allProducts = FileManager.loadAllProducts();
            Product[] matchingProducts = filterProductsBySearch(allProducts, searchText);
            
            if (matchingProducts.length > 0) {
                // Product exists, open catalog with filter
                JOptionPane.showMessageDialog(this,
                    "Found " + matchingProducts.length + " product(s) matching: \"" + searchText + "\"\n\n" +
                    "Opening Product Catalog...",
                    "Search Results",
                    JOptionPane.INFORMATION_MESSAGE);
                new ProductCatalog(searchText);
            } else {
                // Product not found
                JOptionPane.showMessageDialog(this,
                    "Sorry, no products found for: \"" + searchText + "\"\n\n" +
                    "Available products:\n" +
                    "• Blouse, T-Shirt (Clothing)\n" +
                    "• Jeans, Palazzo (Pants)\n" +
                    "• Heels, Sneakers (Shoes)\n\n" +
                    "Try searching with these keywords.",
                    "Product Not Found",
                    JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    // Helper method to filter products by search term
    private Product[] filterProductsBySearch(Product[] allProducts, String searchText) {
        String searchLower = searchText.toLowerCase();
        int count = 0;
        
        // Count matches
        for (Product p : allProducts) {
            if (p != null) {
                String nameLower = p.getName().toLowerCase();
                String categoryLower = p.getCategory().toLowerCase();
                
                if (nameLower.contains(searchLower) || categoryLower.contains(searchLower)) {
                    count++;
                }
            }
        }
        
        // Create filtered array
        Product[] filtered = new Product[count];
        int index = 0;
        for (Product p : allProducts) {
            if (p != null) {
                String nameLower = p.getName().toLowerCase();
                String categoryLower = p.getCategory().toLowerCase();
                
                if (nameLower.contains(searchLower) || categoryLower.contains(searchLower)) {
                    filtered[index++] = p;
                }
            }
        }
        
        return filtered;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Use default if fails
        }

        SwingUtilities.invokeLater(() -> {
            new IrisBoutiques();
        });
    }

    private void createUIComponents() {
        // Auto-generated
    }
}