package com.wyl.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @program: guli_parent
 * @description:
 * @author: wyl
 * @create: 2023-05-25 10:23
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.wyl"})
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.wyl.staservice.mapper")
@EnableScheduling
public class StaApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class, args);
    }

}
