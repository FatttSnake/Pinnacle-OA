package com.cfive.pinnacle.mapper;

import com.cfive.pinnacle.entity.Staff;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cfive.pinnacle.entity.permission.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 员工 Mapper 接口
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Mapper
public interface StaffMapper extends BaseMapper<Staff> {
    List<User> getAllStaff(@Param("departmentId")Long departmentId);
}
