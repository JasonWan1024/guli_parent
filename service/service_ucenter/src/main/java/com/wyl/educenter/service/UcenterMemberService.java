package com.wyl.educenter.service;

import com.wyl.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wyl.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-04-23
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    /**
     * 用户登录
     *
     * @param member
     * @return
     */
    String login(UcenterMember member);

    /**
     * 用户注册
     * @param registerVo
     */
    void register(RegisterVo registerVo);

    /**
     * 根据openid判断
     * @param openid
     * @return
     */
    UcenterMember getOpenidMember(String openid);

    /**
     * 统计每日注册人数
     * @param day
     * @return
     */
    Integer countRegisterByDay(String day);
}
