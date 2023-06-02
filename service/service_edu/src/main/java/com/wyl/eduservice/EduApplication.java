package com.wyl.eduservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: guli_parent
 * @description: 启动类
 * @author: wyl
 * @create: 2023-03-18 11:20
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.wyl"})
@EnableDiscoveryClient //注册nacos
@EnableFeignClients
@MapperScan(basePackages = {"com.wyl.eduservice.mapper"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
