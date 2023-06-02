package com.wyl.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.wyl.servicebase.exceptionhandler.GuliException;
import com.wyl.vod.service.VodService;
import com.wyl.vod.utils.ConstantVodUtil;
import com.wyl.vod.utils.InitObject;
import jdk.internal.util.xml.impl.Input;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @program: guli_parent
 * @description:
 * @author: wyl
 * @create: 2023-04-10 11:22
 **/
@Service
public class VodServiceImpl implements VodService {
    /**
     * 视频上传
     *
     * @param file
     * @return
     */
    @Override
    public String uploadVideo(MultipartFile file) {

        try {
            //上传原始文件的名称
            String fileName = file.getOriginalFilename();
            //上传之后文件显示的名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtil.ACCESS_KEY_ID, ConstantVodUtil.ACCESS_KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                System.out.print("VideoId=" + response.getVideoId() + "\n");
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
            return response.getVideoId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 删除视频
     *
     * @param videoId
     */
    @Override
    public void deleteVideo(String videoId) {
        try {
            DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtil.ACCESS_KEY_ID, ConstantVodUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.print("RequestId = " + response.getRequestId() + "\n");
        } catch (ClientException e) {
            throw new GuliException(20001, "视频删除失败");
        }
    }

    /**
     * 删除多个视频
     * @param videoIdList
     */
    @Override
    public void deleteBatch(List<String> videoIdList) {
        try {
            DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtil.ACCESS_KEY_ID, ConstantVodUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
//            将videoIdList转换为1,2,3 的格式放入set方法中
            String videoId = StringUtils.join(videoIdList.toArray(),",");
            request.setVideoIds(videoId);
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.print("RequestId = " + response.getRequestId() + "\n");
        } catch (ClientException e) {
            throw new GuliException(20001, "视频删除失败");
        }
    }
}
