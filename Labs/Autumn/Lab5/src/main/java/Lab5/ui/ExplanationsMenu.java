package Lab5.ui;

import Lab5.explanations.Helper;

import java.util.Scanner;

public class ExplanationsMenu {
    private final Scanner scanner;
    private boolean running;

    public ExplanationsMenu(Scanner scanner) {
        this.scanner = scanner;
        this.running = true;
    }

    public void showMenu() {
        System.out.println("\n--- Stream API Explanations Menu ---");
        System.out.println("1. Lambda to anonymous class demo");
        System.out.println("2. Terminal operators");
        System.out.println("3. Method references demonstration");
        System.out.println("4. All explanations (complete demo)");
        System.out.println("0. Back to main menu");
    }

    public void handleChoice(int choice) {
        switch (choice) {
            case 1 -> showLambdaToAnonymous();
            case 2 -> showTerminalOperators();
            case 3 -> showMethodReferences();
            case 4 -> showAllExplanations();
            case 0 -> running = false;
            default -> System.out.println("Incorrect choice. Please try again.");
        }

        if (choice != 0) {
            waitForEnter();
        }
    }

    private void showLambdaToAnonymous() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("DEMONSTRATION: LAMBDA TO ANONYMOUS CLASS");
        System.out.println("=".repeat(50));
        Helper.showLambdaToAnonymousClass();
    }

    private void showTerminalOperators() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("EXPLANATION: TERMINAL OPERATORS");
        System.out.println("=".repeat(50));
        Helper.showTerminalOperators();
    }

    private void showMethodReferences() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("DEMONSTRATION: METHOD REFERENCES");
        System.out.println("=".repeat(50));
        Helper.showMethodReferences();
    }

    private void showAllExplanations() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("COMPLETE EXPLANATIONS DEMONSTRATION");
        System.out.println("=".repeat(50));
        Helper.showAllExplanations();
    }

    private void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public boolean isRunning() {
        return running;
    }

    public void run() {
        while (running) {
            showMenu();
            int choice = getChoice();
            handleChoice(choice);
        }
        System.out.println("Returning to Stream API Operations menu...");
    }

    private int getChoice() {
        System.out.print("Choose explanation: ");
        String input = scanner.nextLine().trim();

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}