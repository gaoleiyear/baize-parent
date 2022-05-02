package com.baize.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @SpringBootApplication 说明这个类是SpringBoot的主配置类，SpringBoot就应该运行这个类的main方法来启动SpringBoot应用；
 * @EnableFeignClients 启用feign客户端
 * @EnableDiscoveryClient 注册中心（从Spring Cloud Edgware开始，@EnableDiscoveryClient 或@EnableEurekaClient 可省略。只需加上相关依赖，并进行相应配置，即可将微服务注册到服务发现组件上）
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class BaizeAuthApplication {
    //微服务启动入口
    public static void main(String[] args) {
        SpringApplication.run(BaizeAuthApplication.class,args);
    }
}
