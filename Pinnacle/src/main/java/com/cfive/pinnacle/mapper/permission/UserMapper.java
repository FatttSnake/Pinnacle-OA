package com.cfive.pinnacle.mapper.permission;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cfive.pinnacle.entity.permission.User;
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
    IPage<User> getAll(IPage<User> page);

    List<User> getAllWithRoleAndGroup(@Param("userList") List<User> userList);

    List<User> getAllAffairUser();

    List<User> getAllDepartmentUser(@Param("departmentId")long departmentId);

    User getOneById(@Param("id") long id);

    User getOneWithPowerByUsername(@Param("username") String username);
}
