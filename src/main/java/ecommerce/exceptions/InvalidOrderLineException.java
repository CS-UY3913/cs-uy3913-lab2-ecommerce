package ecommerce.exceptions;
/**
 * Custom unchecked exception to represent an error in a specific order line.
 * This class should hold the state of which order ID failed.
 */

/**
 * TODO: Implement this custom exception.
 * 1. Extend RuntimeException.
 * 2. Add a private final String field for 'orderId'.
 * 3. Create a constructor that takes (String orderId, String message).
 * Hint: Pass the 'message' to the superclass (RuntimeException) and
 * initialize your local 'orderId' field.
 * 4. Add a public getter for orderId.
 */

public class InvalidOrderLineException extends RuntimeException {
    // TODO: Implement fields, constructor, and getter
}