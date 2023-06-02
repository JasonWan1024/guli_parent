package com.wyl.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyl.commonutils.JwtUtils;
import com.wyl.commonutils.R;
import com.wyl.eduorder.entity.Order;
import com.wyl.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-05-16
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     * @param courseId
     * @param request
     * @return
     */
    @PostMapping("/createOrder/{courseId}")
    public R saveOrder(@PathVariable("courseId") String courseId, HttpServletRequest request){
        //创建订单 返回订单号
        String orderNo = orderService.createOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderNo",orderNo);
    }

    /**
     * 根据订单号查询订单信息
     * @param orderNo
     * @return
     */
    @GetMapping("/getOrderInfo/{orderNo}")
    public R getOrderInfo(@PathVariable("orderNo") String orderNo){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order orderInfo = orderService.getOne(wrapper);
        return R.ok().data("orderInfo",orderInfo);
    }

    /**
     * 根据课程ID和用户ID查询订单支付情况
     * @param courseId
     * @param memberId
     * @return
     */
    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);//支付状态 1代表已经支付
        int count = orderService.count(wrapper);
        if(count>0) { //已经支付
            return true;
        } else {
            return false;
        }
    }
}

