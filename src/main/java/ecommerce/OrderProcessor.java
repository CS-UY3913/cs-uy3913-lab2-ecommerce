package ecommerce;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class OrderProcessor {
    /**
     * Requirement: Filter the list to remove high-quantity orders.
     */

    public void removeFraudulentOrders(List<Order> orders) {
        // TODO: Use an explicit Iterator to safely traverse and modify the list [cite: 2025-12-15].
        // TODO: Remove any order where quantity > 100.
        // Hint: it.remove() is required to avoid a ConcurrentModificationException.
    }

    public List<String> processFifo(List<Order> orders, InventoryManager inventory) {
        // TODO: Initialize a Queue<Order> (e.g., ArrayDeque) and populate it with all input orders [cite: 2025-12-15].
        // TODO: Create a List<String> to track the IDs of orders successfully fulfilled by inventory.

        // TODO: While the queue is not empty, use poll() to retrieve orders one by one [cite: 2025-12-15].
        // TODO: Use inventory.fulfill(order) to check stock; if true, record the orderId in your success list.

        return new ArrayList<>();  // Starter placeholder
    }
}