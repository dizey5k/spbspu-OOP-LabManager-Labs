package Lab5.ui;

import Lab5.core.Demo;
import Lab5.utils.InputProcessor;

import java.util.Scanner;

public class Lab5Menu {
    private final Scanner scanner;
    private final InputProcessor inputProcessor;
    private final ExplanationsMenu explanationsMenu;
    private boolean running;

    public Lab5Menu(Scanner scanner) {
        this.scanner = scanner;
        this.inputProcessor = new InputProcessor(scanner);
        this.explanationsMenu = new ExplanationsMenu(scanner);
        this.running = true;
    }

    public void showMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("=== STREAM API OPERATIONS ===");
        System.out.println("=".repeat(50));
        System.out.println("1. PRACTICAL EXERCISES");
        System.out.println("2. EXPLANATIONS");
        System.out.println("0. Back to Lab Manager");
        System.out.println("=".repeat(50));
    }

    public void handleChoice(int choice) {
        switch (choice) {
            case 1 -> showExercisesMenu();
            case 2 -> showExplanationsMenu();
            case 0 -> running = false;
            default -> System.out.println("Incorrect choice. Please try again.");
        }
    }

    private void showExercisesMenu() {
        boolean exercisesRunning = true;

        while (exercisesRunning) {
            System.out.println("\n--- Practical Exercises ---");
            System.out.println("1. Average of integers list");
            System.out.println("2. Strings to uppercase with prefix");
            System.out.println("3. Squares of unique elements");
            System.out.println("4. Filter and sort strings by first letter");
            System.out.println("5. Get last element of collection");
            System.out.println("6. Sum of even numbers in array");
            System.out.println("7. Convert strings to Map (first char -> rest)");
            System.out.println("8. Demonstrate all operations");
            System.out.println("0. Back to main menu");

            System.out.print("Choose exercise: ");
            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1 -> handleAverage();
                    case 2 -> handleUpperCaseWithPrefix();
                    case 3 -> handleUniqueSquares();
                    case 4 -> handleFilterAndSortByLetter();
                    case 5 -> handleLastElement();
                    case 6 -> handleSumEvenNumbers();
                    case 7 -> handleStringToMap();
                    case 8 -> handleAllOperations();
                    case 0 -> exercisesRunning = false;
                    default -> System.out.println("Incorrect choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private void showExplanationsMenu() {
        explanationsMenu.run();
    }

    private void handleAverage() {
        System.out.println("\n=== Exercise 1: Average of Integers ===");
        try {
            var numbers = inputProcessor.readIntegers();
            Demo.demonstrateAverage(numbers);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter integers only.");
        }
        waitForEnter();
    }

    private void handleUpperCaseWithPrefix() {
        System.out.println("\n=== Exercise 2: Strings to Uppercase with Prefix ===");
        var strings = inputProcessor.readStrings();
        Demo.demonstrateUpperCaseWithPrefix(strings);
        waitForEnter();
    }

    private void handleUniqueSquares() {
        System.out.println("\n=== Exercise 3: Squares of Unique Elements ===");
        try {
            var numbers = inputProcessor.readIntegers();
            Demo.demonstrateUniqueSquares(numbers);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter integers only.");
        }
        waitForEnter();
    }

    private void handleFilterAndSortByLetter() {
        System.out.println("\n=== Exercise 4: Filter and Sort Strings by First Letter ===");
        var strings = inputProcessor.readStrings();

        try {
            char letter = inputProcessor.readChar();
            Demo.demonstrateFilterAndSortByLetter(strings, letter);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        waitForEnter();
    }

    private void handleLastElement() {
        System.out.println("\n=== Exercise 5: Get Last Element of Collection ===");
        var elements = inputProcessor.readStrings();
        Demo.demonstrateLastElement(elements);
        waitForEnter();
    }

    private void handleSumEvenNumbers() {
        System.out.println("\n=== Exercise 6: Sum of Even Numbers ===");
        try {
            var numbers = inputProcessor.readIntArray();
            Demo.demonstrateSumEvenNumbers(numbers);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter integers only.");
        }
        waitForEnter();
    }

    private void handleStringToMap() {
        System.out.println("\n=== Exercise 7: Convert Strings to Map ===");
        var strings = inputProcessor.readStrings();
        Demo.demonstrateStringToMap(strings);
        waitForEnter();
    }

    private void handleAllOperations() {
        System.out.println("\n=== Exercise 8: Demonstrate All Operations ===");
        Demo.demonstrateAllOperations();
        waitForEnter();
    }

    private void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public boolean isRunning() {
        return running;
    }
}