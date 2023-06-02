package com.wyl.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyl.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-03-18
 */
public interface EduTeacherService extends IService<EduTeacher> {


    /**
     * 前台用户界面讲师分页
     * @param pageParam
     * @return
     */
    Map<String, Object> pageListWeb(Page<EduTeacher> pageParam);
}
