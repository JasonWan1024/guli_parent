package com.wyl.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyl.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wyl.eduservice.entity.frontvo.CourseFrontVo;
import com.wyl.eduservice.entity.frontvo.CourseWebVo;
import com.wyl.eduservice.entity.vo.CourseInfoVo;
import com.wyl.eduservice.entity.vo.CoursePublishVo;
import com.wyl.eduservice.entity.vo.CourseQuery;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-03-29
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程基本信息
     * @param courseInfoVo
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 通过课程id查询课程基本信息
     * @param courseId
     * @return
     */
    CourseInfoVo getCourseInfoByCourseId(String courseId);

    /**
     * 修改课程基本信息
     * @param courseInfoVo
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 查询课程发布基本信息
     * @param id
     * @return
     */
    CoursePublishVo publishCourseInfo(String id);

    /**
     * 课程的多条件组合分页查询
     * @param current
     * @param limit
     * @param courseQuery
     * @return
     */
    Page<EduCourse> pageCourseConditon(long current, long limit, CourseQuery courseQuery);

    /**
     * 删除课程
     * @param id
     * @return
     */
    void removeCourse(String id);

    /**
     * 前台用户界面课程带条件查询分页
     * @param pageCourse
     * @param courseFrontVo
     * @return
     */
    Map<String,Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    /**
     * 获取课程简介基本信息
     * @param courseId
     */
    CourseWebVo getCourseFrontInfo(String courseId);
}
