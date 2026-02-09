package Lab5.core;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamOperations {

    public static double averageOfIntegers(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    public static List<String> toUpperCaseWithPrefix(List<String> strings) {
        return strings.stream()
                .map(s -> "_new_" + s.toUpperCase())
                .collect(Collectors.toList());
    }

    public static List<Integer> squaresOfUniqueElements(List<Integer> numbers) {
        Map<Integer, Long> frequency = numbers.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return numbers.stream()
                .filter(n -> frequency.get(n) == 1)
                .map(n -> n * n)
                .collect(Collectors.toList());
    }

    public static List<String> filterAndSortByLetter(Collection<String> strings, char letter) {
        return strings.stream()
                .filter(s -> !s.isEmpty() && Character.toLowerCase(s.charAt(0)) == Character.toLowerCase(letter))
                .sorted()
                .collect(Collectors.toList());
    }

    public static <T> T getLastElementOrThrow(Collection<T> collection) {
        return collection.stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new NoSuchElementException("Collection is empty"));
    }

    public static int sumOfEvenNumbers(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(n -> n % 2 == 0)
                .sum();
    }

    public static Map<Character, String> stringsToMap(List<String> strings) {
        return strings.stream()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toMap(
                        s -> s.charAt(0),
                        s -> s.substring(1),
                        (existing, replacement) -> existing
                ));
    }
}