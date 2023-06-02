package com.wyl.eduservice.service;

import com.wyl.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-03-29
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 根据课程ID删除小节
     * @param id
     */
    void removeVideoByCourseId(String id);
}
