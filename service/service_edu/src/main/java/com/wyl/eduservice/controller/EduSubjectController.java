package com.wyl.eduservice.controller;


import com.wyl.commonutils.R;
import com.wyl.eduservice.entity.subject.OneSubject;
import com.wyl.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-03-27
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    /**
     * 添加课程分类
     * @param file
     * @return
     */
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file) {
        eduSubjectService.saveSubject(file, eduSubjectService);
        return R.ok();
    }

    /**
     * 获取所有一级二级课程分类
     * @return
     */
    @GetMapping("/getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = eduSubjectService.getAllOnwTwoSubject();
        return R.ok().data("list",list);
    }
}

