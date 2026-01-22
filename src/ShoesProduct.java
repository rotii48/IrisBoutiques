public class ShoesProduct extends Product {
    private String brand;
    private double taxRate;

    // Constructor
    public ShoesProduct(String productId, String name, String shoeNumber, String color,
                        double price, int stockQuantity, String brand, double taxRate) {
        super(productId, name, shoeNumber, color, price, stockQuantity, "Shoes");
        this.brand = brand;
        this.taxRate = taxRate;
    }

    // POLYMORPHISM - Override abstract method
    @Override
    public String getProductDetails() {
        return String.format(
                "👠 SHOES\n" +
                        "═══════════════════════════════\n" +
                        "ID: %s\n" +
                        "Product: %s\n" +
                        "Shoe Number: %s\n" +
                        "Color: %s\n" +
                        "Brand: %s\n" +
                        "Base Price: RM%.2f\n" +
                        "Tax (%.0f%%): RM%.2f\n" +
                        "Final Price: RM%.2f\n" +
                        "Stock Available: %d units\n" +
                        "═══════════════════════════════",
                productId, name, sizeOrNumber, color, brand,
                price, taxRate, (price * taxRate / 100.0),
                calculateFinalPrice(), stockQuantity
        );
    }

    // POLYMORPHISM - Different calculation (adds tax instead of discount)
    @Override
    public double calculateFinalPrice() {
        return price + (price * taxRate / 100.0);
    }

    // Additional methods
    public String getBrand() {
        return brand;
    }

    public double getTaxRate() {
        return taxRate;
    }

    @Override
    public String toString() {
        return String.format("%s | %s (No. %s, %s) by %s | RM%.2f (+%.0f%%) = RM%.2f | Stock: %d",
                productId, name, sizeOrNumber, color, brand, price, taxRate,
                calculateFinalPrice(), stockQuantity);
    }
}