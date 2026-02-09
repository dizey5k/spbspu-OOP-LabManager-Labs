package Lab1.supervisor;

public class Supervisor implements Runnable {
    private final AbstractProgram program;
    private Thread supervisorThread;
    private volatile boolean running;
    private final Object controlLock = new Object();

    public Supervisor() {
        this.program = new AbstractProgram();
        this.running = false;
    }

    public synchronized void start() {
        if (supervisorThread == null || !supervisorThread.isAlive()) {
            System.out.println("[Supervisor] Initializing...");

            running = true;

            program.start();

            supervisorThread = new Thread(this, "Supervisor");
            supervisorThread.start();

            System.out.println("[Supervisor] Started and monitoring abstract program");
        }
    }

    public synchronized void stop() {
        System.out.println("[Supervisor] Stop request...");
        running = false;

        synchronized (controlLock) {
            controlLock.notifyAll();
        }

        program.stop();

        try {
            if (supervisorThread != null && supervisorThread.isAlive()) {
                supervisorThread.join(1500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("[Supervisor] Stopped");
    }

    private void restartProgram() {
        System.out.println("[Supervisor] STOPPING state detected, restarting program...");

        program.stop();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        program.start();

        System.out.println("[Supervisor] Program restarted");
    }

    @Override
    public void run() {
        System.out.println("[Supervisor] Supervisor thread started");

        try {
            while (running && program.isRunning()) {
                ProgramState currentState = program.getState();

                System.out.printf("[Supervisor] Current program state: %s%n", currentState.getName());

                switch (currentState) {
                    case STOPPING:
                        restartProgram();
                        break;

                    case FATAL_ERROR:
                        System.out.println("[Supervisor] FATAL ERROR detected, terminating program...");
                        program.stop();
                        running = false;
                        break;

                    case RUNNING:
                        System.out.println("[Supervisor] Program working normally");
                        break;

                    case UNKNOWN:
                        System.out.println("[Supervisor] Program state unknown");
                        break;
                }

                synchronized (controlLock) {
                    try {
                        // Use wait with timeout for periodic checking
                        controlLock.wait(800);

                        // Also wait for state change from program
                        program.waitForStateChange(400);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        running = false;
                    }
                }
            }
        } catch (Exception e) {
            System.out.printf("[Supervisor] Error during execution: %s%n", e.getMessage());
        }

        if (program.isRunning()) {
            program.stop();
        }

        System.out.println("[Supervisor] Supervisor thread terminated");
    }

    public boolean isRunning() {
        return running;
    }
}