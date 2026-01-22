public class PantsProduct extends Product {
    private String fitType;     // Slim, Regular, Relaxed
    private double discount;

    // Constructor
    public PantsProduct(String productId, String name, String size, String color,
                        double price, int stockQuantity, String fitType, double discount) {
        super(productId, name, size, color, price, stockQuantity, "Pants");
        this.fitType = fitType;
        this.discount = discount;
    }

    // POLYMORPHISM - Override abstract method
    @Override
    public String getProductDetails() {
        return String.format(
                "👖 PANTS\n" +
                        "═══════════════════════════════\n" +
                        "ID: %s\n" +
                        "Product: %s\n" +
                        "Size: %s\n" +
                        "Color: %s\n" +
                        "Fit Type: %s\n" +
                        "Original Price: RM%.2f\n" +
                        "Discount: %.0f%%\n" +
                        "Final Price: RM%.2f\n" +
                        "Stock Available: %d units\n" +
                        "═══════════════════════════════",
                productId, name, sizeOrNumber, color, fitType,
                price, discount, calculateFinalPrice(), stockQuantity
        );
    }

    // POLYMORPHISM - Different calculation (applies discount)
    @Override
    public double calculateFinalPrice() {
        return price - (price * discount / 100.0);
    }

    // Additional methods
    public String getFitType() {
        return fitType;
    }

    public double getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return String.format("%s | %s (Size %s, %s) | RM%.2f (-%.0f%%) = RM%.2f | Stock: %d",
                productId, name, sizeOrNumber, color, price, discount,
                calculateFinalPrice(), stockQuantity);
    }
}