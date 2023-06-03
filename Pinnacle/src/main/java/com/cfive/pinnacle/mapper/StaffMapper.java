package com.cfive.pinnacle.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cfive.pinnacle.entity.Staff;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cfive.pinnacle.entity.permission.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    IPage<User> getAllStaff(IPage<User> page, @Param("departmentId") Long departmentId, @Param("searchType") Integer searchType, @Param("searchInput") String searchInput, @Param("searchGender") Integer searchGender, @Param("searchBirthFrom") String searchBirthFrom, @Param("searchBirthTo") String searchBirthTo, @Param("searchRegex") Integer searchRegex);
}
