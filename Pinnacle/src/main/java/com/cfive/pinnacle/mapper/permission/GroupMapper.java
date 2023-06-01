package com.cfive.pinnacle.mapper.permission;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cfive.pinnacle.entity.permission.Group;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    IPage<Group> getAll(IPage<Group> groupIPage);

    Group getOneById(@Param("id") long id);
}
