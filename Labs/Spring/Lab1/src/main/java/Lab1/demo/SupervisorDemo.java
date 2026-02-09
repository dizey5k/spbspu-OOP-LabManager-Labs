package Lab1.demo;

import Lab1.supervisor.Supervisor;

public class SupervisorDemo {
    private final Supervisor supervisor;
    private Thread autoStopThread;

    public SupervisorDemo() {
        this.supervisor = new Supervisor();
    }

    public void start() {
        System.out.println("=== DEMO START ===");

        supervisor.start();

        autoStopThread = new Thread(() -> {
            try {
                Thread.sleep(8000);

                System.out.println("\n=== AUTO STOP ===");
                System.out.println("8 seconds elapsed, stopping demo...");

                supervisor.stop();

                System.out.println("=== DEMO COMPLETE ===");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "AutoStop-Thread");

        autoStopThread.start();

        try {
            autoStopThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        if (supervisor.isRunning()) {
            supervisor.stop();
        }

        if (autoStopThread != null && autoStopThread.isAlive()) {
            autoStopThread.interrupt();
        }
    }
}