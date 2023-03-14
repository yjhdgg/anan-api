package com.anan.ananapigateway.project.provider;

import java.util.concurrent.CompletableFuture;

/**
 * @Author 13630
 * @Description TODO
 * @Date 2023/3/13 18:10
 * @Version 1.0
 */
public interface DemoService {

    String sayHello(String name);

    String sayHello2(String name);

    default CompletableFuture<String> sayHelloAsync(String name) {
        return CompletableFuture.completedFuture(sayHello(name));
    }

}
