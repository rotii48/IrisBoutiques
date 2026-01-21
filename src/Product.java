/**
 * Product - Base class for all products
 * Demonstrates INHERITANCE concept
 * Following OOP principles from CSC435 requirements
 */
public abstract class Product {
    // Protected - accessible by child classes
    protected String productId;
    protected String name;
    protected double price;
    protected int stockQuantity;
    protected String category;

    // Constructor
    public Product(String productId, String name, double price, int stockQuantity, String category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }

    // Abstract method - MUST be implemented by child classes (POLYMORPHISM)
    public abstract String getProductDetails();

    // Abstract method - Different calculation for different product types (POLYMORPHISM)
    public abstract double calculateFinalPrice();

    // Concrete methods - shared by all products
    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public void reduceStock(int quantity) {
        if (stockQuantity >= quantity) {
            stockQuantity -= quantity;
        }
    }

    public void addStock(int quantity) {
        stockQuantity += quantity;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public String getCategory() {
        return category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    // toString for easy display
    @Override
    public String toString() {
        return String.format("%s | %s | RM%.2f | Stock: %d",
                productId, name, price, stockQuantity);
    }
}