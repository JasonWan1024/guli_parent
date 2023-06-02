package com.wyl.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyl.eduservice.entity.EduSubject;
import com.wyl.eduservice.entity.excel.SubjectData;
import com.wyl.eduservice.entity.subject.OneSubject;
import com.wyl.eduservice.entity.subject.TwoSubject;
import com.wyl.eduservice.listener.SubjectExcelListener;
import com.wyl.eduservice.mapper.EduSubjectMapper;
import com.wyl.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-03-27
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    /**
     * 添加课程分类
     * @param file
     * @param subjectService
     */
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream is = file.getInputStream();
            EasyExcel.read(is, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有一级二级课程分类（实现返回特定树形结构）
     * @return
     */
    @Override
    public List<OneSubject> getAllOnwTwoSubject() {
        //获取一级课程分类 parent_id=0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
        //获取二级课程分类 parent_id!=0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);
        //最终需要返回的特定格式的课程分类集合
        List<OneSubject> finalSubjectList = new ArrayList<>();
        //封装一级分类
        for (int i = 0; i < oneSubjectList.size(); i++){
            //获取一级分类返回对象
            EduSubject eduSubject = oneSubjectList.get(i);
            //创建新的一级分类对象
            OneSubject oneSubject = new OneSubject();
            //使用工具类将查询到的一级分类数据放入创建的一级分类对象
            BeanUtils.copyProperties(eduSubject, oneSubject);
            //将此对象放入最终集合
            finalSubjectList.add(oneSubject);

            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for (int j = 0; j < twoSubjectList.size(); j++) {
                EduSubject tSubject = twoSubjectList.get(j);
                if (tSubject.getParentId().equals(eduSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject, twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinalSubjectList);
        }
        //封装二级分类
        return finalSubjectList;
    }
}
