package com.wyl.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyl.commonutils.R;
import com.wyl.eduservice.entity.EduCourse;
import com.wyl.eduservice.entity.EduTeacher;
import com.wyl.eduservice.service.EduCourseService;
import com.wyl.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: guli_parent
 * @description:
 * @author: wyl
 * @create: 2023-05-05 16:11
 **/
@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    /**
     * 前台用户界面讲师分页
     *
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/page/{page}/{limit}")
    public R page(@PathVariable("page") long page,
                  @PathVariable("limit") long limit) {
        Page<EduTeacher> pageParam = new Page<>(page, limit);
        Map<String, Object> map = teacherService.pageListWeb(pageParam);
        return R.ok().data(map);
    }

    /**
     * 根据讲师ID查询讲师基本信息和所教课程
     * @param teacherId
     * @return
     */
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId){
        EduTeacher teacherInfo = teacherService.getById(teacherId);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> list = courseService.list(wrapper);
        return R.ok().data("teacherInfo",teacherInfo).data("list",list);
    }
}
