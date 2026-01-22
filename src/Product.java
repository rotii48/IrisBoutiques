public abstract class Product {
    protected String productId;
    protected String name;
    protected String sizeOrNumber;  // Size (S/M/L) or Shoe Number (38/39/40)
    protected String color;
    protected double price;
    protected int stockQuantity;
    protected String category;

    // Constructor
    public Product(String productId, String name, String sizeOrNumber, String color, 
                   double price, int stockQuantity, String category) {
        this.productId = productId;
        this.name = name;
        this.sizeOrNumber = sizeOrNumber;
        this.color = color;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }

    // Abstract methods - POLYMORPHISM
    public abstract String getProductDetails();
    public abstract double calculateFinalPrice();

    // Concrete methods
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

    // Getters
    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getSizeOrNumber() {
        return sizeOrNumber;
    }

    public String getColor() {
        return color;
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

    @Override
    public String toString() {
        return String.format("%s | %s (%s, %s) | RM%.2f | Stock: %d",
                productId, name, sizeOrNumber, color, price, stockQuantity);
    }
}