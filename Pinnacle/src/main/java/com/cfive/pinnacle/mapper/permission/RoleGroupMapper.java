package com.cfive.pinnacle.mapper.permission;

import com.cfive.pinnacle.entity.permission.RoleGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 中间表-角色-用户组 Mapper 接口
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Mapper
public interface RoleGroupMapper extends BaseMapper<RoleGroup> {

}
