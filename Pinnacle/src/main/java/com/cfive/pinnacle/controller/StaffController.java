package com.cfive.pinnacle.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cfive.pinnacle.entity.Staff;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.entity.permission.User;
import com.cfive.pinnacle.service.IStaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 员工 前端控制器
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Tag(name = "员工信息", description = "员工信息相关接口")
@RestController
@RequestMapping("/staff")
public class StaffController {
    private IStaffService staffService;

    @Autowired
    public void setStaffService(IStaffService staffService) {
        this.staffService = staffService;
    }

    @Operation(summary = "获取员工信息")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('staff:manege:get', 'staff:admin:get')")
    public ResponseResult<IPage<User>> getAllStaff(Long currentPage, Long pageSize, Integer searchType, String searchInput, Integer searchGender, String searchBirthFrom, String searchBirthTo, Integer searchRegex) {
        return ResponseResult.databaseSelectSuccess(staffService.getAllStaff(currentPage, pageSize, searchType, searchInput, searchGender, searchBirthFrom, searchBirthTo, searchRegex));
    }

    @Operation(summary = "修改员工信息")
    @PutMapping
    @PreAuthorize("hasAnyAuthority('staff:manege:modify', 'staff:admin:modify')")
    public ResponseResult<?> modifyStaff(@RequestBody User user) {
        if (staffService.modifyStaff(user)) {
            return ResponseResult.databaseUpdateSuccess(null);
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "error", null);
        }
    }

    @Operation(summary = "修改档案信息")
    @PutMapping("self")
    public ResponseResult<?> modifySelf(@RequestBody Staff staff) {
        if (staffService.modifySelf(staff)) {
            return ResponseResult.databaseUpdateSuccess(null);
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "error", null);
        }
    }
}
