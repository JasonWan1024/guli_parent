package com.wyl.eduorder.controller;


import com.wyl.commonutils.R;
import com.wyl.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-05-16
 */
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    /**
     * 生成微信支付二维码
     * @param orderNo
     * @return
     */
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        //返回信息，包含二维码地址，还有其他需要的信息
        Map map = payLogService.createNative(orderNo);
        System.out.println("****返回二维码map集合:"+map);
        return R.ok().data(map);
    }

    /**
     * 根据订单号查询支付状态
     * @param orderNo
     * @return
     */
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        //根据订单号查询订单的支付状态
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("*****查询订单状态map集合:"+map);
        if(map == null) {
            return R.error().message("支付出错了");
        }
        //如果返回map里面不为空，通过map获取订单状态
        if(map.get("trade_state").equals("SUCCESS")) {//支付成功
            //添加记录到支付表，更新订单表订单状态
            payLogService.updateOrdersStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");

    }
}

