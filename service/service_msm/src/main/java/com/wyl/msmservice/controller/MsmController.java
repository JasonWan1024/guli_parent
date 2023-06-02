package com.wyl.msmservice.controller;

import com.wyl.commonutils.R;
import com.wyl.msmservice.service.MsmService;
import com.wyl.msmservice.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: guli_parent
 * @description:
 * @author: wyl
 * @create: 2023-04-19 08:50
 **/
@RestController
@CrossOrigin
@RequestMapping("/edumsm/msm")
public class MsmController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 阿里云短信发送
     *
     * @param phone
     * @return
     */
    @GetMapping("/send/{phone}")
    public R sendMsm(@PathVariable("phone") String phone) {
        //调用工具类生成验证码
        //ps: 验证码不是由阿里云生成的 需要自己手动生成 阿里云只做发短信的服务
        String code = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        boolean isSend = msmService.send(param, phone);
        if (isSend) {
            //通过redis设置短信验证码过期时间为五分钟
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("短信发送失败");
        }

    }
}
