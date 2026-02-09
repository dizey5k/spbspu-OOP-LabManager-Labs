package Lab1.supervisor;

public enum ProgramState {
    UNKNOWN("UNKNOWN", "Before first start"),
    STOPPING("STOPPING", "Program stopped"),
    RUNNING("RUNNING", "Program working"),
    FATAL_ERROR("FATAL ERROR", "Critical error");

    private final String name;
    private final String description;

    ProgramState(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", name, description);
    }
}