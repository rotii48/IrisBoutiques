import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class FileManager {


    public static Product[] loadAllProducts() {
        ArrayList<Product> allProducts = new ArrayList<>();

        // Load from each file
        allProducts.addAll(loadClothing("cloth.txt"));
        allProducts.addAll(loadPants("pants.txt"));
        allProducts.addAll(loadShoes("shoes.txt"));

        System.out.println("✅ Loaded " + allProducts.size() + " total products");

        return allProducts.toArray(new Product[0]);
    }


    private static ArrayList<Product> loadClothing(String filename) {
        ArrayList<Product> products = new ArrayList<>();
        int productCounter = 1;

        try {
            Scanner sc = new Scanner(new File(filename));

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] tokens = line.split(",");
                
                String name = tokens[0].trim();         // Blouse, T-Shirt
                String size = tokens[1].trim();         // S, M, L
                String color = tokens[2].trim();        // White, Black, Blue
                double price = Double.parseDouble(tokens[3].trim());

                // Generate unique ID
                String productId = String.format("C%03d", productCounter++);

                // Create ClothingProduct (with default values for material and discount)
                String material = "Cotton";  // Default material
                double discount = 10.0;      // Default 10% discount

                Product p = new ClothingProduct(productId, name, size, color, 
                        price, 5, material, discount);  // Stock = 5 each
                
                products.add(p);
            }

            sc.close();
            System.out.println("✅ Loaded " + products.size() + " clothing items from " + filename);

        } catch (FileNotFoundException fnf) {
            System.err.println("⚠️ File not found: " + filename);
        } catch (Exception e) {
            System.err.println("⚠️ Error loading " + filename + ": " + e.getMessage());
        }

        return products;
    }


    private static ArrayList<Product> loadPants(String filename) {
        ArrayList<Product> products = new ArrayList<>();
        int productCounter = 1;

        try {
            Scanner sc = new Scanner(new File(filename));

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] tokens = line.split(",");
                
                String name = tokens[0].trim();         // Jeans, Palazzo
                String size = tokens[1].trim();         // S, M, L
                String color = tokens[2].trim();        // Dark Blue, Light Blue, etc.
                double price = Double.parseDouble(tokens[3].trim());

                // Generate unique ID
                String productId = String.format("P%03d", productCounter++);

                // Create PantsProduct (with default values)
                String fitType = "Regular";  // Default fit
                double discount = 15.0;      // Default 15% discount

                Product p = new PantsProduct(productId, name, size, color, 
                        price, 5, fitType, discount);  // Stock = 5 each
                
                products.add(p);
            }

            sc.close();
            System.out.println("✅ Loaded " + products.size() + " pants items from " + filename);

        } catch (FileNotFoundException fnf) {
            System.err.println("⚠️ File not found: " + filename);
        } catch (Exception e) {
            System.err.println("⚠️ Error loading " + filename + ": " + e.getMessage());
        }

        return products;
    }


    private static ArrayList<Product> loadShoes(String filename) {
        ArrayList<Product> products = new ArrayList<>();
        int productCounter = 1;

        try {
            Scanner sc = new Scanner(new File(filename));

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] tokens = line.split(",");
                
                String name = tokens[0].trim();         // Heels, Sneakers
                String number = tokens[1].trim();       // 38, 39, 40
                String color = tokens[2].trim();        // Black, Cream, White
                double price = Double.parseDouble(tokens[3].trim());

                // Generate unique ID
                String productId = String.format("S%03d", productCounter++);

                // Create ShoesProduct (with default values)
                String brand = "iRis Collection";  // Default brand
                double taxRate = 6.0;              // 6% tax

                Product p = new ShoesProduct(productId, name, number, color, 
                        price, 5, brand, taxRate);  // Stock = 5 each
                
                products.add(p);
            }

            sc.close();
            System.out.println("✅ Loaded " + products.size() + " shoes items from " + filename);

        } catch (FileNotFoundException fnf) {
            System.err.println("⚠️ File not found: " + filename);
        } catch (Exception e) {
            System.err.println("⚠️ Error loading " + filename + ": " + e.getMessage());
        }

        return products;
    }


    // ORIGINAL METHOD (kept for backward compatibility)
    public static void saveOrderToFile(String filename, String customerName,
                                       Product[] orderedProducts, int[] quantities,
                                       boolean expressShipping, String paymentMethod) {
        try {
            PrintWriter pw = new PrintWriter(filename);

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

            double totalAmount = 0.0;

            for (int i = 0; i < orderedProducts.length; i++) {
                Product p = orderedProducts[i];
                int qty = quantities[i];

                double itemPrice = p.calculateFinalPrice();
                double itemTotal = itemPrice * qty;

                String format = "%-20s\t%3d\tRM%-6.2f\tRM%-8.2f\n";
                pw.printf(format, p.getName() + " (" + p.getSizeOrNumber() + ", " + p.getColor() + ")", 
                          qty, itemPrice, itemTotal);

                totalAmount += itemTotal;
            }

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

            pw.close();

            System.out.println("✅ Order saved to " + filename);

        } catch (FileNotFoundException fnf) {
            System.err.println("❌ File error! " + fnf.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Error saving order: " + e.getMessage());
        }
    }
    
    
    // NEW METHOD: Save order with full customer profile
    public static void saveOrderToFileWithProfile(String filename, 
                                                  String customerName,
                                                  String customerEmail,
                                                  String customerPhone,
                                                  String customerAddress,
                                                  Product[] orderedProducts, 
                                                  int[] quantities,
                                                  boolean expressShipping, 
                                                  String paymentMethod,
                                                  double shippingCost) {
        try {
            PrintWriter pw = new PrintWriter(filename);

            pw.println("╔═══════════════════════════════════════════════════════╗");
            pw.println("║         IRIS BOUTIQUES - ORDER RECEIPT                ║");
            pw.println("╚═══════════════════════════════════════════════════════╝");
            pw.println();
            pw.println("Order Date: " + java.time.LocalDateTime.now());
            pw.println("Order ID: " + filename.replace("order_", "").replace(".txt", ""));
            pw.println();
            pw.println("═══════════════════════════════════════════════════════");
            pw.println("CUSTOMER INFORMATION");
            pw.println("═══════════════════════════════════════════════════════");
            pw.println("Name:    " + customerName);
            pw.println("Email:   " + customerEmail);
            pw.println("Phone:   " + customerPhone);
            pw.println("Address: " + customerAddress);
            pw.println();
            pw.println("═══════════════════════════════════════════════════════");
            pw.println("ITEMS ORDERED");
            pw.println("═══════════════════════════════════════════════════════");
            pw.println();
            pw.printf("%-25s %5s %10s %12s\n", "Product", "Qty", "Price", "Total");
            pw.println("───────────────────────────────────────────────────────");

            double subtotal = 0.0;

            for (int i = 0; i < orderedProducts.length; i++) {
                Product p = orderedProducts[i];
                int qty = quantities[i];

                double itemPrice = p.calculateFinalPrice();
                double itemTotal = itemPrice * qty;

                String productDesc = p.getName() + " (" + p.getSizeOrNumber() + ", " + p.getColor() + ")";
                pw.printf("%-25s %5d %10.2f %12.2f\n", 
                          productDesc.length() > 25 ? productDesc.substring(0, 25) : productDesc,
                          qty, itemPrice, itemTotal);

                subtotal += itemTotal;
            }

            pw.println("───────────────────────────────────────────────────────");
            pw.println();
            pw.printf("Subtotal:                                    RM %8.2f\n", subtotal);
            
            String shippingLabel;
            if (shippingCost == 0.0) {
                shippingLabel = "FREE Shipping (Order > RM100)";
            } else if (expressShipping) {
                shippingLabel = "Express Shipping";
            } else {
                shippingLabel = "Standard Shipping";
            }
            
            pw.printf("%-44s RM %8.2f\n", shippingLabel + ":", shippingCost);
            pw.println("═══════════════════════════════════════════════════════");
            pw.printf("TOTAL AMOUNT:                                RM %8.2f\n", subtotal + shippingCost);
            pw.println("═══════════════════════════════════════════════════════");
            pw.println();
            pw.println("Payment Method: " + paymentMethod);
            pw.println();
            pw.println("───────────────────────────────────────────────────────");
            pw.println("          Thank you for shopping with us!             ");
            pw.println("              iRis Boutiques - Est. 2025               ");
            pw.println("           support@irisboutiques.com                  ");
            pw.println("           +60 12-345 6789                            ");
            pw.println("───────────────────────────────────────────────────────");

            pw.close();

            System.out.println("✅ Order saved to " + filename);

        } catch (FileNotFoundException fnf) {
            System.err.println("❌ File error! " + fnf.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Error saving order: " + e.getMessage());
        }
    }
}