# Lab 2: E-Commerce Bulk Order Processor

## Objective

This lab focuses on building a robust data processing pipeline using the Java Collections Framework and professional error-handling patterns. You will apply the **"Partial Success" pattern** to ensure that individual data errors do not crash an entire batch process.

By the end of this lab, you will be able to:

* Implement custom, state-bearing unchecked exceptions.
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
* Store a private `orderId`.
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

This class is responsible for reading **"dirty" CSV data**.

#### Try-with-resources

* Use this to open a `BufferedReader`.
* Catch `IOException` and rethrow it as `FileProcessingException`.
* Pass the original error as the cause.

#### The Loop

* Read the file line by line.
* Use a narrow try catch inside the loop.
* Catch `InvalidOrderLineException`.

#### Validation

* If a row has missing fields or a negative quantity, throw `InvalidOrderLineException`.

#### Logging

* When an exception is caught inside the loop:

  * Use `log.warn(...)`
  * Allow the loop to continue processing.

---

## Section 4: Part 3 – Collections & Processing

### Step 1: Implement `InventoryManager.java`

#### Storage

* Use `Map<String, Integer>` to track stock.

#### Add Stock

* Use `getOrDefault()` to safely increment stock for new or existing products.

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
map -> distinct -> sorted
```

* Return lowercase emails.
* Ensure uniqueness.
* Return alphabetically sorted list.

#### Product Counts

Use:

* `Collectors.groupingBy`
* `Collectors.counting()`

Return a `Map` of product names and their order counts.

---

## Section 6: Part 5 – Testing & Submission

### Step 1: Run Local Tests

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

### Step 2: Create Submission ZIP

Run this command from your project root:

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
    │   ├── Order.java (Record)
    │   ├── OrderAnalytics.java
    │   ├── OrderImporter.java
    │   └── OrderProcessor.java
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

*  Package Names: All `.java` files include `package ecommerce;` (or `package ecommerce.exceptions;`).
*  Exception State: `InvalidOrderLineException` correctly stores the `orderId`.
*  Partial Success: The importer logs a warning and continues if an individual line is invalid.
*  Iterator Use: Fraud removal uses an explicit `Iterator` to avoid errors.
*  ZIP Structure: Your ZIP contains only the `src/` folder and `pom.xml`.

### Grading Criteria

**Correctness**

* Code must pass the hidden autograder tests on Gradescope.

**Exception Chaining**

* Proper preservation of the root cause in `FileProcessingException`.

**Collection Selection**

* Proper use of `Queue` for FIFO and `Map` for inventory.

**Stream Efficiency**

* Use of appropriate Stream API operations for analytics.
