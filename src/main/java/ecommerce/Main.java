package ecommerce;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * The Main class serves as the entry point for the Lab 2 application.
 * Students: Use this class to verify your logic as you implement each part.
 * Running this file will demonstrate the full end-to-end data pipeline.
 */
public class Main {
    public static void main(String[] args) {

        // --- SECTION 1: SYSTEM INITIALIZATION ---
        // Set up the inventory manager and prime it with initial stock levels.
        InventoryManager inventory = new InventoryManager();
        inventory.addStock("Laptop", 10);
        inventory.addStock("Smartphone", 50);

        // --- SECTION 2: DATA IMPORT (PARTIAL SUCCESS PATTERN) ---
        // Read from the local CSV file. Bad data should be logged as warnings, not crash the app.
        OrderImporter importer = new OrderImporter();
        List<Order> importedOrders = importer.importOrders(Path.of("src/main/resources/orders.csv"));
        System.out.println("\n>>> Import Phase Complete. Valid Orders Imported: " + importedOrders.size());

        // --- SECTION 3: CORE PROCESSING (COLLECTIONS & LOGIC) ---
        // First, Use an Iterator to remove fraudulent high-quantity orders.
        // Then, Use a Queue to process remaining orders in FIFO sequence.
        OrderProcessor processor = new OrderProcessor();
        processor.removeFraudulentOrders(importedOrders);
        List<String> successfulIds = processor.processFifo(importedOrders, inventory);

        // --- SECTION 4: FINAL DEMAND REPORT (STREAMS & ANALYTICS) ---
        // Use the Stream API to generate a final summary report for the business.
        OrderAnalytics analytics = new OrderAnalytics();

        System.out.println("\n==========================================");
        System.out.println("        FINAL DEMAND REPORT              ");
        System.out.println("==========================================");

        System.out.println("Successfully Fulfilled IDs: " + successfulIds);

        List<String> uniqueCustomers = analytics.getUniqueCustomers(importedOrders);
        System.out.println("Unique Customer Base (" + uniqueCustomers.size() + "): " + uniqueCustomers);

        Map<String, Long> productCounts = analytics.countOrdersByProduct(importedOrders);
        System.out.println("Total Demand by Product: " + productCounts);
        System.out.println("==========================================\n");
    }
}