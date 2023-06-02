package com.wyl.eduservice.service;

import com.wyl.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wyl.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-03-29
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 根据课程id查询章节和小节
     * @param courseId
     * @return
     */
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    /**
     * 根据ID删除章节
     * @param chapterId
     */
    boolean daleteChapter(String chapterId);

    /**
     * 根据课程id删除章节
     * @param id
     */
    void removeChapterByCourseId(String id);
}
