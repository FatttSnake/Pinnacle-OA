package com.cfive.pinnacle.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cfive.pinnacle.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 部门 服务类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
public interface IDepartmentService extends IService<Department> {

    List<Department> getDepartmentWithUser();

    IPage<Department> getAllDepartment(Long currentPage, Long pageSize, Integer searchType, String searchInput, Integer searchRegex);
}
