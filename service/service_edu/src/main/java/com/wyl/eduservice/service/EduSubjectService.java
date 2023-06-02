package com.wyl.eduservice.service;

import com.wyl.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wyl.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-03-27
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 添加课程分类
     * @param file
     * @param subjectService
     */
    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    /**
     * 获取所有一级二级课程分类
     * @return
     */
    List<OneSubject> getAllOnwTwoSubject();
}
