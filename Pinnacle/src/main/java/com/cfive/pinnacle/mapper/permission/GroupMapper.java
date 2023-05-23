package com.cfive.pinnacle.mapper.permission;

import com.cfive.pinnacle.entity.permission.Group;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户组 Mapper 接口
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Mapper
public interface GroupMapper extends BaseMapper<Group> {
    List<Group> getAll();

    Group getOneById(@Param("id") long id);
}