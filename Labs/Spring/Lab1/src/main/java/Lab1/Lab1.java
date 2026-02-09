package Spring.Lab1;

import Lab1.supervisor.Supervisor;
import Lab1.demo.SupervisorDemo;
import LabManager.LabInfo;
import LabManager.RunnableLab;

import java.util.Scanner;

public class Lab1 implements RunnableLab {
    private final LabInfo labInfo;
    private final Scanner scanner;

    public Lab1() {
        this.labInfo = new LabInfo(1, "Supervisor and Abstract Program",
                "Supervisor controls execution of an abstract program running in a separate thread.",
                "Spring");
        this.scanner = new Scanner(System.in);
    }

    @Override
    public LabInfo getLabInfo() {
        return labInfo;
    }

    @Override
    public void run() {
        System.out.println("=== Lab 1: Supervisor and Abstract Program ===");
        System.out.println("Spring Semester");
        System.out.println("\nTask Description:");
        System.out.println(labInfo.description());

        boolean running = true;
        while (running) {
            showMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    runAutoDemo();
                    break;
                case 2:
                    runInteractiveDemo();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }

        System.out.println("Exiting lab work...");
    }

    private void showMenu() {
        System.out.println("\n=== LAB WORK MENU ===");
        System.out.println("1. Auto demo");
        System.out.println("2. Interactive demo");
        System.out.println("0. Back to semester menu");
        System.out.print("Select option: ");
    }

    private int getChoice() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }

    private void runAutoDemo() {
        System.out.println("\n=== AUTO DEMO ===");
        System.out.println("Starting supervisor and abstract program...");

        SupervisorDemo demo = new SupervisorDemo();
        demo.start();

        waitForReturn();
    }

    private void runInteractiveDemo() {
        System.out.println("\n=== INTERACTIVE DEMO ===");
        System.out.println("Manual supervisor startup...");

        Supervisor supervisor = new Supervisor();
        supervisor.start();

        System.out.println("\nSupervisor started. Abstract program is running.");
        System.out.println("Observe state changes in console.");
        System.out.println("\nPress Enter to stop supervisor...");

        scanner.nextLine();
        scanner.nextLine();

        supervisor.stop();

        waitForReturn();
    }

    private void waitForReturn() {
        System.out.println("\nPress Enter to return to lab menu...");
        scanner.nextLine();
        scanner.nextLine();
    }
}