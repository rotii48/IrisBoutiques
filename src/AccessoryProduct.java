/**
 * AccessoryProduct - Extends Product
 * Demonstrates INHERITANCE and POLYMORPHISM
 * Specific attributes for accessories (bags, jewelry, etc.)
 */
public class AccessoryProduct extends Product {
    private String type;        // Bag, Jewelry, Hat, etc.
    private String brand;
    private boolean isGiftWrappable;
    private double taxRate;     // Tax percentage

    // Constructor
    public AccessoryProduct(String productId, String name, double price, int stockQuantity,
                            String type, String brand, boolean isGiftWrappable, double taxRate) {
        // Call parent constructor
        super(productId, name, price, stockQuantity, "Accessory");
        this.type = type;
        this.brand = brand;
        this.isGiftWrappable = isGiftWrappable;
        this.taxRate = taxRate;
    }

    // Implement abstract method - POLYMORPHISM (different from ClothingProduct!)
    @Override
    public String getProductDetails() {
        return String.format(
                "👜 ACCESSORY\n" +
                        "ID: %s\n" +
                        "Name: %s\n" +
                        "Type: %s | Brand: %s\n" +
                        "Base Price: RM%.2f\n" +
                        "Tax (%.0f%%): RM%.2f\n" +
                        "Final Price: RM%.2f\n" +
                        "Gift Wrap: %s\n" +
                        "Stock: %d units",
                productId, name, type, brand, price, taxRate,
                (price * taxRate / 100.0), calculateFinalPrice(),
                isGiftWrappable ? "Available" : "Not Available",
                stockQuantity
        );
    }

    // Implement abstract method - Different calculation (adds tax instead of discount)
    @Override
    public double calculateFinalPrice() {
        return price + (price * taxRate / 100.0);
    }

    // Additional method specific to accessories
    public String getGiftWrapOption() {
        if (isGiftWrappable) {
            return "🎁 Free gift wrap available for this item!";
        } else {
            return "Gift wrap not available for this item.";
        }
    }

    // Getters
    public String getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }

    public boolean isGiftWrappable() {
        return isGiftWrappable;
    }

    public double getTaxRate() {
        return taxRate;
    }

    // Override toString for display in lists
    @Override
    public String toString() {
        return String.format("%s | %s (%s by %s) | RM%.2f (+%.0f%% tax) = RM%.2f | Stock: %d",
                productId, name, type, brand, price, taxRate, calculateFinalPrice(), stockQuantity);
    }
}