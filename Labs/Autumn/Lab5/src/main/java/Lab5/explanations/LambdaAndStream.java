package Lab5.explanations;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LambdaAndStream {

    // ===== Demo lambda to anon class =====

    public static List<String> lambdaExample(List<String> strings) {
        return strings.stream()
                .map(s -> "_new_" + s.toUpperCase())
                .collect(Collectors.toList());
    }

    public static List<String> anonymousClassExample(List<String> strings) {
        return strings.stream()
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) {
                        return "_new_" + s.toUpperCase();
                    }
                })
                .collect(Collectors.toList());
    }

    // ===== demo lambdas =====

    public static void demonstrateLambdaVariations() {
        System.out.println("\n=== diff lambda types ===");

        // no params
        Runnable runnable = () -> System.out.println("Hello from lambda!");
        runnable.run();

        // 1 param
        Consumer<String> consumer1 = (String s) -> System.out.println(s);
        Consumer<String> consumer2 = s -> System.out.println(s); // no type

        // more params
        Comparator<Integer> comparator = (a, b) -> a - b;

        // not single string lambda
        Function<String, String> function = s -> {
            String trimmed = s.trim();
            return trimmed.toUpperCase();
        };

        System.out.println("all done!");
    }

    // ===== terminal operators =====

    public static void demonstrateTerminalOperations() {
        System.out.println("\n=== terminal operations ===");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // terminal operators end stream
        System.out.println("1. collect(): " +
                numbers.stream().toList());

        System.out.println("2. forEach():");
        numbers.stream().forEach(n -> System.out.print(n + " "));
        System.out.println();

        System.out.println("3. count(): " +
                numbers.stream().count());

        System.out.println("4. reduce(): " +
                numbers.stream().reduce(0, Integer::sum));

        System.out.println("5. anyMatch(): " +
                numbers.stream().anyMatch(n -> n > 3));

        // reuse of stream will cause exception
        try {
            System.out.println("\nreusing stream:");
            var stream = numbers.stream();
            stream.forEach(System.out::print);
            stream.count();  // IllegalStateException
        } catch (IllegalStateException e) {
            System.out.println("\nErr: stream already has been called");
        }
    }


    // ===== ref to methods =====

    public static void demonstrateMethodReferences() {
        System.out.println("\n=== ref to methods ===");

        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

        System.out.println("\n1. ref to static method:");
        // lambda: s -> Integer.parseInt(s)
        // ref to method: Integer::parseInt
        List<String> numbers = Arrays.asList("1", "2", "3");
        List<Integer> ints = numbers.stream()
                .map(Integer::parseInt)
                .toList();
        System.out.println("num: " + ints);

        System.out.println("\n2.ref to method example of object:");
        // lambda: s -> System.out.println(s)
        // ref to link: System.out::println
        names.forEach(System.out::println);

        System.out.println("\n3. ref to method exmaple (no obj):");
        // lambda: s -> s.toUpperCase()
        // ref to method: String::toUpperCase
        List<String> upper = names.stream()
                .map(String::toUpperCase)
                .toList();
        System.out.println("upper reg: " + upper);

        System.out.println("\n4. ref to construct:");
        // lambda: s -> new ArrayList<>(s)
        // ref to method: ArrayList::new
        List<String> newList = names.stream()
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("new list: " + newList);
    }
}