package ecommerce;

import ecommerce.exceptions.FileProcessingException;
import ecommerce.exceptions.InvalidOrderLineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class OrderImporter {
    private static final Logger log = LoggerFactory.getLogger(OrderImporter.class);

    public List<Order> importOrders(Path filePath) {
        List<Order> validOrders = new ArrayList<>();

        // TODO: Use try-with-resources to create a BufferedReader using Files.newBufferedReader(filePath).
        // TODO: Catch the broad IOException and throw a FileProcessingException, ensuring you chain the original exception as the cause.

        // TODO: Use a while loop to read the file line by line.
        // TODO: INSIDE the loop, use a narrow try-catch block to implement the "Partial Success" pattern.

        // TODO: Split the line by commas. If fields are missing (less than 4 parts), throw InvalidOrderLineException.
        // TODO: Parse the quantity. If quantity < 0, throw InvalidOrderLineException.
        // Hint: Be mindful of NumberFormatException when parsing integers.

        // TODO: If the line is valid, add a new Order to the validOrders list.

        // TODO: Catch InvalidOrderLineException. Use log.warn() to skip bad rows and keep the program running.

        return validOrders;
    }
}