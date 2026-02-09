package LabManager;

import java.util.*;
import java.util.ServiceLoader;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LabManager {
    private final Scanner scanner;
    private boolean running;
    private final Map<String, Map<Integer, RunnableLab>> labsBySemester;
    private String currentSemester;

    public LabManager() {
        this.scanner = new Scanner(System.in);
        this.running = true;
        this.labsBySemester = new HashMap<>();
        this.currentSemester = null;
        loadLabs();
    }

    private void loadLabs() {
        System.out.println("=== Lab Manager Initialization ===");
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        System.out.println("Classpath: " + System.getProperty("java.class.path"));

        checkResourceAvailability();

        loadLabsWithServiceLoader();

        System.out.println("\n=== Summary ===");
        for (String semester : labsBySemester.keySet()) {
            System.out.printf("Semester '%s': %d labs loaded%n",
                    semester, labsBySemester.get(semester).size());
        }
    }

    private void checkResourceAvailability() {
        System.out.println("\n--- Checking resources ---");

        String[] resourcesToCheck = {
                "META-INF/services/LabManager.RunnableLab",
        };

        for (String resource : resourcesToCheck) {
            URL url = getClass().getClassLoader().getResource(resource);
            if (url != null) {
                System.out.println("Found: " + resource);
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                    String line;
                    System.out.println("   Content:");
                    while ((line = reader.readLine()) != null) {
                        if (!line.trim().isEmpty()) {
                            System.out.println("   - " + line.trim());
                        }
                    }
                    reader.close();
                } catch (Exception e) {
                    System.out.println("   Error reading: " + e.getMessage());
                }
            } else {
                System.out.println("Not found: " + resource);
            }
        }
    }

    private void loadLabsWithServiceLoader() {
        System.out.println("\n--- Loading labs with ServiceLoader ---");

        try {
            ServiceLoader<RunnableLab> loader = ServiceLoader.load(RunnableLab.class);
            int totalLoaded = 0;

            for (RunnableLab lab : loader) {
                LabInfo info = lab.getLabInfo();
                String semester = info.semester();

                Map<Integer, RunnableLab> semesterLabs = labsBySemester
                        .computeIfAbsent(semester, k -> new HashMap<>());

                // Проверяем, нет ли уже лабораторной с таким номером
                if (semesterLabs.containsKey(info.number())) {
                    System.out.printf("Warning: Duplicate lab number %d in semester '%s'%n",
                            info.number(), semester);
                }

                semesterLabs.put(info.number(), lab);
                System.out.printf("Loaded: %s (Semester: %s)%n", info.name(), semester);
                totalLoaded++;
            }

            System.out.println("Total labs loaded: " + totalLoaded);

            if (totalLoaded == 0) {
                System.out.println("No labs found via ServiceLoader, trying manual fallback...");
                manualFallback();
            }

        } catch (Exception e) {
            System.out.println("ServiceLoader failed: " + e.getMessage());
            System.out.println("Trying manual fallback...");
            manualFallback();
        }
    }

    private void manualFallback() {
        System.out.println("\n--- Manual fallback registration ---");

        String[][] labClassesBySemester = {
                {"Autumn", "Autumn.Lab1.Lab1", "Autumn.Lab2.Lab2", "Autumn.Lab3.Lab3"},
                {"Spring", "Spring.Lab1.Lab1", "Spring.Lab2.Lab2"}
        };

        int totalRegistered = 0;

        for (String[] semesterLabs : labClassesBySemester) {
            String semester = semesterLabs[0];
            Map<Integer, RunnableLab> semesterMap = labsBySemester
                    .computeIfAbsent(semester, k -> new HashMap<>());

            for (int i = 1; i < semesterLabs.length; i++) {
                String className = semesterLabs[i];
                try {
                    Class<?> clazz = Class.forName(className);
                    RunnableLab lab = (RunnableLab) clazz.getDeclaredConstructor().newInstance();
                    LabInfo info = lab.getLabInfo();

                    if (!semester.equals(info.semester())) {
                        System.out.printf("Warning: Lab %s reports semester '%s' but placed in '%s'%n",
                                className, info.semester(), semester);
                    }

                    semesterMap.put(info.number(), lab);
                    System.out.printf("Manually registered: %s (Semester: %s)%n",
                            info.name(), info.semester());
                    totalRegistered++;
                } catch (ClassNotFoundException e) {
                    System.out.println("Class not found: " + className);
                } catch (Exception e) {
                    System.out.println("Error registering " + className + ": " + e.getMessage());
                }
            }
        }

        System.out.println("Manually registered total: " + totalRegistered);
    }

    public void start() {
        while (running) {
            if (currentSemester == null) {
                showSemesterMenu();
            } else {
                showLabMenu();
            }
        }
        scanner.close();
        System.out.println("Lab Manager stopped.");
    }

    private void showSemesterMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("=== SEMESTER SELECTION ===");
        System.out.println("=".repeat(50));

        List<String> semesters = new ArrayList<>(labsBySemester.keySet());
        Collections.sort(semesters);

        boolean hasSemesters = !semesters.isEmpty();

        if (hasSemesters) {
            for (int i = 0; i < semesters.size(); i++) {
                String semester = semesters.get(i);
                int labCount = labsBySemester.get(semester).size();
                System.out.printf("%d. %s (%d labs)%n", i + 1, semester, labCount);
            }
        } else {
            System.out.println("No semesters available");
            System.out.println("1. Retry loading labs");
        }

        System.out.println("0. Exit");
        System.out.print("Choose semester: ");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                running = false;
                System.out.println("Exiting...");
            } else if (!hasSemesters && choice == 1) {
                labsBySemester.clear();
                loadLabs();
            } else if (hasSemesters && choice > 0 && choice <= semesters.size()) {
                currentSemester = semesters.get(choice - 1);
                System.out.println("Selected semester: " + currentSemester);
            } else {
                if (hasSemesters) {
                    System.out.println("Invalid choice! Available options: 1-" + semesters.size() + " or 0");
                } else {
                    System.out.println("Invalid choice! Available options: 1 or 0");
                }
            }
        } catch (Exception e) {
            System.out.println("Input error! Please enter a number.");
            scanner.nextLine();
        }
    }

    private void showLabMenu() {
        Map<Integer, RunnableLab> currentLabs = labsBySemester.get(currentSemester);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("=== " + currentSemester.toUpperCase() + " SEMESTER ===");
        System.out.println("=".repeat(50));

        if (currentLabs.isEmpty()) {
            System.out.println("No laboratory works available for this semester");
        } else {
            // Сортируем лабораторные работы по номеру
            currentLabs.values().stream()
                    .map(RunnableLab::getLabInfo)
                    .sorted(Comparator.comparingInt(LabInfo::number))
                    .forEach(System.out::println);
        }

        System.out.println("9. Back to semester selection");
        System.out.println("0. Exit");
        System.out.print("Choose lab: ");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 0) {
                running = false;
                System.out.println("Exiting...");
            } else if (choice == 9) {
                currentSemester = null;
            } else if (currentLabs.containsKey(choice)) {
                runLab(choice);
            } else {
                System.out.println("Incorrect choice! Available labs: " +
                        currentLabs.keySet().stream()
                                .sorted()
                                .map(String::valueOf)
                                .collect(java.util.stream.Collectors.joining(", ")));
            }
        } catch (Exception e) {
            System.out.println("Input error! Please enter a number.");
            scanner.nextLine(); // clear invalid input
        }
    }

    private void runLab(int labNumber) {
        RunnableLab lab = labsBySemester.get(currentSemester).get(labNumber);
        LabInfo info = lab.getLabInfo();

        System.out.println("\n" + "=".repeat(50));
        System.out.println("STARTING: " + info.name().toUpperCase());
        System.out.println("SEMESTER: " + info.semester());
        System.out.println("DESCRIPTION: " + info.description());
        System.out.println("=".repeat(50));

        try {
            lab.run();
        } catch (Exception e) {
            System.out.println("Error running lab: " + e.getMessage());
            e.printStackTrace();
        }

        waitForReturn();
    }

    private void waitForReturn() {
        System.out.println("\nPress Enter to return to lab menu...");
        scanner.nextLine();
    }

    public static void main(String[] args) {
        new LabManager().start();
    }
}