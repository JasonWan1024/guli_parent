package com.wyl.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: guli_parent
 * @description: 课程最终发布返回对象
 * @author: wyl
 * @create: 2023-04-06 08:23
 **/
@ApiModel(value = "课程发布信息")
@Data
public class CoursePublishVo implements Serializable {
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
