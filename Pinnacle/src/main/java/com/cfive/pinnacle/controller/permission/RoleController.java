package com.cfive.pinnacle.controller.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cfive.pinnacle.entity.permission.Role;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.permission.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
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
@Tag(name = "角色", description = "角色相关接口")
public class RoleController {

    private IRoleService roleService;

    @Autowired
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(summary = "获取所有角色")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('system:role:get', 'system:role:add', 'system:role:delete', 'system:role:modeify', 'system:group:add', 'system:group:modify', 'system:user:add', 'system:user:modify')")
    public ResponseResult<List<Role>> getAllRole() {
        List<Role> roles = roleService.getAllRole();
        return ResponseResult.databaseSelectSuccess(roles);
    }

    @Operation(summary = "添加角色")
    @PostMapping
    @PreAuthorize("hasAuthority('system:role:add')")
    public ResponseResult<Role> addRole(@RequestBody Role role) {
        if (!StringUtils.hasText(role.getName())) {
            return ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "Name cannot be empty", null);
        }
        if (roleService.addRole(role)) {
            return ResponseResult.databaseSaveSuccess(role);
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "error", null);
        }
    }

    @Operation(summary = "删除角色")
    @Parameters({
            @Parameter(name = "id", description = "角色ID", in = ParameterIn.PATH)
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:role:delete')")
    public ResponseResult<?> deleteRole(@PathVariable Long id) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getId, id);
        if (roleService.remove(wrapper)) {
            return ResponseResult.databaseDeleteSuccess();
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_DELETE_ERROR, "error", null);
        }
    }

    @Operation(summary = "修改角色")
    @PutMapping()
    @PreAuthorize("hasAuthority('system:role:modify')")
    public ResponseResult<Role> modifyRole(@RequestBody Role role) {
        if (!StringUtils.hasText(role.getName())) {
            return ResponseResult.build(ResponseCode.DATABASE_UPDATE_ERROR, "Name cannot be empty", null);
        }
        if (roleService.modifyRole(role)) {
            return ResponseResult.databaseUpdateSuccess(role);
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_UPDATE_ERROR, "error", null);
        }
    }
}
