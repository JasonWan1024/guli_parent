package com.wyl.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyl.commonutils.R;
import com.wyl.eduservice.entity.EduTeacher;
import com.wyl.eduservice.entity.vo.TeacherQuery;
import com.wyl.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-03-18
 */
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin
public class EduTeacherController {

    @Autowired
    EduTeacherService eduTeacherService;

    /**
     * 查询所有教师数据
     *
     * @return
     */
    @GetMapping("/findAll")
    public R findAll() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items", list);
    }

    /**
     * 根据ID删除教师
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public R removeById(@PathVariable("id") String id) {
        boolean b = eduTeacherService.removeById(id);
        return b ? R.ok() : R.error();
    }

    /**
     * 分页查询
     *
     * @param current
     * @param limit
     * @return
     */
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageTeacher(@PathVariable("current") long current, @PathVariable("limit") long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        eduTeacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal(); //获取分页的总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //获取教师信息集合
        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 多条件组合分页查询 (暂时写在controller 之后移到service中
     *
     * @param current
     * @param limit
     * @param teacherQuery
     * @return
     */
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current") long current,
                                  @PathVariable("limit") long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_modified", end);
        }
        eduTeacherService.page(pageTeacher, wrapper);
        long total = pageTeacher.getTotal(); //获取分页的总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //获取教师信息集合
        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 添加教师
     * @param eduTeacher
     * @return
     */
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        return save ? R.ok() : R.error();
    }

    /**
     * 根据ID查询教师
     * @param id
     * @return
     */
    @GetMapping("/getTeacher/{id}")
    public R getTeacherById(@PathVariable("id") String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

    /**
     * 修改讲师信息
     * @param eduTeacher
     * @return
     */
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        return b ? R.ok(): R.error();
    }
}

