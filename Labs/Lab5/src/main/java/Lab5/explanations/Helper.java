package Lab5.explanations;


import java.util.Arrays;
import java.util.List;

public class Helper {

    public static void showLambdaToAnonymousClass() {
        System.out.println("\n=== lambda to anon class ===");

        List<String> sample = Arrays.asList("hello", "world", "java");

        System.out.println("\n1. with lambda:");
        List<String> lambdaResult = LambdaAndStream.lambdaExample(sample);
        System.out.println("   res: " + lambdaResult);

        System.out.println("\n2. with anon class:");
        List<String> anonymousResult = LambdaAndStream.anonymousClassExample(sample);
        System.out.println("   result: " + anonymousResult);
    }

    public static void showTerminalOperators() {
        LambdaAndStream.demonstrateTerminalOperations();
    }

    public static void showMethodReferences() {
        LambdaAndStream.demonstrateMethodReferences();
    }

    public static void showAllExplanations() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("all explanations");
        System.out.println("=".repeat(50));

        showLambdaToAnonymousClass();
        showTerminalOperators();
        showMethodReferences();

        System.out.println("\n" + "=".repeat(50));
        System.out.println("explanations demo ended");
        System.out.println("=".repeat(50));
    }
}