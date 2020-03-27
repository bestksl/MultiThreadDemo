package demo01;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
@Slf4j
public class Test1 {


    static int threadLocal = 200;
    static int clientLocal = 5000;
    static long count = 0;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadLocal);
        for (int i = 0; i < clientLocal; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        semaphore.acquire();
                        add();
                        semaphore.release();
                    } catch (InterruptedException e) {
                       log.error(e.getMessage());
                    }
                }
            });
        }
        executorService.shutdown();
        log.info("total num is {}",count);

    }

    private static void add() {
        count++;
    }
}
