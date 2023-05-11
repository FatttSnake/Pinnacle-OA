package com.cfive.pinnacle.mapper;

import com.cfive.pinnacle.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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
    List<Department> getDepartAndUser();
}
