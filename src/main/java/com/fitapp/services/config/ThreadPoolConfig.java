package com.fitapp.services.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class ThreadPoolConfig {

    // this is the Spring way of creating a thread pool executor
//    @Bean
//    public Executor springBootDefaultTaskExecutor(){
//         int minThreads = 2;
//         int maxThreads = 4;
//         int queueSize = 3;
//
//        ThreadPoolTaskExecutor poolExecutor = new ThreadPoolTaskExecutor();
//        poolExecutor.setCorePoolSize(minThreads);
//        poolExecutor.setMaxPoolSize(maxThreads);
//        poolExecutor.setQueueCapacity(queueSize);
//        poolExecutor.setThreadNamePrefix("NeerajThread-");
//        poolExecutor.initialize();
//        return poolExecutor;
//    }


    @Bean(name="javaThreadPoolExecutor")
    public Executor javaTaskPoolExecutor(){
         int minThreads = 2;
         int maxThreads = 4;
         int queueSize = 3;

        return new ThreadPoolExecutor(minThreads, maxThreads, 1, TimeUnit.HOURS,
                new ArrayBlockingQueue<>(queueSize), new CustomThreadFactory());
    }
}

class CustomThreadFactory implements ThreadFactory{

    private final AtomicInteger threadNo = new AtomicInteger(1);

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("JavaThread-"+ threadNo.getAndIncrement());
        return thread;
    }
}
