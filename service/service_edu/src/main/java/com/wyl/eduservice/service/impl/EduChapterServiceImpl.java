package com.wyl.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyl.eduservice.entity.EduChapter;
import com.wyl.eduservice.entity.EduVideo;
import com.wyl.eduservice.entity.chapter.ChapterVo;
import com.wyl.eduservice.entity.chapter.VideoVo;
import com.wyl.eduservice.mapper.EduChapterMapper;
import com.wyl.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyl.eduservice.service.EduVideoService;
import com.wyl.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-03-29
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    /**
     * 根据课程id查询章节和小节
     *
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        // 根据课程id查询所有章节数据
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);
        // 根据课程id查询所有小节数据
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);
        // 最终需要返回的集合
        List<ChapterVo> finalList = new ArrayList<>();
        // 遍历章节集合里的所有数据 放入最终集合
        for (int i = 0; i < eduChapterList.size(); i++) {
            // 遍历章节数据
            EduChapter eduChapter = eduChapterList.get(i);
            // 新建返回对象
            ChapterVo chapterVo = new ChapterVo();
            // 使用工具类
            BeanUtils.copyProperties(eduChapter, chapterVo);
            // 放入最终集合
            finalList.add(chapterVo);
            // 用于接收小节数据
            List<VideoVo> finalVideoVoList = new ArrayList<>();
            // 循环遍历小节数据
            for (int j = 0; j < eduVideoList.size(); j++) {
                // 取到小节数据
                EduVideo eduVideo = eduVideoList.get(j);
                // 判断小节中的chapter_id是否和章节id相等
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    finalVideoVoList.add(videoVo);
                }
            }
            // 将小节数据设置到chaptervo的Children中
            chapterVo.setChildren(finalVideoVoList);
        }
        return finalList;
    }

    /**
     * 根据ID删除章节
     * @param chapterId
     */
    @Override
    public boolean daleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        //在小节表中根据章节ID查询小节数据
        wrapper.eq("chapter_id",chapterId);
        //返回查询到的数据数量
        int count = videoService.count(wrapper);
        //如果有小节数据 则不删除
        if (count > 0) {
            throw new GuliException(20001,"无法删除");
        }else {
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        }
    }

    /**
     * 根据课程id删除章节
     * @param id
     */
    @Override
    public void removeChapterByCourseId(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
