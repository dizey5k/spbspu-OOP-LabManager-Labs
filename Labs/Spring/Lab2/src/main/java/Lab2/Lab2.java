package Spring.Lab2;

import LabManager.LabInfo;
import LabManager.RunnableLab;
import java.util.Scanner;
import Spring.Lab2.MessageQueueDemo;

public class Lab2 implements RunnableLab {
    private final LabInfo labInfo;

    public Lab2() {
        this.labInfo = new LabInfo(2, "Message Queue",
                "Message queue with N producers and N consumers.",
                "Spring");
    }

    @Override
    public LabInfo getLabInfo() {
        return labInfo;
    }

    @Override
    public void run() {
        System.out.println("=== Lab 2: Message Queue ===");
        System.out.println("Spring Semester");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of threads (N) for producers and consumers: ");
        int n = scanner.nextInt();

        MessageQueueDemo demo = new MessageQueueDemo();
        demo.start(n);

        System.out.println("\nLab 2 completed. Press Enter to return...");
        scanner.nextLine();
        scanner.nextLine();
    }
}