package com.example.demo;

import javafx.scene.effect.Bloom;

import javax.xml.stream.events.StartDocument;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ronghualu
 * @date 2021-03-27 - 15:31
 */
class Queue {
    private volatile boolean FLAG = true;
    private AtomicInteger atomicInteger = new AtomicInteger();

    BlockingQueue<String> blockingQueue = null;

    /**
     * 阻塞队列的构造方法
     */
    public Queue(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println("队列创建成功");
    }

    public void prod() throws InterruptedException {
        String data = null;
        boolean offer;
        while (FLAG) {
            data = atomicInteger.incrementAndGet() + "";
            offer = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (offer) {
                System.out.println("成功" + data);
            } else {
                System.out.println("失败" + data);
            }
            TimeUnit.SECONDS.sleep(1L);
        }
        System.out.println("生产队列停止！");
    }

    public void consu() throws InterruptedException {
        String data = null;
        while (FLAG) {
            data = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (data == null || data.equals("")) {
                System.out.println("消费完了");
                FLAG = false;
                return;
            }
            System.out.println("消费！" + data);
        }
    }



    public void stop(){
        this.FLAG = false;
        System.out.println("停止！");
    }

}
public class ProdConsumer_BlockQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        Queue queue = new Queue(new ArrayBlockingQueue<String>(3));
        new Thread(()->{
            try {
                queue.prod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"pro").start();

        new Thread(()->{
            try {
                queue.consu();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"consu").start();

        TimeUnit.SECONDS.sleep(10L);
        queue.stop();

    }


}