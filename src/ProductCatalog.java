import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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

    // ARRAYS
    private Product[] allProducts;
    private Product[] filteredProducts;

    public ProductCatalog() {
        super.setTitle("Product Catalog - iRis Boutiques");
        super.setSize(800, 600);
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Container cp = super.getContentPane();
        cp.add(pnlMain);

        loadProducts();
        displayProducts(allProducts);

        btnRefresh.addActionListener(this);
        btnViewDetails.addActionListener(this);
        btnAddToCart.addActionListener(this);
        cmbCategory.addActionListener(this);

        super.setVisible(true);
    }

    private void loadProducts() {
        allProducts = FileManager.loadAllProducts();
        if (allProducts == null || allProducts.length == 0) {
            allProducts = createSampleProducts();
        }
        filteredProducts = allProducts;
    }

    private Product[] createSampleProducts() {
        Product[] products = new Product[6];
        products[0] = new ClothingProduct("C001", "Blouse", "M", "White", 69.90, 5, "Cotton", 10);
        products[1] = new ClothingProduct("C002", "T-Shirt", "L", "Beige", 49.90, 5, "Cotton", 10);
        products[2] = new PantsProduct("P001", "Jeans", "M", "Dark Blue", 99.90, 5, "Regular", 15);
        products[3] = new PantsProduct("P002", "Palazzo", "L", "Beige", 89.90, 5, "Regular", 15);
        products[4] = new ShoesProduct("S001", "Heels", "38", "Black", 129.90, 5, "iRis", 6);
        products[5] = new ShoesProduct("S002", "Sneakers", "39", "Pink", 149.90, 5, "iRis", 6);
        return products;
    }

    private void displayProducts(Product[] products) {
        StringBuilder sb = new StringBuilder();
        sb.append("╔════════════════════════════════════════════════════════════════════════════╗\n");
        sb.append("  ID    | Product           | Size/No | Color      | Price    | Final Price  \n");
        sb.append("╠════════════════════════════════════════════════════════════════════════════╣\n");

        for (int i = 0; i < products.length; i++) {
            Product p = products[i];
            if (p != null) {
                sb.append(String.format("  %-6s| %-18s| %-8s| %-11s| RM%-7.2f| RM%-10.2f\n",
                        p.getProductId(), p.getName(), p.getSizeOrNumber(),
                        p.getColor(), p.getPrice(), p.calculateFinalPrice()));
            }
        }

        sb.append("╚════════════════════════════════════════════════════════════════════════════╝\n");
        sb.append(String.format("\nTotal Products: %d\n", products.length));
        
        int cartSize = ShoppingCart.getCartSize();
        if (cartSize > 0) {
            sb.append(String.format("🛒 Items in Cart: %d\n", cartSize));
        }

        txtProductList.setText(sb.toString());
    }

    private void filterProducts(String category) {
        if (category.equals("All Products")) {
            filteredProducts = allProducts;
        } else {
            int count = 0;
            for (Product p : allProducts) {
                if (p != null && p.getCategory().equalsIgnoreCase(category)) {
                    count++;
                }
            }

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

    private Product findProductById(String productId) {
        for (int i = 0; i < filteredProducts.length; i++) {
            if (filteredProducts[i] != null &&
                    filteredProducts[i].getProductId().equalsIgnoreCase(productId)) {
                return filteredProducts[i];
            }
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cmbCategory) {
            String category = (String) cmbCategory.getSelectedItem();
            filterProducts(category);

        } else if (e.getSource() == btnRefresh) {
            loadProducts();
            filterProducts("All Products");
            cmbCategory.setSelectedIndex(0);
            JOptionPane.showMessageDialog(this, "✅ Products refreshed!");

        } else if (e.getSource() == btnViewDetails) {
            String productId = JOptionPane.showInputDialog(this, "Enter Product ID:");
            if (productId != null && !productId.trim().isEmpty()) {
                Product product = findProductById(productId.trim());
                if (product != null) {
                    JOptionPane.showMessageDialog(this, product.getProductDetails(),
                            "Product Details", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Product not found!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        } else if (e.getSource() == btnAddToCart) {
            String productId = JOptionPane.showInputDialog(this, "Enter Product ID:");
            if (productId != null && !productId.trim().isEmpty()) {
                Product product = findProductById(productId.trim());
                if (product != null) {
                    if (product.getStockQuantity() <= 0) {
                        JOptionPane.showMessageDialog(this, "Out of stock!", "Error",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    String qtyStr = JOptionPane.showInputDialog(this,
                            "Quantity (Available: " + product.getStockQuantity() + "):", "1");
                    if (qtyStr != null && !qtyStr.trim().isEmpty()) {
                        try {
                            int qty = Integer.parseInt(qtyStr.trim());
                            if (qty > 0 && qty <= product.getStockQuantity()) {
                                ShoppingCart.addToCart(product, qty);
                                product.reduceStock(qty);
                                JOptionPane.showMessageDialog(this,
                                        String.format("✅ Added!\n%s (%s, %s)\nQty: %d\nTotal: RM%.2f\n\nCart: %d items",
                                                product.getName(), product.getSizeOrNumber(), product.getColor(),
                                                qty, product.calculateFinalPrice() * qty, ShoppingCart.getCartSize()),
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
                                displayProducts(filteredProducts);
                            } else {
                                JOptionPane.showMessageDialog(this, "Invalid quantity!", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Invalid number!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Product not found!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProductCatalog::new);
    }
}