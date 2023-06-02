package com.wyl.educenter.mapper;

import com.wyl.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2023-04-23
 */
@Mapper
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    /**
     * 统计每日注册人数
     * @param day
     * @return
     */
    Integer countRegisterByDay(String day);
}
