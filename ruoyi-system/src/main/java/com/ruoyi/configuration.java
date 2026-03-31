package com.ruoyi;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author YJ
 */
@Component
public class configuration {
    @Bean(name = "myTaskThreadPool")
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(
                10,
                20,
                600,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(50),
                (Runnable r) -> {
                    Thread t = Executors.defaultThreadFactory().newThread(r);
                    t.setName("myTaskThreadPool-" + t.getId());
                    return t;
                },
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
