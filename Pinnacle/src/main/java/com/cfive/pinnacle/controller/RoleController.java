package com.cfive.pinnacle.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cfive.pinnacle.entity.Role;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    private IRoleService roleService;

    @Autowired
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseResult getAllRole() {
        List<Role> roles = roleService.getAllRole();
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", roles);
    }

    @PostMapping
    public ResponseResult addRole(@RequestBody Role role) {
        if (roleService.addRole(role)) {
            return ResponseResult.build(ResponseCode.DATABASE_SAVE_OK, "success", null);
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_DELETE_ERROR, "error", null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable Long id) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getId, id);
        if (roleService.remove(wrapper)) {
            return ResponseResult.build(ResponseCode.DATABASE_DELETE_OK, "success", null);
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_DELETE_ERROR, "error", null);
        }
    }

    @PutMapping()
    public ResponseResult modifyRole(@RequestBody Role role) {
        if (roleService.modifyRole(role)) {
            return ResponseResult.build(ResponseCode.DATABASE_SAVE_OK, "success", null);
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_DELETE_ERROR, "error", null);
        }
    }
}
