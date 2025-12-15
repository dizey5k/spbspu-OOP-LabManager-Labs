package Lab2;

import Lab2.AnnotadedClass.AnnotatedClass;
import Lab2.AnnotadedClass.AdvancedAnnotatedClass;
import Lab2.MethodInvoker.MethodInvoker;
import Lab2.Repeat.Repeat;
import LabManager.LabInfo;
import LabManager.RunnableLab;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Lab2 implements RunnableLab {
    private final Scanner scanner;
    private final LabInfo labInfo;

    public Lab2() {
        this.scanner = new Scanner(System.in);
        this.labInfo = new LabInfo(2, "annotation and reflection",
                "usage of annotation and reflection for methods class");
    }

    @Override
    public void run() {
        System.out.println("=== Lab 2: Annotation and Reflection ===");
        System.out.println("Demonstration of call annotated methods with reflection");

        boolean running = true;

        while (running) {
            showMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    demonstrateBasicInvocation();
                    break;
                case 2:
                    demonstrateWithCustomObject();
                    break;
                case 3:
                    showReflectionInfo();
                    break;
                case 4:
                    demonstrateAdvancedCases();
                    break;
                case 5:
                    demonstrateEdgeCases();
                    break;
                case 6:
                    showAnnotationStatistics();
                    break;
                case 7:
                    performanceTest();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Incorrect choice");
            }
        }
    }

    @Override
    public LabInfo getLabInfo() {
        return labInfo;
    }

    private void showMenu() {
        System.out.println("\n--- Lab 2 menu ---");
        System.out.println("1. Demonstrate basic method invocation");
        System.out.println("2. Create custom object and call methods");
        System.out.println("3. Show reflection information");
        System.out.println("4. Advanced test cases");
        System.out.println("5. Edge cases testing");
        System.out.println("6. Annotation statistics");
        System.out.println("7. Performance test");
        System.out.println("0. Back to main menu");
        System.out.print("Choose action: ");
    }

    private int getChoice() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }

    /**
     * Demo basic call of annotated class
     */
    private void demonstrateBasicInvocation() {
        System.out.println("\n=== Demo of call of annotated methods ===");

        AnnotatedClass annotatedObject = new AnnotatedClass();
        MethodInvoker invoker = new MethodInvoker();

        invoker.invokeAnnotatedMethods(annotatedObject);

        System.out.println("\n=== Demo ended ===");
    }

    /**
     * Demo with user obj
     */
    private void demonstrateWithCustomObject() {
        System.out.println("\n=== Create user obj ===");
        scanner.nextLine();

        System.out.print("Type name for obj: ");
        String objectName = scanner.nextLine();

        Object customObject = createCustomObject(objectName);
        MethodInvoker invoker = new MethodInvoker();

        invoker.invokeAnnotatedMethods(customObject);
    }

    /**
     * Create user obj with annotated methods
     */
    private Object createCustomObject(final String name) {
        return new Object() {
            @Repeat(2)
            private void customPrivateMethod(String msg) {
                System.out.println("[" + name + "] private method: " + msg);
            }

            @Repeat(3)
            protected void customProtectedMethod(int x) {
                System.out.println("[" + name + "] protected with num method: " + x);
            }

            @Repeat(1)
            private String customMethodWithReturn() {
                return "[" + name + "] with return value method";
            }
        };
    }

    /**
     * Shows info about reflection
     */
    private void showReflectionInfo() {
        System.out.println("\n=== Info about reflection ===");

        Class<AnnotatedClass> clazz = AnnotatedClass.class;

        System.out.println("Class: " + clazz.getName());
        System.out.println("Simple name: " + clazz.getSimpleName());
        System.out.println("Modifiers: " + java.lang.reflect.Modifier.toString(clazz.getModifiers()));

        System.out.println("\n--- All class methods ---");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("Method: " + method.getName());
            System.out.println("  Modifier: " + java.lang.reflect.Modifier.toString(method.getModifiers()));
            System.out.println("  Return type: " + method.getReturnType().getSimpleName());
            System.out.println("  Parameters: " + java.util.Arrays.toString(method.getParameterTypes()));

            Repeat annotation = method.getAnnotation(Repeat.class);
            if (annotation != null) {
                System.out.println("  Annotation @Repeat: " + annotation.value() + " calls");
            }
            System.out.println();
        }
    }

    /**
     * Demo with advanced test cases
     */
    private void demonstrateAdvancedCases() {
        System.out.println("\n=== Advanced Test Cases ===");

        AdvancedAnnotatedClass advancedObject = new AdvancedAnnotatedClass();
        MethodInvoker invoker = new MethodInvoker();

        invoker.invokeAnnotatedMethods(advancedObject);

        System.out.println("\n--- Inheritance Test ---");
        Object inheritedObject = createInheritedObject();
        invoker.invokeAnnotatedMethods(inheritedObject);
    }

    /**
     * Creates object with inheritance for testing
     */
    private Object createInheritedObject() {
        return new Object() {
            private final String baseField = "base";

            @Repeat(2)
            private void baseMethod(String param) {
                System.out.println("Base method: " + baseField + ", param: " + param);
            }

            // Inner class
            class InnerClass {
                @Repeat(1)
                private void innerMethod() {
                    System.out.println("Inner class method, baseField: " + baseField);
                }
            }

            {
                // Initializer for inner class
                new InnerClass().innerMethod();
            }
        };
    }

    /**
     * Testing edge cases
     */
    private void demonstrateEdgeCases() {
        System.out.println("\n=== Edge Cases ===");

        Object edgeCaseObject = new Object() {
            // Method with maximum parameters
            @Repeat(1)
            private void manyParams(int a, String b, boolean c, double d, char e,
                                    byte f, short g, long h, float i) {
                System.out.printf("Many params: %d, %s, %s, %.2f, %c, %d, %d, %d, %.2f%n",
                        a, b, c, d, e, f, g, h, i);
            }

            // Method with exception in parameter constructor
            @Repeat(1)
            private void exceptionInConstructor(AdvancedAnnotatedClass.User user) {
                System.out.println("User processed: " + user);
            }

            // Method with possible null parameters
            @Repeat(1)
            private void methodWithPossibleNulls(String str, Integer number) {
                System.out.println("String: " + str + ", Number: " + number);
            }

            // Method with array of custom objects
            @Repeat(1)
            private void customArrayMethod(AdvancedAnnotatedClass.User[] users) {
                System.out.println("Users array length: " + users.length);
            }

            // Method with primitive array
            @Repeat(2)
            private void primitiveArrayMethod(int[] numbers, boolean[] flags) {
                System.out.println("Numbers: " + numbers.length + ", flags: " + flags.length);
            }
        };

        MethodInvoker invoker = new MethodInvoker();
        invoker.invokeAnnotatedMethods(edgeCaseObject);
    }

    /**
     * Shows annotation statistics
     */
    private void showAnnotationStatistics() {
        System.out.println("\n=== Annotation Statistics ===");

        Class<?>[] testClasses = {AnnotatedClass.class, AdvancedAnnotatedClass.class};

        for (Class<?> clazz : testClasses) {
            long annotatedMethods = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(m -> m.isAnnotationPresent(Repeat.class))
                    .count();

            long totalCalls = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(m -> m.isAnnotationPresent(Repeat.class))
                    .mapToInt(m -> m.getAnnotation(Repeat.class).value())
                    .sum();

            System.out.printf("Class: %s%n", clazz.getSimpleName());
            System.out.printf("  Annotated methods: %d%n", annotatedMethods);
            System.out.printf("  Total calls configured: %d%n", totalCalls);
            System.out.printf("  Private methods: %d%n",
                    Arrays.stream(clazz.getDeclaredMethods())
                            .filter(m -> m.isAnnotationPresent(Repeat.class))
                            .filter(m -> java.lang.reflect.Modifier.isPrivate(m.getModifiers()))
                            .count());
            System.out.printf("  Protected methods: %d%n",
                    Arrays.stream(clazz.getDeclaredMethods())
                            .filter(m -> m.isAnnotationPresent(Repeat.class))
                            .filter(m -> java.lang.reflect.Modifier.isProtected(m.getModifiers()))
                            .count());
            System.out.printf("  Public methods: %d%n",
                    Arrays.stream(clazz.getDeclaredMethods())
                            .filter(m -> m.isAnnotationPresent(Repeat.class))
                            .filter(m -> java.lang.reflect.Modifier.isPublic(m.getModifiers()))
                            .count());
        }
    }

    /**
     * Performance testing
     */
    private void performanceTest() {
        System.out.println("\n=== Performance Test ===");

        AnnotatedClass testObject = new AnnotatedClass();
        MethodInvoker invoker = new MethodInvoker();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            invoker.invokeAnnotatedMethods(testObject);
        }

        long endTime = System.currentTimeMillis();
        System.out.printf("Performance test completed in %d ms%n", endTime - startTime);

        System.out.println("\n--- Individual Method Timing ---");
        Method[] methods = AnnotatedClass.class.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Repeat.class)) {
                long methodStart = System.nanoTime();
                try {
                    method.setAccessible(true);
                    Object[] params = Arrays.stream(method.getParameterTypes())
                            .map(invoker::createInstanceRecursive)
                            .toArray();

                    for (int i = 0; i < 100; i++) {
                        method.invoke(testObject, params);
                    }
                } catch (Exception _) {
                }
                long methodEnd = System.nanoTime();
                System.out.printf("Method %s: %d ns per 100 calls%n",
                        method.getName(), (methodEnd - methodStart));
            }
        }
    }

    /**
     * Additional test: method with recursion in parameters
     */
    private void demonstrateRecursiveParameters() {
        System.out.println("\n=== Recursive Parameters Test ===");

        Object recursiveObject = new Object() {
            @Repeat(1)
            private void recursiveMethod(AdvancedAnnotatedClass.User user,
                                         AdvancedAnnotatedClass.Order order) {
                System.out.println("User: " + user + ", Order: " + order);
            }

            @Repeat(2)
            private void complexRecursiveMethod(AdvancedAnnotatedClass.User[] users,
                                                List<AdvancedAnnotatedClass.Order> orders) {
                System.out.println("Users: " + users.length + ", Orders list: " + orders);
            }
        };

        MethodInvoker invoker = new MethodInvoker();
        invoker.invokeAnnotatedMethods(recursiveObject);
    }
}