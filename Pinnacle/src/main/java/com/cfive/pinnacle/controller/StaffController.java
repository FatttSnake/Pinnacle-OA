package com.cfive.pinnacle.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.entity.permission.User;
import com.cfive.pinnacle.service.IStaffService;
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
@RestController
@RequestMapping("/staff")
public class StaffController {
    private IStaffService staffService;

    @Autowired
    public void setStaffService(IStaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('staff:manege:get', 'staff:admin:get')")
    public ResponseResult<IPage<User>> getAllStaff(Long currentPage, Long pageSize, Integer searchType, String searchInput, Integer searchGender, String searchBirthFrom, String searchBirthTo, Integer searchRegex) {
        return ResponseResult.databaseSelectSuccess(staffService.getAllStaff(currentPage, pageSize, searchType, searchInput, searchGender, searchBirthFrom, searchBirthTo, searchRegex));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('staff:manege:modify', 'staff:admin:modify')")
    public ResponseResult<?> modifyStaff(@RequestBody User user) {
        if (staffService.modifyStaff(user)) {
            return ResponseResult.databaseUpdateSuccess(null);
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "error", null);
        }
    }
}
