package Lab1.MovementStrategies;

public class WalkStrategy implements MovementStrategy {
    @Override
    public void move(String from, String to) {
        System.out.println("Hero walks from " + from + " to " + to);
        System.out.println("Time in travel: 30 min");
        System.out.println("Energy taken: 10 points");
    }

    @Override
    public String getDisplayName() {
        return "Walking";
    }
}
