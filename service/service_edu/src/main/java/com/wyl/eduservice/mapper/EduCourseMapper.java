package com.wyl.eduservice.mapper;

import com.wyl.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyl.eduservice.entity.frontvo.CourseWebVo;
import com.wyl.eduservice.entity.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2023-03-29
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getCoursePublishVo(String courseId);

    /**
     * 根据课程ID查询课程基本信息
     * @param courseId
     * @return
     */
    public CourseWebVo getCourseFrontInfo(String courseId);
}
