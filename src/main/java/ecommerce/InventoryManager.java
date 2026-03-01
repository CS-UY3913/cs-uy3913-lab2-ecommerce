package ecommerce;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    // TODO: Add a private Map<String, Integer> field for stock and initialize it.

    public void addStock(String product, int amount) {
        // TODO: Update the map. Use getOrDefault to handle new products safely without NullPointerExceptions.
    }

    public boolean fulfill(Order order) {
        // TODO: Check if the product exists in stock and has sufficient quantity.
        // TODO: If sufficient, update the map by deducting the quantity and return true.
        // TODO: Otherwise, return false.
        return false;
    }
}