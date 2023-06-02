package com.wyl.eduservice.client;

import com.wyl.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    //删除阿里云vod中的视频
    @DeleteMapping("/eduvod/video/delete/{videoId}")
    public R deleteVodVideo(@PathVariable("videoId") String videoId);

    //删除阿里云vod中多个视频
    @DeleteMapping("/eduvod/video/deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
