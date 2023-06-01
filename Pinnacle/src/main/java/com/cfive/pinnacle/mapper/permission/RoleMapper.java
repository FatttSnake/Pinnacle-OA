package com.cfive.pinnacle.mapper.permission;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cfive.pinnacle.entity.permission.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    IPage<Role> getAll(IPage<Role> roleIPage);

    Role getOneById(@Param("id") long id);
}
