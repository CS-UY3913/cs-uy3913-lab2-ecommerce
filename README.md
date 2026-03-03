# Lab 2: E-Commerce Bulk Order Processor

## Objective

This lab focuses on building a robust data processing pipeline using the Java Collections Framework and professional error handling patterns. You will apply the **"Partial Success" pattern** to ensure that individual data errors do not crash an entire batch process.

By the end of this lab, you will be able to:

* Implement custom, state bearing unchecked exceptions.
* Apply Exception Chaining to preserve root causes during rethrowing.
* Use `Map` for efficient lookups and `Queue` for FIFO processing.
* Utilize `Iterator` to safely modify collections during traversal.
* Build functional data pipelines using the Java Stream API.

---

## Section 1: Setup

### 1. Install the Required Tools

* **Java 17** (Temurin-17 is recommended)
* **IntelliJ IDEA**
* **Apache Maven**

### 2. Clone the Starter Code Repository

```bash
git clone https://github.com/CS-UY3913/cs-uy3913-lab2-ecommerce.git
cd cs-uy3913-lab2-ecommerce
```

### 3. Open the Project in IntelliJ

* Open IntelliJ and select **Open**.
* Navigate to the cloned folder.
* Ensure IntelliJ recognizes the `pom.xml` file as a Maven project.
* Let Maven finish indexing and resolving dependencies (SLF4J, Logback, and JUnit 5).

---

## Section 2: Part 1 – Domain Models & Custom Exceptions

### Step 1: Review `Order.java`

This file is provided in:

```
src/main/java/ecommerce/
```

* It is a Java record used to hold order data.
* **Do not modify this file.**

### Step 2: Implement Custom Exceptions

Implement the following in:

```
src/main/java/ecommerce/exceptions/
```

#### `InvalidOrderLineException.java`

* Must extend `RuntimeException`.
* Store a private `orderId` of the failed row.
* Provide a getter for it.

#### `FileProcessingException.java`

* Must extend `RuntimeException`.
* Implement a constructor that takes:

  * a message
  * a `Throwable cause`
* Supports Exception Chaining.

---

## Section 3: Part 2 – The Bulk Importer (Partial Success)

### Step 1: Implement `OrderImporter.java`

This class is responsible for reading **"dirty" CSV data** from `src/main/resources/orders.csv`.

#### Try-with-resources

- Use this to open a `BufferedReader`.
- Catch `IOException` and rethrow it as `FileProcessingException`.
- Pass the original error as the cause to support **Exception Chaining**.

#### The Loop (Partial Success)


- Read the remaining file line by line.
- Use a narrow `try-catch` block inside the loop to catch `InvalidOrderLineException`.

#### Validation

- Parse each line by splitting on the comma (`,`) delimiter.
- If a row has missing fields (`length < 4`), throw an `InvalidOrderLineException`.
- If the quantity is negative or zero, throw an `InvalidOrderLineException`.
- If parsing the quantity fails (e.g., non-numeric data):
  - Catch the `NumberFormatException`
  - Rethrow it as an `InvalidOrderLineException`.

#### Logging

When an `InvalidOrderLineException` is caught inside the loop:

- Use `log.warn(...)` to log the failure message and the specific `orderId`.
- Allow the loop to continue processing the next line.
---

## Section 4: Part 3 – Collections & Processing

### Step 1: Implement `InventoryManager.java`

#### Storage

* Use `Map<String, Integer>` to track stock.

#### Add Stock

* Use `getOrDefault()` to safely increment stock for new or existing products without NullPointerExceptions.

#### Fulfillment

Implement:

```java
fulfill(Order order)
```

* If sufficient stock exists:

  * Deduct it
  * Return `true`
* Otherwise, return `false`.

### Step 2: Implement `OrderProcessor.java`

#### Fraud Removal

* Use an explicit `Iterator`.
* Remove any order with quantity > 100.
* Avoid `ConcurrentModificationException`.

#### FIFO Processing

* Load orders into a `Queue` (e.g., `ArrayDeque`).
* Use `poll()` to process them in sequence.
* Attempt fulfillment.

---

## Section 5: Part 4 – Analytics (Streams)

