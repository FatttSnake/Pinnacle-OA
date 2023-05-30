package com.cfive.pinnacle.controller;

import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.entity.permission.User;
import com.cfive.pinnacle.service.IStaffService;
import com.cfive.pinnacle.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 员工 前端控制器
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Slf4j
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
    public ResponseResult<List<User>> getAllStaff() {
        return ResponseResult.databaseSelectSuccess(staffService.getAllStaff(WebUtil.hasAuthority("staff:admin:get") ? null : WebUtil.getLoginUser().getUser().getDepartmentId()));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('staff:manege:modify', 'staff:admin:modify')")
    public ResponseResult<?> modifyStaff(@RequestBody User user) {
        log.info(user.toString());
        if (staffService.modifyStaff(user)) {
            return ResponseResult.databaseUpdateSuccess(null);
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "error", null);
        }
    }
}
