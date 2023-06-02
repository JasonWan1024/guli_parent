package com.wyl.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: guli_parent
 * @description:
 * @author: wyl
 * @create: 2023-03-28 18:05
 **/
@Data
public class OneSubject {
    private String id;
    private String title;
    //一级分类下有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
