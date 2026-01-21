import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ProductCatalog - Display products using ARRAYS - FIXED VERSION
 * Now integrates with ShoppingCart!
 */
@SuppressWarnings("unused")
public class ProductCatalog extends JFrame implements ActionListener {
    // GUI Components
    private JPanel pnlMain;
    private JComboBox<String> cmbCategory;
    private JButton btnRefresh;
    private JTextArea txtProductList;
    private JLabel lblSelectedProduct;
    private JButton btnViewDetails;
    private JButton btnAddToCart;

    // ARRAYS - Core requirement!
    private Product[] allProducts;
    private Product[] filteredProducts;
    private int selectedIndex = -1;

    public ProductCatalog() {
        super.setTitle("Product Catalog - iRis Boutiques");
        super.setSize(750, 600);
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Container cp = super.getContentPane();
        cp.add(pnlMain);

        // Load products from file into ARRAY
        loadProducts();

        // Display initial products
        displayProducts(allProducts);

        // Add action listeners
        btnRefresh.addActionListener(this);
        btnViewDetails.addActionListener(this);
        btnAddToCart.addActionListener(this);
        cmbCategory.addActionListener(this);

        super.setVisible(true);
    }

    /**
     * Load products from file - stores in ARRAY
     */
    private void loadProducts() {
        // Try to load from file
        allProducts = FileManager.loadProductsFromFile("products.csv");

        // If file not found or empty, create sample data
        if (allProducts == null || allProducts.length == 0) {
            System.out.println("⚠️ products.csv not found, using sample data");
            allProducts = createSampleProducts();
        } else {
            System.out.println("✅ Loaded " + allProducts.length + " products from file");
        }

        filteredProducts = allProducts;
    }

    /**
     * Create sample products - demonstrates ARRAY creation
     */
    private Product[] createSampleProducts() {
        Product[] products = new Product[10];

        // POLYMORPHISM - array holds both types
        products[0] = new ClothingProduct("P001", "Floral Maxi Dress", 89.90, 15,
                "M", "Pink", "Cotton", 15);
        products[1] = new ClothingProduct("P002", "Denim Jacket", 129.90, 8,
                "L", "Blue", "Denim", 10);
        products[2] = new ClothingProduct("P003", "Silk Blouse", 69.90, 20,
                "S", "White", "Silk", 20);
        products[3] = new ClothingProduct("P004", "Summer Skirt", 59.90, 12,
                "M", "Yellow", "Polyester", 15);
        products[4] = new ClothingProduct("P005", "Cardigan Sweater", 79.90, 10,
                "L", "Beige", "Wool", 10);

        products[5] = new AccessoryProduct("P006", "Leather Handbag", 199.90, 5,
                "Bag", "Gucci", true, 6);
        products[6] = new AccessoryProduct("P007", "Pearl Necklace", 149.90, 7,
                "Jewelry", "Tiffany", true, 6);
        products[7] = new AccessoryProduct("P008", "Sunglasses", 89.90, 15,
                "Eyewear", "RayBan", false, 6);
        products[8] = new AccessoryProduct("P009", "Silk Scarf", 45.90, 20,
                "Scarf", "Hermes", true, 6);
        products[9] = new AccessoryProduct("P010", "Leather Belt", 55.90, 18,
                "Belt", "Coach", false, 6);

        return products;
    }

    /**
     * Display products from ARRAY
     */
    private void displayProducts(Product[] products) {
        StringBuilder sb = new StringBuilder();
        sb.append("╔════════════════════════════════════════════════════════════════════╗\n");
        sb.append("  ID    | Product Name              | Price    | Stock | Final Price  \n");
        sb.append("╠════════════════════════════════════════════════════════════════════╣\n");

        // ARRAY ITERATION
        for (int i = 0; i < products.length; i++) {
            Product p = products[i];
            if (p != null) {
                // POLYMORPHISM in action!
                sb.append(String.format("  %-6s| %-26s| RM%-7.2f| %-6d| RM%-10.2f\n",
                        p.getProductId(),
                        p.getName(),
                        p.getPrice(),
                        p.getStockQuantity(),
                        p.calculateFinalPrice()
                ));
            }
        }

        sb.append("╚════════════════════════════════════════════════════════════════════╝\n");
        sb.append(String.format("\nTotal Products: %d\n", products.length));

        // Show cart status
        int cartSize = ShoppingCart.getCartSize();
        if (cartSize > 0) {
            sb.append(String.format("🛒 Items in Cart: %d\n", cartSize));
        }

        txtProductList.setText(sb.toString());
    }

