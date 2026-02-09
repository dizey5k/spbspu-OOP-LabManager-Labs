package Lab3.Animal;

public abstract class Animal implements Comparable<Animal> {
    private final String name;

    public Animal() {
        this.name = getClass().getSimpleName() + "-" + System.identityHashCode(this);
    }

    public Animal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Animal other) {
        return this.name.compareTo(other.name);
    }
}