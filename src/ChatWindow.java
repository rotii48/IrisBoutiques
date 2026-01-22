import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ChatWindow extends JFrame implements ActionListener {
    private JTextArea txtChat;
    private JTextField txtMessage;
    private JButton btnSend;
    
    public ChatWindow() {
        super.setTitle("Customer Chat - iRis Boutiques");
        super.setSize(400, 400);
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Create components
        txtChat = new JTextArea();
        txtChat.setEditable(false);
        txtChat.setLineWrap(true);
        txtChat.setWrapStyleWord(true);
        txtChat.setText("Welcome to iRis Boutiques Customer Support! 🌸\nHow can we help you today?\n\n");
        
        txtMessage = new JTextField();
        btnSend = new JButton("Send");
        
        // Layout
        JScrollPane scrollPane = new JScrollPane(txtChat);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(txtMessage, BorderLayout.CENTER);
        bottomPanel.add(btnSend, BorderLayout.EAST);
        
        Container cp = super.getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(scrollPane, BorderLayout.CENTER);
        cp.add(bottomPanel, BorderLayout.SOUTH);
        
        // Add listeners
        btnSend.addActionListener(this);
        txtMessage.addActionListener(this);  // Enter key also sends
        
        super.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String message = txtMessage.getText().trim();
        if (!message.isEmpty()) {
            txtChat.append("You: " + message + "\n");
            txtMessage.setText("");
            
            // Generate intelligent response
            String response = generateResponse(message);
            txtChat.append("Support: " + response + "\n\n");
            txtChat.setCaretPosition(txtChat.getDocument().getLength());
        }
    }
    
    /**
     * Generate intelligent response based on user message
     */
    private String generateResponse(String message) {
        String msgLower = message.toLowerCase();
        
        // Product inquiries
        if (msgLower.contains("product") || msgLower.contains("item") || 
            msgLower.contains("clothing") || msgLower.contains("pants") || 
            msgLower.contains("shoes") || msgLower.contains("blouse") ||
            msgLower.contains("jeans") || msgLower.contains("heels")) {
            return "We have a wide range of products!\n" +
                   "• Clothing: Blouses, T-Shirts\n" +
                   "• Pants: Jeans, Palazzo\n" +
                   "• Shoes: Heels, Sneakers\n\n" +
                   "Click the search bar or 'Browse All Products' to see our full catalog!";
        }
        
        // Price inquiries
        if (msgLower.contains("price") || msgLower.contains("cost") || 
            msgLower.contains("how much") || msgLower.contains("berapa")) {
            return "Our prices range from:\n" +
                   "• Clothing: RM49.90 - RM69.90 (with 10% discount)\n" +
                   "• Pants: RM89.90 - RM99.90 (with 15% discount)\n" +
                   "• Shoes: RM129.90 - RM149.90 (includes 6% tax)\n\n" +
                   "Visit our Product Catalog for exact prices!";
        }
        
        // Shipping inquiries
        if (msgLower.contains("ship") || msgLower.contains("delivery") || 
            msgLower.contains("postage") || msgLower.contains("hantar")) {
            return "Shipping Information:\n" +
                   "• Standard Shipping: RM5.00\n" +
                   "• Express Shipping: RM10.00\n" +
                   "• FREE SHIPPING on orders above RM100! 🎉\n\n" +
                   "We deliver nationwide within 3-7 business days.";
        }
        
        // Payment inquiries
        if (msgLower.contains("pay") || msgLower.contains("payment") || 
            msgLower.contains("cash") || msgLower.contains("card") || 
            msgLower.contains("bayar")) {
            return "We accept:\n" +
                   "• Cash on Delivery (COD)\n" +
                   "• Credit/Debit Card\n\n" +
                   "All transactions are secure and encrypted!";
        }
        
        // Discount/Promotion inquiries
        if (msgLower.contains("discount") || msgLower.contains("promo") || 
            msgLower.contains("sale") || msgLower.contains("offer") ||
            msgLower.contains("diskaun")) {
            return "Current Promotions:\n" +
                   "• Clothing: 10% OFF\n" +
                   "• Pants: 15% OFF\n" +
                   "• FREE Shipping for orders above RM100\n\n" +
                   "Check 'Special Offers' section for more deals!";
        }
        
        // Size inquiries
        if (msgLower.contains("size") || msgLower.contains("saiz")) {
            return "Available Sizes:\n" +
                   "• Clothing: S, M, L\n" +
                   "• Pants: S, M, L\n" +
                   "• Shoes: 38, 39, 40\n\n" +
                   "All our products have size guides in Product Details!";
        }
        
        // Stock inquiries
        if (msgLower.contains("stock") || msgLower.contains("available") || 
            msgLower.contains("ada") || msgLower.contains("stok")) {
            return "✅ All products are in stock!\n" +
                   "Current stock levels are displayed in the Product Catalog.\n\n" +
                   "Add to cart now before they sell out!";
        }
        
        // Return/Exchange inquiries
        if (msgLower.contains("return") || msgLower.contains("exchange") || 
            msgLower.contains("refund") || msgLower.contains("tukar")) {
            return "🔄 Return & Exchange Policy:\n" +
                   "• 7-day return/exchange guarantee\n" +
                   "• Items must be unused with tags attached\n" +
                   "• Free returns for defective items\n\n" +
                   "Contact us at support@irisboutiques.com for assistance.";
        }
        
        // Contact inquiries
        if (msgLower.contains("contact") || msgLower.contains("email") || 
            msgLower.contains("phone") || msgLower.contains("hubungi")) {
            return "📞 Contact Us:\n" +
                   "• Email: support@irisboutiques.com\n" +
                   "• Phone: +60 12-345 6789\n" +
                   "• Business Hours: 9 AM - 6 PM (Mon-Fri)\n\n" +
                   "We usually respond within 24 hours!";
        }
        
        // Thank you messages
        if (msgLower.contains("thank") || msgLower.contains("thanks") || 
            msgLower.contains("terima kasih")) {
            return "You're welcome! Is there anything else I can help you with?\n\n" +
                   "Happy shopping at iRis Boutiques!";
        }
        
        // Hello/Hi messages
        if (msgLower.contains("hello") || msgLower.contains("hi") || 
            msgLower.contains("hey") || msgLower.contains("hai")) {
            return "Hello! Welcome to iRis Boutiques!\n" +
                   "I'm here to help you with:\n" +
                   "• Product information\n" +
                   "• Pricing & discounts\n" +
                   "• Shipping details\n" +
                   "• Payment options\n\n" +
                   "What would you like to know?";
        }
        
        // Order tracking
        if (msgLower.contains("order") || msgLower.contains("track") || 
            msgLower.contains("pesanan")) {
            return "📦 Order Information:\n" +
                   "Your order receipt is saved as a .txt file after checkout.\n" +
                   "Look for 'order_[timestamp].txt' in your project folder.\n\n" +
                   "For order tracking, please contact us with your order number!";
        }
        
        // Help request
        if (msgLower.contains("help") || msgLower.contains("tolong")) {
            return "I'm here to help!\n\n" +
                   "You can ask me about:\n" +
                   "• Products & Prices\n" +
                   "• Shipping & Delivery\n" +
                   "• Payment Methods\n" +
                   "• Discounts & Offers\n" +
                   "• Returns & Exchanges\n\n" +
                   "Just type your question!";
        }
        
        // Default response for unrecognized queries
        return "Thank you for your message! \n\n" +
               "I can help you with:\n" +
               "• Product inquiries\n" +
               "• Shipping & payment info\n" +
               "• Discounts & promotions\n" +
               "• Returns & exchanges\n\n" +
               "Please ask me anything specific, or type 'help' for more options!";
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChatWindow();
        });
    }
}