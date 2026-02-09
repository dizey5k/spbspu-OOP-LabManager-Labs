package Lab5.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputProcessor {
    private final Scanner scanner;

    public InputProcessor(Scanner scanner) {
        this.scanner = scanner;
    }

    public List<Integer> readIntegers() {
        System.out.println("Enter integers separated by spaces:");
        String input = scanner.nextLine().trim();

        return Arrays.stream(input.split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public List<String> readStrings() {
        System.out.println("Enter strings separated by commas:");
        String input = scanner.nextLine().trim();

        return Arrays.stream(input.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public int[] readIntArray() {
        System.out.println("Enter integers separated by spaces:");
        String input = scanner.nextLine().trim();

        return Arrays.stream(input.split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public char readChar() {
        System.out.print("Enter a single character: ");
        String input = scanner.nextLine().trim();

        if (input.length() != 1) {
            throw new IllegalArgumentException("Please enter exactly one character");
        }
        return input.charAt(0);
    }

    public static boolean isValidIntegerInput(String input) {
        try {
            Arrays.stream(input.split("\\s+"))
                    .forEach(Integer::parseInt);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidStringList(String input) {
        return input != null && !input.trim().isEmpty();
    }
}