package com.wyl.educenter.controller;


import com.wyl.commonutils.JwtUtils;
import com.wyl.commonutils.R;
import com.wyl.commonutils.ordervo.UcenterMemberOrder;
import com.wyl.educenter.entity.UcenterMember;
import com.wyl.educenter.entity.vo.RegisterVo;
import com.wyl.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-04-23
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;


    /**
     * 用户登录
     *
     * @param member
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody UcenterMember member) {
        String token = ucenterMemberService.login(member);
        return R.ok().data("token", token);
    }


    /**
     * 用户注册
     *
     * @param registerVo
     * @return
     */
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo) {
        ucenterMemberService.register(registerVo);
        return R.ok();
    }

    /**
     * 根据登录生成的token获取用户的基本信息
     *
     * @param request
     * @return
     */
    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember memberInfo = ucenterMemberService.getById(memberId);
        return R.ok().data("memberInfo", memberInfo);
    }

    /**
     * 根据用户ID获取用户信息
     * @param id
     * @return
     */
    @PostMapping("/getUserInfoById/{id}")
    public UcenterMemberOrder getUserInfoById(@PathVariable("id") String id){
        UcenterMember member = ucenterMemberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member, ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    /**
     * 统计每日注册人数
     * @param day
     * @return
     */
    @GetMapping(value = "/countregister/{day}")
    public R registerCount(@PathVariable String day) {
        Integer count = ucenterMemberService.countRegisterByDay(day);
        System.out.println("count = " + count);
        return R.ok().data("countRegister", count);
    }
}

