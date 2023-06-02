package com.wyl.eduorder.service;

import com.wyl.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-05-16
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * 生成微信支付二维码
     * @param orderNo
     * @return
     */
    Map createNative(String orderNo);

    /**
     * 根据订单号查询订单的支付状态
     * @param orderNo
     * @return
     */
    Map<String, String> queryPayStatus(String orderNo);

    /**
     * 向支付表中添加记录 并更新支付状态
     * @param map
     */
    void updateOrdersStatus(Map<String, String> map);
}
