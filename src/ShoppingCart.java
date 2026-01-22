import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * ShoppingCart Window - With User Profile Integration
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
        chkExpressShipping.addActionListener(this);

        // DISPLAY CART ITEMS
        displayCartItems();

        super.setVisible(true);
    }


    private void displayCartItems() {
        if (cartItems.isEmpty()) {
            txtProductList.setText("🛒 Your cart is empty!\n\nGo to Product Catalog to add items.");
            chkExpressShipping.setEnabled(false);
        } else {
            chkExpressShipping.setEnabled(true);
            
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

            // AUTO FREE SHIPPING if subtotal >= RM100
            if (subtotal >= 100.0) {
                chkExpressShipping.setEnabled(false);
                chkExpressShipping.setSelected(false);
                sb.append("\nFREE SHIPPING! (Orders above RM100)\n");
                sb.append(String.format("Shipping: RM0.00 (FREE)\n"));
                sb.append("═══════════════════════════════════════════════════\n");
                sb.append(String.format("TOTAL: RM%.2f\n", subtotal));
            } else {
                chkExpressShipping.setEnabled(true);
                
                if (chkExpressShipping.isSelected()) {
                    sb.append(String.format("Express Shipping: RM10.00\n"));
                    sb.append("═══════════════════════════════════════════════════\n");
                    sb.append(String.format("TOTAL: RM%.2f\n", subtotal + 10.0));
                } else {
                    sb.append(String.format("Standard Shipping: RM5.00\n"));
                    sb.append(String.format("\n💡 Tip: Add RM%.2f more for FREE shipping!\n", 100.0 - subtotal));
                    sb.append("═══════════════════════════════════════════════════\n");
                    sb.append(String.format("TOTAL: RM%.2f\n", subtotal + 5.0));
                }
            }

            txtProductList.setText(sb.toString());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == chkExpressShipping) {
            // Refresh display when checkbox changes
            displayCartItems();
            
        } else if (e.getSource() == btnCheckout) {
            // Check if cart is empty
            if (cartItems.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Your cart is empty!\nPlease add items before checkout.",
                        "Empty Cart",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // GET USER PROFILE
            String customerName = ProfileWindow.getUserName();
            String customerEmail = ProfileWindow.getUserEmail();
            String customerPhone = ProfileWindow.getUserPhone();
            String customerAddress = ProfileWindow.getUserAddress();
            
            // Check if profile is still default (not updated)
            if (customerName.equals("Sample User")) {
                int updateProfile = JOptionPane.showConfirmDialog(this,
                        "You haven't updated your profile yet.\n\n" +
                        "Would you like to update it now?\n" +
                        "(Required for delivery information)",
                        "Update Profile?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                
                if (updateProfile == JOptionPane.YES_OPTION) {
                    new ProfileWindow();
                    return;
                }
            }

            // Collect data
            String paymentMethod = rbCash.isSelected() ? "Cash on Delivery" : "Credit/Debit Card";
            boolean expressShipping = chkExpressShipping.isSelected();

            // Calculate total
            double subtotal = 0.0;
            for (int i = 0; i < cartItems.size(); i++) {
                subtotal += cartItems.get(i).calculateFinalPrice() * cartQuantities.get(i);
            }
            
            double shippingCost = 0.0;
            String shippingType = "";
            
            // FREE SHIPPING if subtotal >= RM100
            if (subtotal >= 100.0) {
                shippingCost = 0.0;
                shippingType = "FREE Shipping (Order above RM100)";
            } else if (expressShipping) {
                shippingCost = 10.0;
                shippingType = "Express Shipping";
            } else {
                shippingCost = 5.0;
                shippingType = "Standard Shipping";
            }
            
            double total = subtotal + shippingCost;

            // Build confirmation message WITH USER INFO
            String message = "═══════════════════════════════════\n";
            message += "      ORDER CONFIRMATION\n";
            message += "═══════════════════════════════════\n\n";
            message += "Customer Information:\n";
            message += "Name: " + customerName + "\n";
            message += "Email: " + customerEmail + "\n";
            message += "Phone: " + customerPhone + "\n";
            message += "Address: " + customerAddress + "\n\n";
            message += "───────────────────────────────────\n\n";
            message += String.format("Total Items: %d\n", cartItems.size());
            message += String.format("Subtotal: RM%.2f\n", subtotal);
            message += String.format("Shipping: RM%.2f (%s)\n\n", shippingCost, shippingType);
            message += String.format("TOTAL AMOUNT: RM%.2f\n\n", total);
            message += "Payment: " + paymentMethod + "\n\n";
            message += "Proceed with checkout?";

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    message,
                    "Confirm Order",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                // Save order to file WITH USER INFO
                String filename = "order_" + System.currentTimeMillis() + ".txt";

                try {
                    // Save order with user info
                    FileManager.saveOrderToFileWithProfile(
                            filename,
                            customerName,
                            customerEmail,
                            customerPhone,
                            customerAddress,
                            cartItems.toArray(new Product[0]),
                            cartQuantities.stream().mapToInt(i -> i).toArray(),
                            expressShipping,
                            paymentMethod,
                            shippingCost
                    );

                    String successMsg = "✅ Order placed successfully!\n\n";
                    successMsg += "Confirmation sent to: " + customerEmail + "\n\n";
                    successMsg += "Order saved to: " + filename + "\n";
                    successMsg += "Subtotal: RM" + String.format("%.2f", subtotal) + "\n";
                    successMsg += "Shipping: RM" + String.format("%.2f", shippingCost);
                    if (shippingCost == 0.0) {
                        successMsg += " (FREE!)";
                    }
                    successMsg += "\nTotal: RM" + String.format("%.2f", total) + "\n\n";
                    successMsg += "Delivery to:\n" + customerAddress + "\n\n";
                    successMsg += "Thank you for shopping with iRis Boutiques!";
                    
                    JOptionPane.showMessageDialog(this,
                            successMsg,
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