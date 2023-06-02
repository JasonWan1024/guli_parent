package com.wyl.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyl.commonutils.JwtUtils;
import com.wyl.commonutils.R;
import com.wyl.commonutils.ordervo.CourseWebVoOrder;
import com.wyl.eduservice.client.OrderClient;
import com.wyl.eduservice.entity.EduCourse;
import com.wyl.eduservice.entity.chapter.ChapterVo;
import com.wyl.eduservice.entity.frontvo.CourseFrontVo;
import com.wyl.eduservice.entity.frontvo.CourseWebVo;
import com.wyl.eduservice.service.EduChapterService;
import com.wyl.eduservice.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @program: guli_parent
 * @description:
 * @author: wyl
 * @create: 2023-05-10 08:22
 **/
@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    @PostMapping("/coursePage/{page}/{limit}")
    public R coursePage(@PathVariable("page") long page,
                        @PathVariable("limit") long limit,
                        @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> pageCourse = new Page<>(page, limit);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);
        return R.ok().data(map);
    }

    /**
     * 课程简介（课程的基本信息和课程章节大纲的查询）
     * @param courseId
     * @return
     */
    @GetMapping("/getCourseFrontInfo/{courseId}")
    public R getCourseFrontInfo(@PathVariable("courseId") String courseId, HttpServletRequest request){
        //获取课程的基本信息
        CourseWebVo courseWebVo = courseService.getCourseFrontInfo(courseId);
        //获取课程大纲信息
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        //判断课程购买情况
        boolean buyCourse = orderClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isbuy",buyCourse);
    }

    /**
     * 根据课程ID查询课程信息
     * @param id
     * @return
     */
    @PostMapping("/getCourseInfoById/{id}")
    public CourseWebVoOrder getCourseInfoById(@PathVariable("id") String id){
        CourseWebVo courseFrontInfo = courseService.getCourseFrontInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseFrontInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }

}
