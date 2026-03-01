package ecommerce;

public record Order(String orderId, String product, int quantity, String customerEmail) {}