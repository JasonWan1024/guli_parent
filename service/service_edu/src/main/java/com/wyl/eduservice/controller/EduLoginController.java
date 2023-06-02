package com.wyl.eduservice.controller;

import com.wyl.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @program: guli_parent
 * @description: 登录功能简单实现 完整实现等后面用权限框架
 * @author: wyl
 * @create: 2023-03-23 11:31
 **/
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin //解决跨域问题
public class EduLoginController {

    @PostMapping("/login")
    public R login(){
        //data 中的key和
        // /vue-admin-template-master/src/store/modules/user.js
        //中的login方法的返回值相对应
        return R.ok().data("token","admin");
    }

    @GetMapping("/info")
    public R info() {
        //data 中的key和
        // /vue-admin-template-master/src/store/modules/user.js
        //中的GetInfo方法的返回值相对应
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
