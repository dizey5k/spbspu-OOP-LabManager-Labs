package Lab2.AnnotadedClass;

import Lab2.Repeat.Repeat;

import java.util.List;
import java.util.Map;

public class AdvancedAnnotatedClass {

    public static class User {
        private final String name;
        private final int age;

        public User() {
            this.name = "default_user";
            this.age = 0;
        }

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{name='" + name + "', age=" + age + "}";
        }
    }

    public static class Order {
        private final User user;
        private final double amount;

        public Order(User user, double amount) {
            this.user = user;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "Order{user=" + user + ", amount=" + amount + "}";
        }
    }

    @Repeat(2)
    private void processUser(User user) {
        System.out.println("Processing user: " + user);
    }

    @Repeat(1)
    private void createOrder(User user, double amount) {
        Order order = new Order(user, amount);
        System.out.println("Created order: " + order);
    }

    @Repeat(3)
    private void processArray(int[] numbers) {
        System.out.print("Array processing: ");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    @Repeat(2)
    private String complexMethod(String title, User user, int count, boolean active) {
        String result = String.format("Title: %s, User: %s, Count: %d, Active: %s",
                title, user, count, active);
        System.out.println("Complex method: " + result);
        return result;
    }

    @Repeat(1)
    private void riskyMethod(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }
        System.out.println("Safe processing: " + input);
    }

    @Repeat(1)
    private void interfaceMethod(List<String> items, Map<String, Integer> mapping) {
        System.out.println("Items: " + items + ", Mapping: " + mapping);
    }

    @Repeat(2)
    private void allPrimitives(byte b, short s, int i, long l, float f, double d, boolean bool, char c) {
        System.out.printf("Primitives: byte=%d, short=%d, int=%d, long=%d, float=%.2f, double=%.2f, boolean=%s, char=%c%n",
                b, s, i, l, f, d, bool, c);
    }

    @Repeat(5)
    private static void staticMethod() {
        System.out.println("Static method called");
    }

    @Repeat(1)
    private void varargsMethod(String... strings) {
        System.out.print("Varargs: ");
        for (String s : strings) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    @Repeat(1)
    private void recursiveTypeMethod(User[] users, Order[][] orders) {
        System.out.println("Users array length: " + users.length);
        System.out.println("Orders 2D array: " + orders.length + "x" + (orders.length > 0 ? orders[0].length : 0));
    }
}
