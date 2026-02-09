package Lab2.AnnotadedClass;

import Lab2.Repeat.Repeat;

public class AnnotatedClass {

    // public methods
    @Repeat(2)
    public void publicMethod1() {
        System.out.println("Called method 2 no params");
    }

    @Repeat(1)
    public void publicMethod2(String message) {
        System.out.println("Called method 2 with param: " + message);
    }

    public void publicMethodWithoutAnnotation() {
        System.out.println("Should not be called (no annotation");
    }

    // protected methods
    @Repeat(3)
    protected void protectedMethod1(int number) {
        System.out.println("Called protected method 2 with num: " + number);
    }

    @Repeat(2)
    protected void protectedMethod2(String text, double value) {
        System.out.println("Called protected method 2: text='" + text + "', value=" + value);
    }

    protected void protectedMethodWithoutAnnotation() {
        System.out.println("Should not be called protected (no annotation");
    }

    // private methods
    @Repeat(4)
    private void privateMethod1() {
        System.out.println("Called private method 1 no params");
    }

    @Repeat(1)
    private void privateMethod2(boolean flag, int count) {
        System.out.println("Called private method 2: flag=" + flag + ", count=" + count);
    }

    @Repeat(2)
    private String privateMethod3(String name) {
        String result = "Hi, " + name + "! from private";
        System.out.println(result);
        return result;
    }

    private void privateMethodWithoutAnnotation() {
        System.out.println("Should be called private (no annotation)");
    }

    // private method with diff params
    @Repeat(2)
    private void complexPrivateMethod(int a, String b, boolean c, double d) {
        System.out.println("Complex private: " +
                a + ", '" + b + "', " + c + ", " + d);
    }
}