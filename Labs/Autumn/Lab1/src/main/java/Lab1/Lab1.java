package Autumn.Lab1;

import Lab1.Hero.Hero;
import Lab1.MovementStrategies.*;
import LabManager.LabInfo;
import LabManager.RunnableLab;

import java.util.Scanner;

public class Lab1 implements RunnableLab {
    private final Scanner scanner;
    private Hero hero;
    private final LabInfo labInfo;

    public Lab1() {
        this.scanner = new Scanner(System.in);
        this.hero = new Hero("Oleg");
        this.labInfo = new LabInfo(1, "Game",
                "Strategy pattern for hero movement", "Autumn");
    }

    @Override
    public void run() {
        System.out.println("=== Lab 1: pattern 'Strategy' ===");
        System.out.println("Demonstrate diff mov strategies");

        boolean running = true;

        while (running) {
            showMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    demonstrateMovement();
                    break;
                case 2:
                    changeStrategy();
                    break;
                case 3:
                    demonstrateAllStrategies();
                    break;
                case 4:
                    createCustomHero();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Incorrect choice");
            }
        }
    }

    @Override
    public LabInfo getLabInfo() {
        return labInfo;
    }

    private void showMenu() {
        System.out.println("\n--- Lab 1 menu ---");
        System.out.println("Active hero: " + hero.getName());
        System.out.println("Active movement type: " + hero.getMovementStrategy());
        System.out.println("1. demonstrate movement");
        System.out.println("2. change movement type");
        System.out.println("3. show all strategies");
        System.out.println("4. create new hero");
        System.out.println("0. back to main menu");
        System.out.print("Choose action: ");
    }

    private int getChoice() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }

    private void demonstrateMovement() {
        scanner.nextLine();

        System.out.print("Enter start point: ");
        String from = scanner.nextLine();

        System.out.print("Enter end point: ");
        String to = scanner.nextLine();

        hero.move(from, to);
    }

    private void changeStrategy() {
        System.out.println("\n--- Change movement strategy ---");
        MovementStrategy newStrategy = selectMovementStrategy();

        if (newStrategy != null) {
            hero.setMovementStrategy(newStrategy);
            System.out.println("Movement strategy changed to: " + hero.getMovementStrategy());
        } else {
            System.out.println("Change strategy got canceled");
        }
    }

    private void demonstrateAllStrategies() {
        System.out.println("=== Demonstrate all movement strategies ===");

        String from = "Castle";
        String to = "Swamp";

        Hero demoHero = new Hero("Demo-hero");

        demoHero.setMovementStrategy(new WalkStrategy());
        demoHero.move(from, to);

        demoHero.setMovementStrategy(new HorseStrategy());
        demoHero.move(from, to);

        demoHero.setMovementStrategy(new FlyStrategy());
        demoHero.move(from, to);

        demoHero.setMovementStrategy(new TeleportStrategy());
        demoHero.move(from, to);

        System.out.println("\nAll strategies had been shown");
    }

    private void createCustomHero() {
        scanner.nextLine();

        System.out.print("Choose name for new hero: ");
        String name = scanner.nextLine();

        System.out.println("Choose movement strategy:");
        MovementStrategy strategy = selectMovementStrategy();

        if (strategy != null) {
            this.hero = new Hero(name, strategy);
            System.out.println("Created new hero: " + name + " with movement strategy: " + hero.getMovementStrategy());
        } else {
            System.out.println("Create hero got canceled");
        }
    }

    private MovementStrategy selectMovementStrategy() {
        while (true) {
            System.out.println("\nAvailable movement strategy:");
            System.out.println("1. Walk");
            System.out.println("2. horse");
            System.out.println("3. fly");
            System.out.println("4. teleport");
            System.out.println("0. cancel");
            System.out.print("Choose strategy: ");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Error: enter int from 0 to 4");
                continue;
            }

            switch (choice) {
                case 1:
                    return new WalkStrategy();
                case 2:
                    return new HorseStrategy();
                case 3:
                    return new FlyStrategy();
                case 4:
                    return new TeleportStrategy();
                case 0:
                    return null;
                default:
                    System.out.println("Incorrect choice. Should be int from 0 to 4");
            }
        }
    }
}