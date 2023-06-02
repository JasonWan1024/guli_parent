package com.wyl.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @program: guli_parent
 * @description:
 * @author: wyl
 * @create: 2023-04-10 11:22
 **/
public interface VodService {
    /**
     * 视频上传
     * @param file
     * @return
     */
    public String uploadVideo(MultipartFile file) throws IOException;

    /**
     * 删除视频
     * @param videoId
     */
    void deleteVideo(String videoId);

    /**
     * 删除多个视频
     * @param videoIdList
     */
    void deleteBatch(List<String> videoIdList);
}
