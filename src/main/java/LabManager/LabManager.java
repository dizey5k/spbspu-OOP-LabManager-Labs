package LabManager;

import java.util.*;
import java.util.ServiceLoader;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LabManager {
    private final Scanner scanner;
    private boolean running;
    private final Map<Integer, RunnableLab> labs;

    public LabManager() {
        this.scanner = new Scanner(System.in);
        this.running = true;
        this.labs = new HashMap<>();
        loadLabs();
    }

    private void loadLabs() {
        System.out.println("=== Lab Manager Initialization ===");
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        System.out.println("Classpath: " + System.getProperty("java.class.path"));

        checkResourceAvailability();

        boolean serviceLoaderWorked = tryServiceLoader();

        if (!serviceLoaderWorked) {
            useManualRegistration();
        }

        System.out.println("Total labs loaded: " + labs.size());
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
                    while ((line = reader.readLine()) != null) {
                        System.out.println("   Content: " + line.trim());
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

    private boolean tryServiceLoader() {
        System.out.println("\n--- Trying ServiceLoader ---");
        try {
            ServiceLoader<RunnableLab> loader = ServiceLoader.load(RunnableLab.class);
            int count = 0;

            for (RunnableLab lab : loader) {
                LabInfo info = lab.getLabInfo();
                labs.put(info.number(), lab);
                System.out.println("ServiceLoader loaded: " + info.name());
                count++;
            }

            if (count > 0) {
                System.out.println("ServiceLoader successfully loaded " + count + " labs");
                return true;
            } else {
                System.out.println("ServiceLoader found no labs");
                return false;
            }
        } catch (Exception e) {
            System.out.println("ServiceLoader failed: " + e.getMessage());
            return false;
        }
    }

    private void useManualRegistration() {
        System.out.println("\n--- Using manual registration ---");

        String[] labClasses = {
                "Lab1.Lab1"
        };

        for (String className : labClasses) {
            try {
                Class<?> clazz = Class.forName(className);
                RunnableLab lab = (RunnableLab) clazz.getDeclaredConstructor().newInstance();
                LabInfo info = lab.getLabInfo();
                labs.put(info.number(), lab);
                System.out.println("Manually registered: " + info.name());
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found: " + className);
            } catch (Exception e) {
                System.out.println("Error registering " + className + ": " + e.getMessage());
            }
        }
    }

    public void start() {
        while (running) {
            showMenu();
            handleChoice();
        }
        scanner.close();
        System.out.println("Lab Manager stopped.");
    }

    private void showMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("=== LAB MANAGER ===");
        System.out.println("=".repeat(50));

        if (labs.isEmpty()) {
            System.out.println("No laboratory works available");
        } else {
            labs.values().stream()
                    .map(RunnableLab::getLabInfo)
                    .sorted(Comparator.comparingInt(LabInfo::number))
                    .forEach(System.out::println);
        }

        System.out.println("0. Exit");
        System.out.print("Choose lab: ");
    }

    private void handleChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                running = false;
                System.out.println("Exiting...");
            } else if (labs.containsKey(choice)) {
                runLab(choice);
            } else {
                System.out.println("Incorrect choice! Available: " + labs.keySet());
            }
        } catch (Exception e) {
            System.out.println("Input error! Please enter a number.");
            scanner.nextLine();
        }
    }

    private void runLab(int labNumber) {
        RunnableLab lab = labs.get(labNumber);
        LabInfo info = lab.getLabInfo();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("STARTING: " + info.name().toUpperCase());
        System.out.println("DESCRIPTION: " + info.description());
        System.out.println("=".repeat(60));

        try {
            lab.run();
        } catch (Exception e) {
            System.out.println("Error running lab: " + e.getMessage());
        }

        waitForReturn();
    }

    private void waitForReturn() {
        System.out.println("\nPress Enter to return to lab manager...");
        scanner.nextLine();
    }

    public static void main(String[] args) {
        new LabManager().start();
    }
}