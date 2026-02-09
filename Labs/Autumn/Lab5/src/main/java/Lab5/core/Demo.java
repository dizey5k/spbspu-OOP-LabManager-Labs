package Lab5.core;


import java.util.*;

public class Demo {

    public static void demonstrateAverage(List<Integer> numbers) {
        double average = StreamOperations.averageOfIntegers(numbers);
        System.out.println("Numbers: " + numbers);
        System.out.printf("Average: %.2f\n", average);
    }

    public static void demonstrateUpperCaseWithPrefix(List<String> strings) {
        List<String> result = StreamOperations.toUpperCaseWithPrefix(strings);
        System.out.println("Original strings: " + strings);
        System.out.println("Transformed: " + result);
    }

    public static void demonstrateUniqueSquares(List<Integer> numbers) {
        List<Integer> result = StreamOperations.squaresOfUniqueElements(numbers);
        System.out.println("Numbers: " + numbers);
        System.out.println("Unique squares: " + result);
    }

    public static void demonstrateFilterAndSortByLetter(Collection<String> strings, char letter) {
        List<String> result = StreamOperations.filterAndSortByLetter(strings, letter);
        System.out.println("Original strings: " + strings);
        System.out.println("Filtered by '" + letter + "': " + result);
    }

    public static void demonstrateLastElement(Collection<String> elements) {
        try {
            String lastElement = StreamOperations.getLastElementOrThrow(elements);
            System.out.println("Collection: " + elements);
            System.out.println("Last element: " + lastElement);
        } catch (NoSuchElementException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void demonstrateSumEvenNumbers(int[] numbers) {
        int sum = StreamOperations.sumOfEvenNumbers(numbers);
        System.out.println("Numbers: " + Arrays.toString(numbers));
        System.out.println("Sum of even numbers: " + sum);
    }

    public static void demonstrateStringToMap(List<String> strings) {
        Map<Character, String> result = StreamOperations.stringsToMap(strings);
        System.out.println("Strings: " + strings);
        System.out.println("Map (first char -> rest): " + result);
    }

    public static void demonstrateAllOperations() {
        System.out.println("\n=== Demonstration of All Operations ===");

        List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 5, 2, 3);
        List<String> strings1 = Arrays.asList("apple", "banana", "cherry");
        int[] array1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Collection<String> strings2 = Arrays.asList("apple", "banana", "apricot", "cherry", "blueberry");
        List<String> strings3 = Arrays.asList("apple", "banana", "avocado", "apricot");

        System.out.println("1. Average of integers:");
        demonstrateAverage(numbers1);

        System.out.println("\n2. Uppercase with prefix:");
        demonstrateUpperCaseWithPrefix(strings1);

        System.out.println("\n3. Squares of unique elements:");
        demonstrateUniqueSquares(numbers1);

        System.out.println("\n4. Filter and sort by letter 'a':");
        demonstrateFilterAndSortByLetter(strings2, 'a');

        System.out.println("\n5. Last element:");
        demonstrateLastElement(strings1);

        System.out.println("\n6. Sum of even numbers:");
        demonstrateSumEvenNumbers(array1);

        System.out.println("\n7. Strings to Map:");
        demonstrateStringToMap(strings3);
    }
}