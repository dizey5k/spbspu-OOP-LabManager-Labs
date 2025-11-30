package Lab4;

import Lab4.core.Translator;
import Lab4.exceptions.FileReadException;
import Lab4.exceptions.InvalidFileFormatException;
import LabManager.LabInfo;
import LabManager.RunnableLab;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Lab4 implements RunnableLab {
    private final Scanner scanner;
    private final LabInfo labInfo;
    private final Translator translator;

    public Lab4() {
        this.scanner = new Scanner(System.in);
        this.labInfo = new LabInfo(4, "Translator", "Program that translates text using a dictionary");
        this.translator = new Translator();
    }

    @Override
    public void run() {
        System.out.println("=== Lab 4: Translator ===");
        System.out.println("Program for translating text using a dictionary");

        boolean running = true;
        while (running) {
            showMainMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    loadDictionary();
                    break;
                case 2:
                    translateFromFile();
                    break;
                case 3:
                    translateTextInput();
                    break;
                case 4:
                    showDictionaryInfo();
                    break;
                case 5:
                    showTranslationRules();
                    break;
                case 6:
                    demonstrateExample();
                    break;
                case 0:
                    running = false;
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Incorrect choice. Please try again.");
            }
        }
    }

    @Override
    public LabInfo getLabInfo() {
        return labInfo;
    }

    private void showMainMenu() {
        System.out.println("\n--- Translator Menu ---");
        System.out.println("1. Load dictionary");
        System.out.println("2. Translate from file");
        System.out.println("3. Translate text input");
        System.out.println("4. Show dictionary info");
        System.out.println("5. Show translation rules");
        System.out.println("6. Demonstrate with example");
        System.out.println("0. Back to main menu");
    }

    private int getChoice() {
        System.out.print("Choose option: ");
        String input = scanner.nextLine().trim();

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void loadDictionary() {
        System.out.print("Enter dictionary file path: ");
        String filePath = scanner.nextLine().trim();

        if (filePath.isEmpty()) {
            System.out.println("File path cannot be empty.");
            waitForEnter();
            return;
        }

        try {
            translator.loadDictionary(filePath);
            System.out.println("Dictionary loaded successfully!");
        } catch (FileReadException e) {
            System.out.println("File read error: " + e.getMessage());
        } catch (InvalidFileFormatException e) {
            System.out.println("Invalid dictionary format: " + e.getMessage());
        }

        waitForEnter();
    }

    private void translateFromFile() {
        if (!translator.isDictionaryLoaded()) {
            System.out.println("Error: Dictionary is not loaded. Please load dictionary first.");
            waitForEnter();
            return;
        }

        System.out.print("Enter file path to translate: ");
        String filePath = scanner.nextLine().trim();

        if (filePath.isEmpty()) {
            System.out.println("File path cannot be empty.");
            waitForEnter();
            return;
        }

        try {
            System.out.println("\nTranslating...");
            String translation = translator.translateFile(filePath);

            System.out.println("\n=== Translation Result ===");
            System.out.println(translation);

        } catch (FileReadException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        waitForEnter();
    }

    private void translateTextInput() {
        if (!translator.isDictionaryLoaded()) {
            System.out.println("Error: Dictionary is not loaded. Please load dictionary first.");
            waitForEnter();
            return;
        }

        System.out.print("Enter text to translate: ");
        String text = scanner.nextLine().trim();

        if (text.isEmpty()) {
            System.out.println("Text cannot be empty.");
            waitForEnter();
            return;
        }

        System.out.println("\nTranslating...");
        String translation = translator.translateText(text);

        System.out.println("\n=== Translation Result ===");
        System.out.println(translation);

        waitForEnter();
    }

    private void showDictionaryInfo() {
        System.out.println("\n=== Dictionary Information ===");

        if (translator.isDictionaryLoaded()) {
            System.out.println("Dictionary is loaded");
            System.out.println("Entries count: " + translator.getDictionarySize());
        } else {
            System.out.println("Dictionary is not loaded");
            System.out.println("Use option 1 to load a dictionary file");
        }

        waitForEnter();
    }

    private void showTranslationRules() {
        System.out.println("\n=== Translation Rules ===");
        System.out.println("1. Case insensitive - letter case is ignored");
        System.out.println("2. Unknown words - if word not found, shown without translation");
        System.out.println("3. Longest match - selects variant with maximum left part length");
        System.out.println("4. Dictionary format - word/expression | translation");

        System.out.println("\nExample dictionary format:");
        System.out.println("   look | smotret'");
        System.out.println("   look forward to | prismatrivat'");
        System.out.println("   good morning | horoshego dnya");

        waitForEnter();
    }

    private void demonstrateExample() {
        System.out.println("\n=== Demonstration ===");

        Map<String, String> demoDict = new HashMap<>();
        demoDict.put("hello", "privet");
        demoDict.put("world", "mir");
        demoDict.put("cat", "kot");
        demoDict.put("look", "smotret'");
        demoDict.put("look forward to", "prismatrivat'");
        demoDict.put("good morning", "horoshego dnya");

        System.out.println("Demo dictionary created with " + demoDict.size() + " entries");

        String demoText = "Hello world! I look forward to meeting you. Good morning! Unknown word: elephant";

        System.out.println("\nOriginal text:");
        System.out.println(demoText);

        System.out.println("\nSimulating translation...");

        System.out.println("\nTranslated text:");
        System.out.println(demoText);

        waitForEnter();
    }

    private void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}