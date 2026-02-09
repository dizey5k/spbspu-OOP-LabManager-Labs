package Spring.Lab2;

import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

public class MessageQueueDemo {

    public void start(int n) {
        System.out.println("\n=== Message Queue Demo ===");
        System.out.println("Number of producers/consumers (N): " + n);
        System.out.println("Using only java.util.concurrent package");
        System.out.println("Thread names are meaningful");
        System.out.println("No wait/notify constructions used\n");

        BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>(50);

        int[] producerCounts = new int[n];
        int[] consumerCounts = new int[n];

        try (ExecutorService executor = Executors.newFixedThreadPool(2 * n)) {

            List<Future<?>> futures = new ArrayList<>();

            System.out.println("Starting " + n + " producer threads...");
            for (int i = 0; i < n; i++) {
                final int producerId = i;
                Future<?> future = executor.submit(() -> {
                    String threadName = "Producer-" + (producerId + 1);
                    Thread.currentThread().setName(threadName);
                    System.out.println(threadName + " started");

                    int messageCounter = 0;
                    try {
                        while (!Thread.currentThread().isInterrupted()) {
                            String message = "Message-" + (++messageCounter) + " from " + threadName;

                            messageQueue.put(message);

                            producerCounts[producerId] = messageCounter;

                            Thread.sleep(100 + (int)(Math.random() * 400));
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    System.out.println(threadName + " stopped. Produced: " + messageCounter + " messages");
                });
                futures.add(future);
            }

            System.out.println("Starting " + n + " consumer threads...\n");
            for (int i = 0; i < n; i++) {
                final int consumerId = i;
                Future<?> future = executor.submit(() -> {
                    String threadName = "Consumer-" + (consumerId + 1);
                    Thread.currentThread().setName(threadName);
                    System.out.println(threadName + " started");

                    int messageCounter = 0;
                    try {
                        while (!Thread.currentThread().isInterrupted()) {
                            String message = messageQueue.take();
                            messageCounter++;

                            consumerCounts[consumerId] = messageCounter;

                            System.out.println(threadName + " processed: " + message +
                                    " (Queue size: " + messageQueue.size() + ")");

                            Thread.sleep(150 + (int)(Math.random() * 300));
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    System.out.println(threadName + " stopped. Consumed: " + messageCounter + " messages");
                });
                futures.add(future);
            }

            System.out.println("\nSystem will run for 15 seconds...\n");
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("\nStopping all threads...");
            for (Future<?> future : futures) {
                future.cancel(true);
            }

        }

        printStatistics(n, producerCounts, consumerCounts, messageQueue.size());
    }

    private void printStatistics(int n, int[] producerCounts, int[] consumerCounts, int queueSize) {
        System.out.println("\n=== Statistics ===");
        System.out.println("Configuration: N = " + n);

        int totalProduced = 0;
        int totalConsumed = 0;

        System.out.println("\nProducers:");
        for (int i = 0; i < n; i++) {
            System.out.println("  Producer-" + (i + 1) + ": " + producerCounts[i] + " messages");
            totalProduced += producerCounts[i];
        }

        System.out.println("\nConsumers:");
        for (int i = 0; i < n; i++) {
            System.out.println("  Consumer-" + (i + 1) + ": " + consumerCounts[i] + " messages");
            totalConsumed += consumerCounts[i];
        }

        System.out.println("\nSummary:");
        System.out.println("  Total produced: " + totalProduced + " messages");
        System.out.println("  Total consumed: " + totalConsumed + " messages");
        System.out.println("  Remaining in queue: " + queueSize + " messages");
        System.out.println("  Lost messages: " + (totalProduced - totalConsumed - queueSize) + " messages");

        System.out.println("\n=== Demo Completed ===");
    }
}