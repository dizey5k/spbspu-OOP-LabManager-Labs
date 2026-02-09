package Lab1.MovementStrategies;

public class FlyStrategy implements MovementStrategy {
    @Override
    public void move (String from, String to) {
        System.out.println("Hero is flying from " + from + " to " + to);
        System.out.println("Time in travel: 3 min");
        System.out.println("Energy taken: 15 points");
    }

    @Override
    public String getDisplayName() {
        return "Flying";
    }
}
