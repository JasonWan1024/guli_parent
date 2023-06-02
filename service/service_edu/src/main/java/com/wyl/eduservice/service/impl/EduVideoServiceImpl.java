package com.wyl.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyl.eduservice.client.VodClient;
import com.wyl.eduservice.entity.EduVideo;
import com.wyl.eduservice.mapper.EduVideoMapper;
import com.wyl.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-03-29
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;
    /**
     * 根据课程ID删除小节
     * @param id
     */
    @Override
    public void removeVideoByCourseId(String id) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapper);
        List<String> videoIds = new ArrayList<>();
        for (int i = 0; i < eduVideoList.size(); i++) {
            EduVideo eduVideo = eduVideoList.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)){
                videoIds.add(videoSourceId);
            }
        }
        // 删除多个视频
        if (videoIds.size()>0){
            vodClient.deleteBatch(videoIds);
        }
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
