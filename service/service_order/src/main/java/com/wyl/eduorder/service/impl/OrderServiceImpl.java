package com.wyl.eduorder.service.impl;

import com.wyl.commonutils.ordervo.CourseWebVoOrder;
import com.wyl.commonutils.ordervo.UcenterMemberOrder;
import com.wyl.eduorder.client.EduClient;
import com.wyl.eduorder.client.UcenterClient;
import com.wyl.eduorder.entity.Order;
import com.wyl.eduorder.mapper.OrderMapper;
import com.wyl.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyl.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-05-16
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;
    /**
     * 创建订单
     * @param courseId
     * @param memberIdByJwtToken
     * @return
     */
    @Override
    public String createOrder(String courseId, String memberIdByJwtToken) {
        //通过nacos远程调用 通过用户ID获取用户信息
        UcenterMemberOrder userInfoById = ucenterClient.getUserInfoById(memberIdByJwtToken);
        //通过nacos远程调用 通过课程ID获取课程信息
        CourseWebVoOrder courseInfoById = eduClient.getCourseInfoById(courseId);
        //创建Order对象，向order对象里面设置需要数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseInfoById.getTitle());
        order.setCourseCover(courseInfoById.getCover());
        order.setTeacherName(courseInfoById.getTeacherName());
        order.setTotalFee(courseInfoById.getPrice());
        order.setMemberId(memberIdByJwtToken);
        order.setMobile(userInfoById.getMobile());
        order.setNickname(userInfoById.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1
        baseMapper.insert(order);
        //返回订单号
        return order.getOrderNo();
    }
}
