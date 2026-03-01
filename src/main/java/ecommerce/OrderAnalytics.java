package ecommerce;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderAnalytics {

    public List<String> getUniqueCustomers(List<Order> orders) {
        // TODO: Start a stream() from the 'orders' list.
        // TODO: Chain operations to: map to email -> lowercase -> distinct -> sorted.
        // TODO: Collect the final results into a List.
        return null;
    }
    /**
     * Requirement: Tally the total number of orders for each product name.
     */

    public Map<String, Long> countOrdersByProduct(List<Order> orders) {
        // TODO: Use a Stream and Collectors.groupingBy to organize orders by product name.
        // TODO: Use Collectors.counting() as the downstream collector to get the count for each group.
        return null;
    }
}