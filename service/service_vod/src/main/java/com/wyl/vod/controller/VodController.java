package com.wyl.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.wyl.commonutils.R;
import com.wyl.servicebase.exceptionhandler.GuliException;
import com.wyl.vod.service.VodService;
import com.wyl.vod.utils.ConstantVodUtil;
import com.wyl.vod.utils.InitObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @program: guli_parent
 * @description:
 * @author: wyl
 * @create: 2023-04-10 11:23
 **/
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    /**
     * 视频上传
     * @param file
     * @return
     */
    @PostMapping("/uploadVideo")
    public R upLoadVideo(MultipartFile file) throws IOException {
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId",videoId);
    }

    /**
     * 删除视频
     * @param videoId
     * @return
     */
    @DeleteMapping("/delete/{videoId}")
    public R deleteVodVideo(@PathVariable("videoId") String videoId){
        vodService.deleteVideo(videoId);
        return R.ok();
    }

    /**
     * 删除多个视频
     * @param videoIdList
     * @return
     */
    @DeleteMapping("/deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.deleteBatch(videoIdList);
        return R.ok();
    }

    /**
     * 根据视频id获取视频凭证
     * @param id
     * @return
     */
    @GetMapping("/getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable("id") String id){
        try {
            //创建初始化对象
            DefaultAcsClient client =
                    InitObject.initVodClient(ConstantVodUtil.ACCESS_KEY_ID, ConstantVodUtil.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        }catch(Exception e) {
            throw new GuliException(20001,"获取凭证失败");
        }
    }
}
