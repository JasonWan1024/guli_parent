package com.wyl.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyl.commonutils.JwtUtils;
import com.wyl.commonutils.MD5;
import com.wyl.educenter.entity.UcenterMember;
import com.wyl.educenter.entity.vo.RegisterVo;
import com.wyl.educenter.mapper.UcenterMemberMapper;
import com.wyl.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyl.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-04-23
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 用户登录
     *
     * @param member
     * @return
     */
    @Override
    public String login(UcenterMember member) {
        //获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();
        //判断手机号或密码是否为空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "登录失败");
        }
        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        if (mobileMember == null) {
            throw new GuliException(20001, "手机号错误，请重新输入或进行注册");
        }
        //判断密码是否正确
        //使用MD5加密
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new GuliException(20001, "密码错误，请重新输入");
        }
        //判断用户是否被禁用
        if (mobileMember.getIsDisabled()) {
            throw new GuliException(20001, "登录失败，该用户已被禁用");
        }

        //通过jwt生成token字符串
        String token = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return token;
    }

    /**
     * 用户注册
     *
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        //手机号
        String mobile = registerVo.getMobile();
        //验证码
        String code = registerVo.getCode();
        //昵称
        String nickname = registerVo.getNickname();
        //密码
        String password = registerVo.getPassword();

        //非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "注册失败，请填写完整信息");
        }
        //验证码判断
        //通过redis获取验证码 并判断
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new GuliException(20001, "验证码错误，请重新输入");
        }
        //判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new GuliException(20001, "该手机号已经被注册");
        }

        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        baseMapper.insert(member);
    }

    /**
     * 根据openid判断
     *
     * @param openid
     * @return
     */
    @Override
    public UcenterMember getOpenidMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    /**
     * 统计每日注册人数
     * @param day
     * @return
     */
    @Override
    public Integer countRegisterByDay(String day) {
        return baseMapper.countRegisterByDay(day);
    }
}
