package LabManager;

public record LabInfo(
        int number,
        String name,
        String description,
        String semester
) {
    @Override
    public String toString() {
        return String.format("[%s] %d. %s - %s", semester, number, name, description);
    }
}
