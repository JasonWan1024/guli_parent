package com.wyl.msmservice.service;

import java.util.Map;

/**
 * @program: guli_parent
 * @description:
 * @author: wyl
 * @create: 2023-04-19 08:50
 **/
public interface MsmService{
    /**
     * 阿里云短信发送
     * @param param
     * @param phone
     * @return
     */
    boolean send(Map<String, Object> param, String phone);
}
