package com.example.demo;

/**
 * @author ronghualu
 * @date 2021-03-25 - 17:11
 */
public class SingletoDemo {

    private static volatile SingletoDemo instance = null;

    private SingletoDemo() {
        System.out.println("私有的构造方法");
    }

    public static SingletoDemo getInstance() {
        if (instance == null) {
            synchronized (SingletoDemo.class) {
                if (instance == null) {
                    instance = new SingletoDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
//        System.out.println( SingletoDemo.getInstance() == SingletoDemo.getInstance());
//        System.out.println( SingletoDemo.getInstance() == SingletoDemo.getInstance());
//        System.out.println( SingletoDemo.getInstance() == SingletoDemo.getInstance());

        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                SingletoDemo.getInstance();
            }, String.valueOf(i)).start();
        }
    }
}
