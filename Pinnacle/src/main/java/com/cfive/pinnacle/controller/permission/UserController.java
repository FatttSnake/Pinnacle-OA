package com.cfive.pinnacle.controller.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cfive.pinnacle.entity.permission.User;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.permission.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户", description = "用户相关接口")
public class UserController {
    private IUserService userService;

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    @Operation(summary = "获取当前用户信息")
    public ResponseResult<User> getInfo() {
        return ResponseResult.databaseSelectSuccess(userService.getInfo());
    }

    @GetMapping("/affair")
    @PreAuthorize("hasAuthority('affair:self:add')")
    @Operation(summary = "获取拥有审批权限的用户")
    public ResponseResult<List<User>> getAffairUser() {
        return ResponseResult.databaseSelectSuccess(userService.getAffairUser());
    }

    @GetMapping("/department")
    @PreAuthorize("hasAuthority('attendance:manage:modify')")
    @Operation(summary = "获取同部门下所有用户")
    public ResponseResult<List<User>> getDepartmentUser() {
        return ResponseResult.databaseSaveSuccess(userService.getDepartmentUser());
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('system:user:get', 'system:user:add', 'system:user:modify')")
    @Operation(summary = "获取所有用户（权限管理相关）")
    public ResponseResult<List<User>> getAllUser() {
        List<User> users = userService.getAllUser();
        return ResponseResult.databaseSelectSuccess(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:user:one')")
    @Operation(summary = "获取单个用户（权限管理相关）")
    public ResponseResult<User> getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return ResponseResult.databaseSelectSuccess(user);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('system:user:add')")
    @Operation(summary = "添加用户（权限管理相关）")
    public ResponseResult<User> addUser(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUsername())) {
            return ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "Username cannot be empty", null);
        }
        if (!StringUtils.hasText(user.getPasswd())) {
            return ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "Password cannot be empty", null);
        }
        if (userService.addUser(user)) {
            return ResponseResult.databaseSaveSuccess(user);
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "error", null);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:user:delete')")
    @Operation(summary = "删除用户（权限管理相关）")
    public ResponseResult<?> deleteUser(@PathVariable Long id) {
        if (id == 1L) {
            return ResponseResult.build(ResponseCode.DATABASE_DELETE_ERROR, "Unable to remove super admin", null);
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, id);
        if (userService.remove(wrapper)) {
            return ResponseResult.databaseDeleteSuccess();
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_DELETE_ERROR, "error", null);
        }
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('system:user:modify')")
    @Operation(summary = "修改用户（权限管理相关）")
    public ResponseResult<User> modifyUser(@RequestBody User user) {
        if (user.getId() == 1L && user.getEnable() == 0) {
            return ResponseResult.build(ResponseCode.DATABASE_DELETE_ERROR, "Unable to disable super admin", null);
        }
        if (!StringUtils.hasText(user.getUsername())) {
            return ResponseResult.build(ResponseCode.DATABASE_UPDATE_ERROR, "Username cannot be empty", null);
        }
        if (!StringUtils.hasText(user.getPasswd())) {
            user.setPasswd(null);
        }
        if (userService.modifyUser(user)) {
            return ResponseResult.databaseUpdateSuccess(user);
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_UPDATE_ERROR, "error", null);
        }
    }
}
