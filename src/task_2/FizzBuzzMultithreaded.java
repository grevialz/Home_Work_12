package task_2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FizzBuzzMultithreaded {
    private AtomicInteger currentNumber;
    private int n;
    private BlockingQueue<String> queue;
    private Lock lock;

    public FizzBuzzMultithreaded(int n) {
        this.n = n;
        this.currentNumber = new AtomicInteger(1);
        this.queue = new LinkedBlockingQueue<>();
        this.lock = new ReentrantLock();
    }

    public void fizz() throws InterruptedException {
        while (true) {
            lock.lock();
            try {
                if (currentNumber.get() > n) {
                    return;
                }
                if (currentNumber.get() % 3 == 0 && currentNumber.get() % 5 != 0) {
                    queue.put("fizz");
                    currentNumber.incrementAndGet();
                }
            } finally {
                lock.unlock();
            }
            Thread.sleep(1);
        }
    }

    public void buzz() throws InterruptedException {
        while (true) {
            lock.lock();
            try {
                if (currentNumber.get() > n) {
                    return;
                }
                if (currentNumber.get() % 5 == 0 && currentNumber.get() % 3 != 0) {
                    queue.put("buzz");
                    currentNumber.incrementAndGet();
                }
            } finally {
                lock.unlock();
            }
            Thread.sleep(1);
        }
    }

    public void fizzbuzz() throws InterruptedException {
        while (true) {
            lock.lock();
            try {
                if (currentNumber.get() > n) {
                    return;
                }
                if (currentNumber.get() % 3 == 0 && currentNumber.get() % 5 == 0) {
                    queue.put("fizzbuzz");
                    currentNumber.incrementAndGet();
                }
            } finally {
                lock.unlock();
            }
            Thread.sleep(1);
        }
    }

    public void number() throws InterruptedException {
        while (true) {
            lock.lock();
            try {
                if (currentNumber.get() > n) {
                    return;
                }
                if (currentNumber.get() % 3 != 0 && currentNumber.get() % 5 != 0) {
                    queue.put(Integer.toString(currentNumber.get()));
                }
                currentNumber.incrementAndGet();
            } finally {
                lock.unlock();
            }
            Thread.sleep(1);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int n = 15;
        FizzBuzzMultithreaded fizzBuzz = new FizzBuzzMultithreaded(n);
        Thread threadA = new Thread(() -> {
            try {
                fizzBuzz.fizz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread threadB = new Thread(() -> {
            try {
                fizzBuzz.buzz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread threadC = new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread threadD = new Thread(() -> {
            try {
                fizzBuzz.number();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        threadA.join();
        threadB.join();
        threadC.join();
        threadD.join();

        while (!fizzBuzz.queue.isEmpty()) {
            System.out.println(fizzBuzz.queue.take());
        }
    }
}
