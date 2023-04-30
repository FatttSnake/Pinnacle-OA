package com.cfive.pinnacle.mapper;

import com.cfive.pinnacle.entity.UserGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 中间表-用户-用户组 Mapper 接口
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Mapper
public interface UserGroupMapper extends BaseMapper<UserGroup> {

}
