package com.wyl.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: guli_parent
 * @description: 自定义异常
 * @author: wyl
 * @create: 2023-03-20 11:00
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuliException extends RuntimeException{
    private Integer code;
    private String msg;
}
