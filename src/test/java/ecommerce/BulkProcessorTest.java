package ecommerce;

import ecommerce.exceptions.FileProcessingException;
import ecommerce.exceptions.InvalidOrderLineException;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BulkProcessorTest {

    @Test
    void customException_carriesState() {
        InvalidOrderLineException ex = new InvalidOrderLineException("A1", "Bad");
        assertEquals("A1", ex.getOrderId());
    }

    @Test
    void inventory_safeMissingKeys() {
        InventoryManager inv = new InventoryManager();
        inv.addStock("Laptop", 5);
        assertTrue(inv.fulfill(new Order("1", "Laptop", 3, "x@x.com")));
        assertFalse(inv.fulfill(new Order("2", "Mouse", 1, "x@x.com")));
    }

    @Test
    void processor_iteratorRemovesSafely() {
        List<Order> list = new ArrayList<>();
        list.add(new Order("1", "A", 10, "x"));
        list.add(new Order("2", "B", 105, "y"));
        list.add(new Order("3", "C", 5, "z"));

        new OrderProcessor().removeFraudulentOrders(list);
        assertEquals(2, list.size());
        assertEquals("3", list.get(1).orderId());
    }

    @Test
    void streams_uniqueCustomers() {
        List<Order> list = List.of(
                new Order("1", "A", 1, "Alice@mail.com"),
                new Order("2", "A", 1, "alice@MAIL.com"),
                new Order("3", "B", 1, "bob@mail.com")
        );
        List<String> res = new OrderAnalytics().getUniqueCustomers(list);
        assertEquals(List.of("alice@mail.com", "bob@mail.com"), res);
    }
}