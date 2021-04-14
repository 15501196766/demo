package com.example.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ronghualu
 * @date 2021-03-27 - 14:54
 */
class Demo {
    private int number = 1;
    private Lock lock = new ReentrantLock();
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();
    Condition c3 = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            while (number != 1) {
                c1.await();
            }
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + i);
            }
            number = 2;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void print10() {
        lock.lock();
        try {
            while (number != 2) {
                c2.await();
            }
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + i);
            }
            number = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void print15() {
        lock.lock();
        try {
            while (number != 3) {
                c3.await();
            }
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + i);
            }
            number = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}

public class SyncAndReentrantLockDemo {

    public static void main(String[] args) {
        Demo demo = new Demo();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                demo.print5();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                demo.print10();
            }
            demo.print10();
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                demo.print15();
            }
        }, "C").start();
    }
}
