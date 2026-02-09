package Lab1.MovementStrategies;

public class TeleportStrategy implements MovementStrategy{
    @Override
    public void move (String from, String to) {
        System.out.println("Hero is teleporting from " + from + " to " + to);
        System.out.println("Time in travel: momentary");
        System.out.println("Energy taken: 25 points");
    }

    @Override
    public String getDisplayName() {
        return "Teleporting";
    }
}