### Step 1: Implement `OrderAnalytics.java`

#### Unique Customers

Use a Stream pipeline:

```
map -> lowercase -> distinct -> sorted
```

* Return lowercase emails.
* Ensure uniqueness.
* Return alphabetically sorted list.

#### Product Counts

Use:

* `Collectors.groupingBy`
* `Collectors.counting()`

Return a `Map` of product names and their order counts.

#### Note: The order of entries in a standard `HashMap` is not guaranteed. If you use `TreeMap` for the result, your output will be alphabetically sorted to match the example in the report below.
---

## Section 6: Part 5 – Testing & Submission

### Step 1: Run the Main Application

Open Main.java and click the green "Run" arrow. This file demonstrates the full end to end data pipeline. If implemented correctly, your console should display the following:

#### Note: The order of entries in the "Total Demand" map may vary depending on your `Map` implementation. If you use `TreeMap`, your output will be alphabetically sorted as shown below.

```
==========================================
        FINAL DEMAND REPORT              
==========================================
Successfully Fulfilled IDs: [101, 102, 104]
Unique Customer Base (4): [alice@nyu.edu, bob@nyu.edu, missing_product@nyu.edu, out_of_stock@nyu.edu]
Total Demand by Product: {Headphones=1, Laptop=3, Smartphone=1}
==========================================
```

### Step 2: Run Local Unit Tests

A basic test suite (`BulkProcessorTest.java`) is provided.

Run:

```bash
mvn clean test
```

Expected output:

```
Tests run: 4, Failures: 0, Errors: 0, BUILD SUCCESS
```

*(Total test count may vary depending on implementation.)*

### Step 3: Create Submission ZIP

Run this command from your project root and upload to Gradescope:

```bash
zip -r submission.zip src/ pom.xml
```

---

## Section 7: Project Structure & Troubleshooting

### Project Structure

```
cs-uy3913-lab2-ecommerce/
├── pom.xml
├── README.md
└── src/
    ├── main/java/ecommerce/
    │   ├── exceptions/
    │   │   ├── FileProcessingException.java
    │   │   └── InvalidOrderLineException.java
    │   ├── InventoryManager.java
    │   ├── Main.java                          ← Entry point (Do not modify)
    │   ├── Order.java (Record)
    │   ├── OrderAnalytics.java
    │   ├── OrderImporter.java
    │   └── OrderProcessor.java
    ├── main/resources/
    │   └── orders.csv                         ← Sample data
    └── test/java/ecommerce/
        └── BulkProcessorTest.java
```

### Troubleshooting

**Red underlines in test files**

* This is expected initially because your custom exceptions and methods haven't been implemented yet.

**Maven issues**

* Right-click `pom.xml`
* Select **Reload Project**

---

## Section 8: Submission Checklist & Grading

### Submission Checklist
* [ ] **Package Names**: All `.java` files include `package ecommerce;` (or `package ecommerce.exceptions;`).
* [ ] **Exception State**: `InvalidOrderLineException` correctly stores the `orderId`.
* [ ] **Partial Success**: The importer logs a warning and continues if an individual line is invalid.
* [ ] **Iterator Use**: Fraud removal uses an explicit `Iterator` to avoid errors.
* [ ] **Main Application**: Running `Main.java` produces the correct "Final Demand Report".
* [ ] **ZIP Structure**: Your ZIP contains only the `src/` folder and `pom.xml`.

### Grading Criteria

**Correctness (40 pts)**
* Code must pass the hidden autograder tests on Gradescope.
* Successful fulfillment must respect stock levels in `InventoryManager`.

**Exception Handling (20 pts)**
* Proper preservation of the root cause in `FileProcessingException` (Exception Chaining).
* Custom exceptions correctly extend `RuntimeException`.

**Collection Selection (20 pts)**
* Proper use of `Queue` for FIFO processing in `OrderProcessor`.
* Use of `Map` with null-safe methods (like `getOrDefault`) in `InventoryManager`.

**Stream Efficiency (20 pts)**
* Use of appropriate Stream API operations (`distinct`, `sorted`, `groupingBy`) for analytics.
* Analytics must reflect "Total Demand" (all validly imported orders) regardless of fulfillment status.
