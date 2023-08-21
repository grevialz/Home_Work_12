package task_1;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Thread pastThread = new Thread(new pastTimePrinter());
        Thread messageThread = new Thread(new FiveSecondMessagePrinter());

        pastThread.start();
        messageThread.start();
    }
    private static class pastTimePrinter implements Runnable {
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            while (true) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                long hours = TimeUnit.MILLISECONDS.toHours(elapsedTime) % 24;
                long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime) % 60;
                long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60;

                if (seconds % 5 != 0) {
                    String timeString = String.format("%02d год. %02d хв. %02d сек.", hours, minutes, seconds);
                    System.out.println("Час роботи програми: " + timeString);
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class FiveSecondMessagePrinter implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Минуло 5 секунд");
            }
        }
    }
}
