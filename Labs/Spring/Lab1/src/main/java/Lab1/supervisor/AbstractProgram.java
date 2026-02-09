package Lab1.supervisor;

import java.util.Random;

public class AbstractProgram implements Runnable {
    private ProgramState state;
    private final Object stateLock = new Object();
    private Thread programThread;
    private Thread stateDaemonThread;
    private volatile boolean running;
    private final Random random = new Random();

    public AbstractProgram() {
        this.state = ProgramState.UNKNOWN;
        this.running = false;
    }

    /**
     * Starts abstract program.
     * Creates main program thread and daemon thread for state changes.
     */
    public synchronized void start() {
        if (programThread == null || !programThread.isAlive()) {
            System.out.println("[Abstract Program] Starting...");

            running = true;
            setState(ProgramState.RUNNING);

            programThread = new Thread(this, "AbstractProgram-Main");
            programThread.start();

            stateDaemonThread = new Thread(this::stateChangeDaemon, "AbstractProgram-Daemon");
            stateDaemonThread.setDaemon(true);
            stateDaemonThread.start();

            System.out.println("[Abstract Program] Started successfully");
        }
    }

    /**
     * Stops abstract program.
     */
    public synchronized void stop() {
        System.out.println("[Abstract Program] Stop request...");
        running = false;
        setState(ProgramState.STOPPING);

        synchronized (stateLock) {
            stateLock.notifyAll();
        }

        try {
            if (programThread != null && programThread.isAlive()) {
                programThread.join(1000);
            }
            if (stateDaemonThread != null && stateDaemonThread.isAlive()) {
                stateDaemonThread.join(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("[Abstract Program] Stopped");
    }

    /**
     * Daemon thread that randomly changes program state.
     * Change interval: 1-2 seconds.
     */
    private void stateChangeDaemon() {
        System.out.println("[Abstract Program] State change daemon started");

        try {
            while (running) {
                // Random interval: 1000-2000 ms (1-2 seconds)
                int interval = 1000 + random.nextInt(1000);
                Thread.sleep(interval);

                if (running) {
                    // Randomly select new state (except UNKNOWN)
                    ProgramState[] possibleStates = {
                            ProgramState.RUNNING,
                            ProgramState.STOPPING,
                            ProgramState.FATAL_ERROR
                    };
                    ProgramState newState = possibleStates[random.nextInt(possibleStates.length)];

                    setState(newState);
                    System.out.printf("[Abstract Program] Daemon changed state to: %s%n", newState.getName());
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[Abstract Program] Daemon interrupted");
        }

        System.out.println("[Abstract Program] State change daemon terminated");
    }

    public void setState(ProgramState newState) {
        synchronized (stateLock) {
            ProgramState oldState = this.state;
            this.state = newState;

            if (oldState != newState) {
                System.out.printf("[Abstract Program] State changed: %s -> %s%n",
                        oldState.getName(), newState.getName());
            }

            stateLock.notifyAll();
        }
    }

    public ProgramState getState() {
        synchronized (stateLock) {
            return state;
        }
    }

    public void waitForStateChange() throws InterruptedException {
        synchronized (stateLock) {
            stateLock.wait();
        }
    }

    public void waitForStateChange(long timeout) throws InterruptedException {
        synchronized (stateLock) {
            stateLock.wait(timeout);
        }
    }

    @Override
    public void run() {
        System.out.println("[Abstract Program] Main thread started");

        try {
            while (running) {
                synchronized (stateLock) {
                    ProgramState currentState = this.state;

                    switch (currentState) {
                        case RUNNING:
                            System.out.println("[Abstract Program] Performing work...");
                            stateLock.wait(500);
                            break;

                        case STOPPING:
                            System.out.println("[Abstract Program] Stopped, waiting...");
                            stateLock.wait();
                            break;

                        case FATAL_ERROR:
                            System.out.println("[Abstract Program] Critical error, terminating...");
                            running = false;
                            break;

                        case UNKNOWN:
                            stateLock.wait();
                            break;
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[Abstract Program] Main thread interrupted");
        }

        System.out.println("[Abstract Program] Main thread terminated");
    }

    public boolean isRunning() {
        return running;
    }
}