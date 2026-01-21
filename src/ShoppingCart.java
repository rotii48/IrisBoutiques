import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * ShoppingCart Window - FIXED VERSION
 * Now works with actual product data!
 */
public class ShoppingCart extends JFrame implements ActionListener {
    // STATIC storage for cart items (shared across all windows)
    private static ArrayList<Product> cartItems = new ArrayList<>();
    private static ArrayList<Integer> cartQuantities = new ArrayList<>();

    // Components - MUST match binding variables in ShoppingCart.form
    private JPanel pnlMain;
    private JLabel lblTitle;
    private JLabel lblProducts;
    private JTextArea txtProductList;
    private JCheckBox chkExpressShipping;
    private JRadioButton rbCash;
    private JRadioButton rbCard;
    private JButton btnCheckout;

    // Constructor
    public ShoppingCart() {
        super.setTitle("Shopping Cart - iRis Boutiques");
        super.setSize(550, 500);
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Container cp = super.getContentPane();
        cp.add(pnlMain);

        // Group radio buttons
        ButtonGroup bgPayment = new ButtonGroup();
        bgPayment.add(rbCash);
        bgPayment.add(rbCard);
        rbCash.setSelected(true);

        // Add action listeners
        btnCheckout.addActionListener(this);

        // DISPLAY CART ITEMS
        displayCartItems();

        super.setVisible(true);
    }

    /**
     * Display all items in cart
     */
    private void displayCartItems() {
        if (cartItems.isEmpty()) {
            txtProductList.setText("🛒 Your cart is empty!\n\nGo to Product Catalog to add items.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("╔════════════════════════════════════════════════════╗\n");
            sb.append("  Product                    Qty    Price    Total   \n");
            sb.append("╠════════════════════════════════════════════════════╣\n");

            double subtotal = 0.0;

            for (int i = 0; i < cartItems.size(); i++) {
                Product p = cartItems.get(i);
                int qty = cartQuantities.get(i);
                double itemPrice = p.calculateFinalPrice();
                double itemTotal = itemPrice * qty;

                sb.append(String.format("  %-26s %3d    RM%-6.2f  RM%-8.2f\n",
                        p.getName(), qty, itemPrice, itemTotal));

                subtotal += itemTotal;
            }

            sb.append("╚════════════════════════════════════════════════════╝\n");
            sb.append(String.format("\nSubtotal: RM%.2f\n", subtotal));

            if (chkExpressShipping.isSelected()) {
                sb.append(String.format("Express Shipping: RM10.00\n"));
                sb.append(String.format("TOTAL: RM%.2f\n", subtotal + 10.0));
            } else {
                sb.append(String.format("TOTAL: RM%.2f\n", subtotal));
            }

            txtProductList.setText(sb.toString());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCheckout) {
            // Check if cart is empty
            if (cartItems.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Your cart is empty!\nPlease add items before checkout.",
                        "Empty Cart",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Collect data
            String paymentMethod = rbCash.isSelected() ? "Cash on Delivery" : "Credit/Debit Card";
            boolean expressShipping = chkExpressShipping.isSelected();

            // Calculate total
            double total = 0.0;
            for (int i = 0; i < cartItems.size(); i++) {
                total += cartItems.get(i).calculateFinalPrice() * cartQuantities.get(i);
            }
            if (expressShipping) {
                total += 10.0;
            }

            // Build confirmation message
            String message = "═══════════════════════════════════\n";
            message += "      ORDER CONFIRMATION\n";
            message += "═══════════════════════════════════\n\n";
            message += String.format("Total Items: %d\n", cartItems.size());
            message += String.format("Total Amount: RM%.2f\n\n", total);
            message += "Payment: " + paymentMethod + "\n";
            message += "Shipping: " + (expressShipping ? "Express (+RM10)" : "Standard") + "\n\n";
            message += "Proceed with checkout?";

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    message,
                    "Confirm Order",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                // Save order to file
                String filename = "order_" + System.currentTimeMillis() + ".txt";

                try {
                    FileManager.saveOrderToFile(
                            filename,
                            "Customer",  // You can add name input later
                            cartItems.toArray(new Product[0]),
                            cartQuantities.stream().mapToInt(i -> i).toArray(),
                            expressShipping,
                            paymentMethod
                    );

                    JOptionPane.showMessageDialog(this,
                            "✅ Order placed successfully!\n\n" +
                                    "Order saved to: " + filename + "\n" +
                                    "Total: RM" + String.format("%.2f", total) + "\n\n" +
                                    "Thank you for shopping with iRis Boutiques!",
                            "Order Success",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Clear cart after successful order
                    clearCart();
                    dispose();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Error saving order: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    // ============= STATIC METHODS FOR CART MANAGEMENT =============

    /**
     * Add item to cart
     */
    public static void addToCart(Product product, int quantity) {
        // Check if product already in cart
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getProductId().equals(product.getProductId())) {
                // Update quantity
                cartQuantities.set(i, cartQuantities.get(i) + quantity);
                return;
            }
        }
        // Add new item
        cartItems.add(product);
        cartQuantities.add(quantity);
    }

    /**
     * Get cart size
     */
    public static int getCartSize() {
        return cartItems.size();
    }

    /**
     * Clear all items from cart
     */
    public static void clearCart() {
        cartItems.clear();
        cartQuantities.clear();
    }

    /**
     * Get cart items (for display)
     */
    public static ArrayList<Product> getCartItems() {
        return cartItems;
    }

    // Test method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ShoppingCart();
        });
    }
}