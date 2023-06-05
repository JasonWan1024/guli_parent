package com.wyl.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyl.commonutils.R;
import com.wyl.eduservice.entity.EduCourse;
import com.wyl.eduservice.entity.vo.CourseInfoVo;
import com.wyl.eduservice.entity.vo.CoursePublishVo;
import com.wyl.eduservice.entity.vo.CourseQuery;
import com.wyl.eduservice.entity.vo.TeacherQuery;
import com.wyl.eduservice.service.EduChapterService;
import com.wyl.eduservice.service.EduCourseDescriptionService;
import com.wyl.eduservice.service.EduCourseService;
import com.wyl.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-03-29
 */
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;
    /**
     * 添加课程基本信息
     *
     * @param courseInfoVo
     * @return
     */
    @PostMapping("/addCourse")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    /**
     * 通过课程id查询课程基本信息
     *
     * @param courseId
     * @return
     */
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable("courseId") String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfoByCourseId(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    /**
     * 修改课程基本信息
     *
     * @param courseInfoVo
     * @return
     */
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    /**
     * 查询课程发布基本信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable("id") String id) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVo);
    }

    /**
     * 课程最终发布
     * 将课程中的status设置为normal
     *
     * @param id
     * @return
     */
    @PostMapping("/publishCourse/{id}")
    public R publishCourse(@PathVariable("id") String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }

    /**
     * 课程的多条件组合分页查询
     *
     * @param current
     * @param limit
     * @param courseQuery
     * @return
     */
    @PostMapping("/pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable("current") long current,
                                 @PathVariable("limit") long limit,
                                 @RequestBody(required = false) CourseQuery courseQuery) {
        Page<EduCourse> pageCourse = courseService.pageCourseConditon(current, limit, courseQuery);
        long total = pageCourse.getTotal();
        List<EduCourse> records = pageCourse.getRecords();// 获取课程信息集合
        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 删除课程
     * @param id
     * @return
     */
    @DeleteMapping("/deleteCourse/{id}")
    public R deleteCourseCondition(@PathVariable("id") String id) {
        courseService.removeCourse(id);
        return R.ok();
    }
}

