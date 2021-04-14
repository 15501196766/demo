package com.example.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * cas demo  比较与交换
 * @author ronghualu
 * @date 2021-03-25 - 19:13
 */
public class CASDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        atomicInteger.getAndIncrement();
        boolean b = atomicInteger.compareAndSet(5, 10);
        boolean c = atomicInteger.compareAndSet(5, 20);

        System.out.println(b);
        System.out.println(c);
        System.out.println(atomicInteger.get());
    }
}
