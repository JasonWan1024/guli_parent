package com.wyl.educms;

/**
 * @program: guli_parent
 * @description:
 * @author: wyl
 * @create: 2023-04-14 15:50
 **/

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.wyl"}) //指定扫描位置
@MapperScan("com.wyl.educms.mapper")
@EnableDiscoveryClient //注册nacos
@EnableFeignClients
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