    /**
     * Filter products by category
     */
    private void filterProducts(String category) {
        if (category.equals("All Products")) {
            filteredProducts = allProducts;
        } else {
            // Count matching products
            int count = 0;
            for (Product p : allProducts) {
                if (p != null && p.getCategory().equalsIgnoreCase(category)) {
                    count++;
                }
            }

            // Create filtered ARRAY
            filteredProducts = new Product[count];
            int index = 0;
            for (Product p : allProducts) {
                if (p != null && p.getCategory().equalsIgnoreCase(category)) {
                    filteredProducts[index++] = p;
                }
            }
        }

        displayProducts(filteredProducts);
    }

    /**
     * Search product by ID in ARRAY - LINEAR SEARCH
     */
    private Product findProductById(String productId) {
        for (int i = 0; i < filteredProducts.length; i++) {
            if (filteredProducts[i] != null &&
                    filteredProducts[i].getProductId().equalsIgnoreCase(productId)) {
                selectedIndex = i;
                return filteredProducts[i];
            }
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cmbCategory) {
            // Filter products
            String category = (String) cmbCategory.getSelectedItem();
            filterProducts(category);

        } else if (e.getSource() == btnRefresh) {
            // Reload products
            loadProducts();
            filterProducts("All Products");
            cmbCategory.setSelectedIndex(0);
            JOptionPane.showMessageDialog(this, "Products refreshed!");

        } else if (e.getSource() == btnViewDetails) {
            // Show product details
            String productId = JOptionPane.showInputDialog(this,
                    "Enter Product ID:", "View Product", JOptionPane.QUESTION_MESSAGE);

            if (productId != null && !productId.trim().isEmpty()) {
                Product product = findProductById(productId.trim());
                if (product != null) {
                    // POLYMORPHISM - different details for each type!
                    JOptionPane.showMessageDialog(this,
                            product.getProductDetails(),
                            "Product Details",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Product not found!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        } else if (e.getSource() == btnAddToCart) {
            // FIXED: Add to cart functionality
            String productId = JOptionPane.showInputDialog(this,
                    "Enter Product ID to add to cart:",
                    "Add to Cart",
                    JOptionPane.QUESTION_MESSAGE);

            if (productId != null && !productId.trim().isEmpty()) {
                Product product = findProductById(productId.trim());

                if (product != null) {
                    // Check stock
                    if (product.getStockQuantity() <= 0) {
                        JOptionPane.showMessageDialog(this,
                                "Sorry, this product is out of stock!",
                                "Out of Stock",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Ask for quantity
                    String qtyStr = JOptionPane.showInputDialog(this,
                            "Enter quantity (Available: " + product.getStockQuantity() + "):",
                            "1");

                    if (qtyStr != null && !qtyStr.trim().isEmpty()) {
                        try {
                            int qty = Integer.parseInt(qtyStr.trim());

                            if (qty > 0 && qty <= product.getStockQuantity()) {
                                // ADD TO CART!
                                ShoppingCart.addToCart(product, qty);

                                // Update stock
                                product.reduceStock(qty);

                                // Show success message
                                JOptionPane.showMessageDialog(this,
                                        String.format("✅ Added to Cart!\n\n" +
                                                        "Product: %s\n" +
                                                        "Quantity: %d\n" +
                                                        "Price: RM%.2f each\n" +
                                                        "Total: RM%.2f\n\n" +
                                                        "Cart now has %d item(s)",
                                                product.getName(),
                                                qty,
                                                product.calculateFinalPrice(),
                                                product.calculateFinalPrice() * qty,
                                                ShoppingCart.getCartSize()),
                                        "Success",
                                        JOptionPane.INFORMATION_MESSAGE);

                                // Refresh display
                                displayProducts(filteredProducts);

                            } else {
                                JOptionPane.showMessageDialog(this,
                                        "Invalid quantity or insufficient stock!",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this,
                                    "Please enter a valid number!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Product not found!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProductCatalog::new);
    }
}