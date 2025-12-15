package Lab5;

import LabManager.LabInfo;
import LabManager.RunnableLab;
import Lab5.ui.Lab5Menu;

import java.util.Scanner;

public class Lab5 implements RunnableLab {
    private final Scanner scanner;
    private final LabInfo labInfo;

    public Lab5() {
        this.scanner = new Scanner(System.in);
        this.labInfo = new LabInfo(5, "Stream API Operations",
                "Implementation of various operations using Stream API");
    }

    @Override
    public void run() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("=== LABORATORY WORK #5: STREAM API ===");
        System.out.println("=".repeat(50));

        Lab5Menu menu = new Lab5Menu(scanner);

        while (menu.isRunning()) {
            menu.showMainMenu();
            int choice = getChoice();
            menu.handleChoice(choice);
        }

        System.out.println("Returning to Lab Manager...");
    }

    @Override
    public LabInfo getLabInfo() {
        return labInfo;
    }

    private int getChoice() {
        System.out.print("\nChoose option: ");
        String input = scanner.nextLine().trim();

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}