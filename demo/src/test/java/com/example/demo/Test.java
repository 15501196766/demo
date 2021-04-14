package com.example.demo;


import cn.hutool.core.convert.Convert;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ronghualu
 * @date 2021-03-25 - 15:54
 */
class MyData {
    volatile int number = 0;

    public void setNumber() {
        this.number = 60;
    }

    public void setNumber2() {
        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();

    public void setNumber3() {
        atomicInteger.getAndIncrement();
    }

}

public class Test {

    public static void main(String[] args) throws InterruptedException {
        Integer a = 1;
        String aStr = Convert.toStr(a);
        System.out.println(aStr);

        String[] b = {"1", "2", "3", "4"};
        String[] strings = Convert.toStrArray(b);
    }




    /**
     *volatile 轻量化的同步机制，保证可见性，不保证原子性，禁止指令重排
     */

    /**
     * 解决volatile的原子性
     */
    private static void seeAtomicVolatile() {
        MyData myData = new MyData();

        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myData.setNumber3();
                }
            }, String.valueOf(i)).start();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println("===========" + myData.atomicInteger);
    }

    /**
     * volatile不保证原子性（写丢失）
     */
    private static void seeNoAtomicVolatile() {
        MyData myData = new MyData();

        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myData.setNumber2();
                }
            }, String.valueOf(i)).start();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println("===========" + myData.number);
    }

    /**
     * volatile 保证多线程可见性
     */
    private static void seeOkByVolatile() {
        MyData myData = new MyData();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "线程A,当前值为:" + myData.number);
            try {
                TimeUnit.SECONDS.sleep(3L);
            } catch (Exception e) {
                e.printStackTrace();
            }
            myData.setNumber();
            System.out.println(Thread.currentThread().getName() + "线程A2,当前值为:" + myData.number);
        }, "aaa").start();

        while (myData.number == 0) {

        }
        System.out.println(Thread.currentThread().getName() + "线程main,当前值为:" + myData.number);
    }
}
