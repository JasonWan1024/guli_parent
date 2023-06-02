package com.wyl.staservice.controller;


import com.wyl.commonutils.R;
import com.wyl.staservice.client.UcenterClient;
import com.wyl.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-05-25
 */
@RestController
@RequestMapping("/staservice/sta")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService staService;

    /**
     * 统计每日注册人数 生成统计数据
     * @param day
     * @return
     */
    @PostMapping("/registerCount/{day}")
    public R registerCount(@PathVariable("day") String day){
        staService.registerCount(day);
        return R.ok();
    }

    /**
     * 图表显示，返回两部分数据，日期json数组，数量json数组
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,@PathVariable String begin,
                      @PathVariable String end) {
        Map<String,Object> map = staService.getShowData(type,begin,end);
        return R.ok().data(map);
    }
}

