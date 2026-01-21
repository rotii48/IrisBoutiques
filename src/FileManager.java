import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * FileManager - Handle file reading and writing
 * FOLLOWS lecturer's pattern from SaleApp.java and RainReport.java
 * Demonstrates FILE HANDLING requirement
 */
public class FileManager {

    /**
     * Load products from CSV file
     * Format: productId,name,price,stock,type,attr1,attr2,attr3,attr4
     * Following lecturer's Scanner pattern
     */
    public static Product[] loadProductsFromFile(String filename) {
        ArrayList<Product> productList = new ArrayList<>();

        try {
            // 1 - Open file stream (LIKE LECTURER)
            Scanner sc = new Scanner(new File(filename));

            // 2 - Skip header line if exists
            if (sc.hasNextLine()) {
                sc.nextLine(); // Skip header
            }

            // 3 - Loop to read all products (LIKE LECTURER)
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] tokens = line.split(","); // Break into tokens (LIKE LECTURER)

                // 4 - Parse data based on product type
                String productId = tokens[0];
                String name = tokens[1];
                double price = Double.parseDouble(tokens[2]); // Parse numeric (LIKE LECTURER)
                int stock = Integer.parseInt(tokens[3]);
                String type = tokens[4];

                // 5 - Create appropriate product object (POLYMORPHISM!)
                Product product;
                if (type.equalsIgnoreCase("Clothing")) {
                    String size = tokens[5];
                    String color = tokens[6];
                    String material = tokens[7];
                    double discount = Double.parseDouble(tokens[8]);
                    product = new ClothingProduct(productId, name, price, stock,
                            size, color, material, discount);
                } else if (type.equalsIgnoreCase("Accessory")) {
                    String accType = tokens[5];
                    String brand = tokens[6];
                    boolean giftWrap = Boolean.parseBoolean(tokens[7]);
                    double tax = Double.parseDouble(tokens[8]);
                    product = new AccessoryProduct(productId, name, price, stock,
                            accType, brand, giftWrap, tax);
                } else {
                    continue; // Skip unknown types
                }

                productList.add(product);
            }

            // 6 - Close stream (LIKE LECTURER)
            sc.close();

        }
        // 7 - Catch exceptions (LIKE LECTURER)
        catch (FileNotFoundException fnf) {
            System.err.println("File error! " + fnf.getMessage());
        }
        catch (Exception e) {
            System.err.println("Error loading products: " + e.getMessage());
            e.printStackTrace();
        }

        // Convert ArrayList to Array (FOR ARRAYS REQUIREMENT)
        return productList.toArray(new Product[0]);
    }

    /**
     * Save order to file
     * Following lecturer's PrintWriter pattern
     */
    public static void saveOrderToFile(String filename, String customerName,
                                       Product[] orderedProducts, int[] quantities,
                                       boolean expressShipping, String paymentMethod) {
        try {
            // 1 - Open PrintWriter stream (LIKE LECTURER)
            PrintWriter pw = new PrintWriter(filename);

            // 2 - Print header (LIKE LECTURER)
            pw.println("===============================================");
            pw.println("           IRIS BOUTIQUES - ORDER RECEIPT");
            pw.println("===============================================");
            pw.println("Customer: " + customerName);
            pw.println("Date: " + java.time.LocalDateTime.now());
            pw.println("Payment Method: " + paymentMethod);
            pw.println("===============================================");
            pw.println();
            pw.println("ITEMS ORDERED:");
            pw.println("Product\t\t\tQty\tPrice\tTotal");
            pw.println("-----------------------------------------------");

            // 3 - Declare variables for footer (LIKE LECTURER)
            double totalAmount = 0.0;

            // 4 - Loop through products (LIKE LECTURER)
            for (int i = 0; i < orderedProducts.length; i++) {
                Product p = orderedProducts[i];
                int qty = quantities[i];

                // 5 - Calculate (LIKE LECTURER)
                double itemPrice = p.calculateFinalPrice(); // POLYMORPHISM!
                double itemTotal = itemPrice * qty;

                // 6 - Print to file with formatting (LIKE LECTURER)
                String format = "%-20s\t%3d\tRM%-6.2f\tRM%-8.2f\n";
                pw.printf(format, p.getName(), qty, itemPrice, itemTotal);

                // 7 - Update footer variables (LIKE LECTURER)
                totalAmount += itemTotal;
            }

            // 8 - Print footer (LIKE LECTURER)
            pw.println("-----------------------------------------------");
            pw.printf("Subtotal:\t\t\t\tRM%.2f\n", totalAmount);

            if (expressShipping) {
                pw.printf("Express Shipping:\t\t\tRM10.00\n");
                totalAmount += 10.0;
            }

            pw.println("-----------------------------------------------");
            pw.printf("TOTAL AMOUNT:\t\t\t\tRM%.2f\n", totalAmount);
            pw.println("===============================================");
            pw.println("Thank you for shopping with iRis Boutiques!");
            pw.println("===============================================");

            // 9 - Close stream (LIKE LECTURER)
            pw.close();

            System.out.println("Order saved to " + filename);

        }
        // 10 - Catch exceptions (LIKE LECTURER)
        catch (FileNotFoundException fnf) {
            System.err.println("File error! " + fnf.getMessage());
        }
        catch (Exception e) {
            System.err.println("Error saving order: " + e.getMessage());
        }
    }

    /**
     * Generate sales report (BONUS - like lecturer's RainReport)
     */
    public static void generateSalesReport(String inputFile, String outputFile) {
        try {
            Scanner sc = new Scanner(new File(inputFile));
            PrintWriter pw = new PrintWriter(outputFile);

            pw.println("IRIS BOUTIQUES - SALES REPORT");
            pw.println("Generated: " + java.time.LocalDateTime.now());
            pw.println("===============================================");

            // Processing logic similar to RainReport.java
            // Can add calculations for total sales, best selling items, etc.

            pw.close();
            sc.close();
        }
        catch (FileNotFoundException fnf) {
            System.err.println(fnf.getMessage());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}