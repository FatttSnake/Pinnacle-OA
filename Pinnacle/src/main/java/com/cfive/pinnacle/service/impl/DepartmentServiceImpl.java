package com.cfive.pinnacle.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.cfive.pinnacle.entity.Department;
import com.cfive.pinnacle.mapper.DepartmentMapper;
import com.cfive.pinnacle.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 部门 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {
    private DepartmentMapper departmentMapper;

    @Autowired
    public void setDepartmentMapper(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    @Override
    public List<Department> getDepartmentWithUser() {
        return departmentMapper.getDepartmentWithUser();
    }

    @Override
    public IPage<Department> getAllDepartment(Long currentPage, Long pageSize, Integer searchType, String searchInput, Integer searchRegex) {
        IPage<Department> departmentIPage;
        if (currentPage == null || pageSize == null) {
            departmentIPage = PageDTO.of(0, -1);
        } else {
            departmentIPage = PageDTO.of(currentPage, pageSize);
        }
        searchInput = searchInput.trim();

        return departmentMapper.getAllDepartment(departmentIPage, searchType, searchInput, searchRegex);
    }
}
