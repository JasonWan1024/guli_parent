package com.wyl.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: guli_parent
 * @description:
 * @author: wyl
 * @create: 2023-03-30 11:37
 **/
@Data
public class ChapterVo {
    private String id;
    private String title;
    private List<VideoVo> children = new ArrayList<>();
}
