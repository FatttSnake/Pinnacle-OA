package com.cfive.pinnacle.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cfive.pinnacle.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 部门 Mapper 接口
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {
    @Deprecated
    List<Department> getDepartAndUser();

    IPage<Department> getAllDepartment(IPage<Department> page, @Param("searchType") Integer searchType, @Param("searchInput") String searchInput, @Param("searchRegex") Integer searchRegex);
}
