package com.cfive.pinnacle.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cfive.pinnacle.entity.Department;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.exception.DataValidationFailedException;
import com.cfive.pinnacle.service.IDepartmentService;
import com.cfive.pinnacle.utils.WebUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

/**
 * <p>
 * 部门 前端控制器
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@RestController
@RequestMapping("/department")
@Tag(name = "部门", description = "部门相关接口")
public class DepartmentController {
    private IDepartmentService departmentService;

    @Autowired
    public void setDepartmentService(IDepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    //获取所有部门及其各部门所属成员
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('notice:manage:add')")
    public ResponseResult<List<Department>> getDepartAndUser() {
        return ResponseResult.databaseSelectSuccess(departmentService.getDepartmentWithUser());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('department:admin:get')")
    public ResponseResult<IPage<Department>> getAllDepartment(Long currentPage, Long pageSize, Integer searchType, String searchInput, Integer searchRegex) {
        return ResponseResult.databaseSelectSuccess(departmentService.getAllDepartment(currentPage, pageSize, searchType, searchInput, searchRegex));
    }

    @GetMapping("list")
    @PreAuthorize("hasAnyAuthority('staff:manege:modify', 'staff:admin:modify')")
    public ResponseResult<List<Department>> getDepartmentList() {
        List<Department> departmentList;
        if (WebUtil.hasAuthority("staff:admin:modify")) {
            departmentList = departmentService.list();
        } else {
            LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Department::getId, WebUtil.getLoginUser().getUser().getDepartmentId());
            departmentList = departmentService.list(wrapper);
        }
        return ResponseResult.databaseSelectSuccess(departmentList);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('department:admin:add')")
    public ResponseResult<?> addDepartment(@RequestBody Department department) {
        if (Pattern.matches("[0-9-()（）]{7,18}", department.getTel())) {
            throw new DataValidationFailedException();
        }
        if (departmentService.save(department)) {
            return ResponseResult.databaseSaveSuccess(null);
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "error", null);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('department:admin:modify')")
    public ResponseResult<?> modifyDepartment(@RequestBody Department department) {
        if (Pattern.matches("[0-9-()（）]{7,18}", department.getTel())) {
            throw new DataValidationFailedException();
        }
        if (departmentService.updateById(department)) {
            return ResponseResult.databaseUpdateSuccess(null);
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "error", null);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('department:admin:delete')")
    public ResponseResult<?> deleteDepartment(@PathVariable Long id) {
        if (departmentService.removeById(id)) {
            return ResponseResult.databaseDeleteSuccess();
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_DELETE_ERROR, "error", null);
        }
    }
}
