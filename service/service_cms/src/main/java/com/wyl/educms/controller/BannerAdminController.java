package com.wyl.educms.controller;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyl.commonutils.R;
import com.wyl.educms.entity.CrmBanner;
import com.wyl.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 后台增删改查接口
 * </p>
 *
 * @author testjava
 * @since 2023-04-14
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 分页查询banner
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable("page") long page,
                        @PathVariable("limit") long limit) {
        Page<CrmBanner> pageBanner = new Page<>(page, limit);
        bannerService.page(pageBanner,null);
        List<CrmBanner> records = pageBanner.getRecords();
        long total = pageBanner.getTotal();
        return R.ok().data("items",records).data("total",total);
    }

    /**
     * 添加banner
     * @param crmBanner
     * @return
     */
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        bannerService.save(crmBanner);
        return R.ok();
    }

    /**
     * 获取banner
     * @param id
     * @return
     */
    @GetMapping("/getBanner/{id}")
    public R getBanner(@PathVariable("id") String id){
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("banner",banner);
    }

    /**
     * 修改banner
     * @param crmBanner
     * @return
     */
    @PutMapping("/updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        bannerService.updateById(crmBanner);
        return R.ok();
    }

    /**
     * 删除banner
     * @param id
     * @return
     */
    @DeleteMapping("/deleteBanner/{id}")
    public R deleteBanner(@PathVariable("id") String id) {
        bannerService.removeById(id);
        return R.ok();
    }
}

