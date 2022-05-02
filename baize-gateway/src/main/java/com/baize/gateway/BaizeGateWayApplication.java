package com.baize.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @SpringBootApplication 启动SpringBoot应用
 * @EnableDiscoveryClient 注册中心(客户端)
 * @EnableFeignClients 启用feign客户端
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BaizeGateWayApplication {
    /**
     * 微服务网关启动
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(BaizeGateWayApplication.class,args);
    }
}
