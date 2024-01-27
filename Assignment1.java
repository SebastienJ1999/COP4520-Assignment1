import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class Assignment1 {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        final int maxNumber = 100000000;
        final int numThreads = 8;

        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        List<Future<List<Integer>>> futures = new ArrayList<>();

        // Divide the workload among threads
        for (int i = 0; i < numThreads; i++) {
            int start = 1 + (i * maxNumber) / numThreads;
            int end = (i + 1) * maxNumber / numThreads;
            Future<List<Integer>> future = executorService.submit(new PrimeCalculator(start, end));
            futures.add(future);
        }

        List<Integer> primes = new ArrayList<>();

        // Collect results from threads
        for (Future<List<Integer>> future : futures) {
            try {
                primes.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        // Write results to a file
        writeResultsToFile(primes, endTime, executionTime);

        System.out.println("Results written to primes.txt");
    }

    private static void writeResultsToFile(List<Integer> primes, long endTime, long executionTime) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("primes.txt"))) {
            writer.write("Execution time: " + executionTime + " milliseconds" + "\n");
            writer.write("Total primes: " + primes.size() + "\n");
            writer.write("Sum of all primes: " + primes.stream().mapToLong(Integer::longValue).sum() + "\n");
            writer.write("Top ten primes:\n");
            primes.stream().skip(Math.max(0, primes.size() - 10)).forEach(prime -> {
                try {
                    writer.write(prime + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class PrimeCalculator implements Callable<List<Integer>> {
    private final int start;
    private final int end;

    public PrimeCalculator(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public List<Integer> call() {
        List<Integer> primes = new ArrayList<>();
        for (int i = start; i <= end; i += 2) {
            if (isPrime(i)) {
                primes.add(i);
            }
        }
        return primes;
    }

    private boolean isPrime(int num) {
        if (num < 2) {
            return false;
        }
        if (num == 2 || num == 3) {
            return true;
        }
        if (num % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= num; i += 2) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
