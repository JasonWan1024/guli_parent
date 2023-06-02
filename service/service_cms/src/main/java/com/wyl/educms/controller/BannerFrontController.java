package com.wyl.educms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyl.commonutils.R;
import com.wyl.educms.entity.CrmBanner;
import com.wyl.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

/**
 * @program: guli_parent
 * @description: 首页banner表 前台接口
 * @author: wyl
 * @create: 2023-04-14 16:01
 **/
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 查询所有banner
     * @return
     */
    @GetMapping("/getAllBanner")
    public R getAllBanner(){
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("id");
        wrapper.last("limit 2");
        List<CrmBanner> list = bannerService.getAllBanner();
       return R.ok().data("list",list);
    }
}
