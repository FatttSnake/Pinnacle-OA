package com.cfive.pinnacle.mapper.permission;

import com.cfive.pinnacle.entity.permission.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    List<Long> filterRoleByPowerId(@Param("roleList") List<Long> roleList, @Param("powerId") Long powerId, @Param("searchName") String searchName, @Param("searchEnable") Integer searchEnable, @Param("searchRegex") Integer searchRegex);

    List<Role> getAll(@Param("roleList") List<Role> roleList);

    Role getOneById(@Param("id") long id);
}
