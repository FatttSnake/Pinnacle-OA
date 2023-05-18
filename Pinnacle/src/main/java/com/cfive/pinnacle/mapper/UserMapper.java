package com.cfive.pinnacle.mapper;

import com.cfive.pinnacle.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> getAll();

    User getOneById(@Param("id") long id);

    User getOneWithPowerByUsername(@Param("username") String username);
}
