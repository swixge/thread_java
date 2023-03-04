import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Vvedit` krok: ");
        int step = scanner.nextInt();   //крок
        System.out.print("Vvedit` kilkist` potokiv: ");
        byte numThreads = scanner.nextByte();     //кількість потоків
        double[] sums = new double[numThreads];   // сума кожного з потоків
        double[] counts = new double[numThreads];   //кількість доданків
        boolean canStop = false;

        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            int index = i;  //позицію потоку в масиві
            threads[i] = new Thread(() -> calculator(index, step, sums, counts, canStop));
            threads[i].start();
        }

        Thread stopperThread = new Thread(() -> {
            try {
                stopper(canStop);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        stopperThread.start();


        stopperThread.join();

        for (int i = 0; i < numThreads; i++) {
            System.out.println("Thread " + (i + 1) + ": sum = " + sums[i] + " count = " + counts[i]);
        }
    }

    static void calculator(int index, int step, double[] sums, double[] counts, boolean canStop) {
        double sum = 0;
        int count = 0;

        for (int i = 0; !canStop; i += step) {
            sum += i;
            count++;
        }

        sums[index] = sum;
        counts[index] = count;
    }

    static void stopper(boolean canStop) throws InterruptedException {
        Thread.sleep(5000);
        canStop = true;
    }
}