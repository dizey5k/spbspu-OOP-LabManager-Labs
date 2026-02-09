package Lab1.MovementStrategies;

public class HorseStrategy implements MovementStrategy {
    @Override
    public void move (String from, String to) {
        System.out.println("Hero on hors moving from " + from + " to " + to);
        System.out.println("Time in travel: 10 min");
        System.out.println("Energy taken: 5 points");
    }

    @Override
    public String getDisplayName() {
        return "Riding on horse";
    }
}
