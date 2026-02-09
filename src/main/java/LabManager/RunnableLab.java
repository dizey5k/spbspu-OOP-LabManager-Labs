package LabManager;

public interface RunnableLab {
    void run();
    LabInfo getLabInfo();

    default String getSemester() {
        return "Unknown";
    }
}
