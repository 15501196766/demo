package com.example.demo;

import ch.qos.logback.core.util.TimeUtil;

import javax.xml.stream.events.StartDocument;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author ronghualu
 * @date 2021-03-26 - 9:45
 */
public class ABADemo {

    //原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public static void main(String[] args) {

//        List<String> list = Collections.synchronizedList(new ArrayList<>());
//        List<String> list2 = new CopyOnWriteArrayList<>();
//        Map<String, Object> map = Collections.synchronizedMap(new HashMap<>());
//        Map<String, Object> map2 = new ConcurrentHashMap<>();
//
//        Lock lock = new ReentrantLock();


        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);
        blockingQueue.offer("a");
        blockingQueue.offer("b");
        blockingQueue.offer("c");
        blockingQueue.offer("d");
    }


    /**
     * 手写自旋锁
     */
    private static void testSpinLock() {
        ABADemo a = new ABADemo();

        new Thread(()->{
            a.lock();
            try {
                TimeUnit.SECONDS.sleep(5L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            a.unlock();
        },"AAA").start();
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            a.lock();
            a.unlock();
        },"BBB").start();
    }

    public void lock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName()+"上锁");
        while (!atomicReference.compareAndSet(null,thread)){

        }
    }

    public void unlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(thread.getName()+"解锁");
    }


}
