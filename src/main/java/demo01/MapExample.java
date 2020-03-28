package demo01;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
public class MapExample {

    static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    static int threadLocal = 200;
    static int clientLocal = 5000;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadLocal);
        for (int i = 0; i < clientLocal; i++) {
            final int count = i;
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        semaphore.acquire();
                        add(count);
                        semaphore.release();
                    } catch (InterruptedException e) {
                        log.error(e.getMessage());
                    }
                }
            });
        }
        executorService.shutdown();
        log.info("Map size is {}", map.size());

    }

    private static void add(int key) {
        map.put(key, key);
    }
}