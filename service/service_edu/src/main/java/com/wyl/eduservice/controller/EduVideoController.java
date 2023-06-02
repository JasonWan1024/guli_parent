package com.wyl.eduservice.controller;


import com.wyl.commonutils.R;
import com.wyl.eduservice.client.VodClient;
import com.wyl.eduservice.entity.EduVideo;
import com.wyl.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-03-29
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    /**
     * 添加小节
     *
     * @param eduVideo
     * @return
     */
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    /**
     * 删除小节
     * TODO 后面有视频之后需完善 删除小节时需要删除其中的视频
     *
     * @param id
     * @return
     */
    @DeleteMapping("/deleteVideo/{id}")
    public R deleteVideo(@PathVariable("id") String id) {
//       通过小节ID获取到视频ID
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //判断是否为空
        if (!StringUtils.isEmpty(videoSourceId)) {
            // 通过feign远程调用
            vodClient.deleteVodVideo(videoSourceId);
        }
        boolean flag = videoService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 根据ID查询小节信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getVideo/{id}")
    public R getVideoById(@PathVariable("id") String id) {
        EduVideo eduVideo = videoService.getById(id);
        return R.ok().data("video", eduVideo);
    }

    /**
     * 修改章节信息
     *
     * @param eduVideo
     * @return
     */
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return R.ok();
    }
}

