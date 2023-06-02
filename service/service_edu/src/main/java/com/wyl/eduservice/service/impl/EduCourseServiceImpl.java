package com.wyl.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyl.eduservice.entity.EduCourse;
import com.wyl.eduservice.entity.EduCourseDescription;
import com.wyl.eduservice.entity.frontvo.CourseFrontVo;
import com.wyl.eduservice.entity.frontvo.CourseWebVo;
import com.wyl.eduservice.entity.vo.CourseInfoVo;
import com.wyl.eduservice.entity.vo.CoursePublishVo;
import com.wyl.eduservice.entity.vo.CourseQuery;
import com.wyl.eduservice.mapper.EduCourseMapper;
import com.wyl.eduservice.service.EduChapterService;
import com.wyl.eduservice.service.EduCourseDescriptionService;
import com.wyl.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyl.eduservice.service.EduVideoService;
import com.wyl.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-03-29
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private EduChapterService chapterService;


    /**
     * 获取前端返回的数据
     * 添加课程基本信息
     *
     * @param courseInfoVo
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //创建eduCourse对象 将前端返回的courseInfoVo中的数据放入里面 添加到数据库
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        //返回影响行数
        int insert = baseMapper.insert(eduCourse);
        //判断是否执行成功
        if (insert == 0) {
            throw new GuliException(20001, "添加课程信息失败");
        }
        String cid = eduCourse.getId();
        //创建eduCourseDescription课程简介对象 将前端返回的数据放入其中 并添加到数据库
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(cid);
        courseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    /**
     * 通过课程id查询课程基本信息
     *
     * @param courseId
     * @return
     */
    @Override
    public CourseInfoVo getCourseInfoByCourseId(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    /**
     * 修改课程基本信息
     *
     * @param courseInfoVo
     */
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        baseMapper.updateById(eduCourse);

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        courseDescriptionService.updateById(eduCourseDescription);
    }

    /**
     * 查询课程发布基本信息
     *
     * @param id
     * @return
     */
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo coursePublishVo = baseMapper.getCoursePublishVo(id);
        return coursePublishVo;
    }

    /**
     * 课程的多条件组合分页查询
     *
     * @param current
     * @param limit
     * @param courseQuery
     * @return
     */
    @Override
    public Page<EduCourse> pageCourseConditon(long current, long limit, CourseQuery courseQuery) {
        Page<EduCourse> pageCourse = new Page<>(current, limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }
        page(pageCourse, wrapper);
        return pageCourse;
    }

    /**
     * 删除课程
     *
     * @param id
     * @return
     */
    @Override
    public void removeCourse(String id) {
        //1 根据课程id删除小节
        videoService.removeVideoByCourseId(id);
        //2 根据课程id删除章节
        chapterService.removeChapterByCourseId(id);
        //3 根据课程id删除描述
        courseDescriptionService.removeById(id);
        //4 根据课程id删除课程本身
        int result = baseMapper.deleteById(id);
        if (result == 0) { //失败返回
            throw new GuliException(20001, "删除失败");
        }
    }

    /**
     * 前台用户界面课程带条件查询分页
     *
     * @param pageCourse
     * @param courseFrontVo
     * @return
     */
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //一级分类
        String subjectParentId = courseFrontVo.getSubjectParentId();
        //二级分类
        String subjectId = courseFrontVo.getSubjectId();
        //购买数量/关注度
        String buyCountSort = courseFrontVo.getBuyCountSort();
        //时间
        String gmtCreateSort = courseFrontVo.getGmtCreateSort();
        //价格
        String priceSort = courseFrontVo.getPriceSort();
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id", subjectId);
        }
        if (!StringUtils.isEmpty(buyCountSort)) {
            wrapper.orderByDesc("buy_count", buyCountSort);
        }
        if (!StringUtils.isEmpty(gmtCreateSort)) {
            wrapper.orderByDesc("gmt_create", gmtCreateSort);
        }
        if (!StringUtils.isEmpty(priceSort)) {
            wrapper.orderByDesc("price", priceSort);
        }
        baseMapper.selectPage(pageCourse, wrapper);
        List<EduCourse> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();//下一页
        boolean hasPrevious = pageCourse.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }

    /**
     * 获取课程简介基本信息
     * @param courseId
     */
    @Override
    public CourseWebVo getCourseFrontInfo(String courseId) {
        return baseMapper.getCourseFrontInfo(courseId);
    }
}
