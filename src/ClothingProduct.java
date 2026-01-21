/**
 * ClothingProduct - Extends Product
 * Demonstrates INHERITANCE and POLYMORPHISM
 * Specific attributes and behaviors for clothing items
 */
public class ClothingProduct extends Product {
    private String size;        // XS, S, M, L, XL
    private String color;
    private String material;
    private double discount;    // Percentage discount

    // Constructor
    public ClothingProduct(String productId, String name, double price, int stockQuantity,
                           String size, String color, String material, double discount) {
        // Call parent constructor
        super(productId, name, price, stockQuantity, "Clothing");
        this.size = size;
        this.color = color;
        this.material = material;
        this.discount = discount;
    }

    // Implement abstract method - POLYMORPHISM in action!
    @Override
    public String getProductDetails() {
        return String.format(
                "👗 CLOTHING\n" +
                        "ID: %s\n" +
                        "Name: %s\n" +
                        "Size: %s | Color: %s\n" +
                        "Material: %s\n" +
                        "Price: RM%.2f\n" +
                        "Discount: %.0f%%\n" +
                        "Final Price: RM%.2f\n" +
                        "Stock: %d units",
                productId, name, size, color, material,
                price, discount, calculateFinalPrice(), stockQuantity
        );
    }

    // Implement abstract method - Different calculation than accessories
    @Override
    public double calculateFinalPrice() {
        return price - (price * discount / 100.0);
    }

    // Additional method specific to clothing
    public String getSizeGuide() {
        return String.format("Size %s - Check our size chart for measurements", size);
    }

    // Getters
    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public String getMaterial() {
        return material;
    }

    public double getDiscount() {
        return discount;
    }

    // Override toString for display in lists
    @Override
    public String toString() {
        return String.format("%s | %s (%s, %s) | RM%.2f (-%.0f%%) = RM%.2f | Stock: %d",
                productId, name, size, color, price, discount, calculateFinalPrice(), stockQuantity);
    }
}