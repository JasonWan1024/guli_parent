package com.wyl.eduorder.service;

import com.wyl.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-05-16
 */
public interface OrderService extends IService<Order> {

    /**
     * 创建订单
     * @param courseId
     * @param memberIdByJwtToken
     * @return
     */
    String createOrder(String courseId, String memberIdByJwtToken);
}
