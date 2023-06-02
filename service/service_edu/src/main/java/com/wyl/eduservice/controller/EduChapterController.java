package com.wyl.eduservice.controller;


import com.wyl.commonutils.R;
import com.wyl.eduservice.entity.EduChapter;
import com.wyl.eduservice.entity.chapter.ChapterVo;
import com.wyl.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-03-29
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    /**
     * 根据课程id查询章节和小节
     * @param courseId
     * @return
     */
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable("courseId") String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("list",list);
    }

    /**
     * 添加章节
     * @param eduChapter
     * @return
     */
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        chapterService.save(eduChapter);
        return R.ok();
    }

    /**
     * 根据章节ID查询所有章节信息
     * @param chapterId
     * @return
     */
    @GetMapping("/getChapterInfo/{chapterId}")
    public R getChapterInfoById(@PathVariable("chapterId") String chapterId){
        EduChapter eduChapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }

    /**
     * 修改章节信息
     * @param eduChapter
     * @return
     */
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        chapterService.updateById(eduChapter);
        return R.ok();
    }

    /**
     * 根据ID删除章节
     * @param chapterId
     * @return
     */
    @DeleteMapping("/deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable("chapterId") String chapterId){
        boolean flag = chapterService.daleteChapter(chapterId);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }
}

