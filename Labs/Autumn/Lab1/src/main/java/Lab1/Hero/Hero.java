package Lab1.Hero;

import Lab1.MovementStrategies.MovementStrategy;
import Lab1.MovementStrategies.WalkStrategy;

public class Hero {
    private final String name;
    private MovementStrategy movementStrategy;

    public Hero(String name) {
        this.name = name;
        this.movementStrategy = new WalkStrategy();
    }

    public Hero(String name, MovementStrategy movementStrategy) {
        this.name = name;
        this.movementStrategy = movementStrategy;
    }

    public void move(String from, String to) {
        System.out.println("\n--- " + name + " starts moving ---");
        movementStrategy.move(from, to);

        System.out.println("--- Movement end ---");
    }

    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;

        System.out.println(name + " changed movement strategy to " + movementStrategy.getDisplayName());
    }

    public String getName() {
        return name;
    }

    public String getMovementStrategy() {
        return movementStrategy.getDisplayName();
    }
}
