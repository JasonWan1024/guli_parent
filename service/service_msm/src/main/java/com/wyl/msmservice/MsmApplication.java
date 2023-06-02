package com.wyl.msmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: guli_parent
 * @description:
 * @author: wyl
 * @create: 2023-04-19 08:42
 **/
@ComponentScan({"com.wyl"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) //取消数据源自动配置
public class MsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsmApplication.class, args);
    }
}
