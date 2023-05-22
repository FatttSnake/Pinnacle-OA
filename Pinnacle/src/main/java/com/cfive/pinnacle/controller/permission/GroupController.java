package com.cfive.pinnacle.controller.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cfive.pinnacle.entity.permission.Group;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.permission.IGroupService;
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
 * 用户组 前端控制器
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@RestController
@RequestMapping("/group")
@Tag(name = "用户组", description = "用户组相关接口")
public class GroupController {
    private IGroupService groupService;

    @Autowired
    public void setGroupService(IGroupService groupService) {
        this.groupService = groupService;
    }

    @Operation(summary = "获取所有用户组")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('system:group:all', 'system:group:add', 'system:group:delete', 'system:group:modify', 'system:user:add', 'system:user:modify')")
    public ResponseResult<List<Group>> getAllGroup() {
        List<Group> groups = groupService.getAllGroup();
        return ResponseResult.databaseSelectSuccess(groups);
    }

    @Operation(summary = "添加用户组")
    @PostMapping
    @PreAuthorize("hasAuthority('system:group:add')")
    public ResponseResult<Group> addGroup(@RequestBody Group group) {
        if (!StringUtils.hasText(group.getName())) {
            return ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "Name cannot be empty", null);
        }
        if (groupService.addGroup(group)) {
            return ResponseResult.databaseSaveSuccess(group);
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "error", null);
        }
    }

    @Operation(summary = "删除用户组")
    @Parameters({
            @Parameter(name = "id", description = "用户组ID", in = ParameterIn.PATH)
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:group:delete')")
    public ResponseResult<?> deleteGroup(@PathVariable Long id) {
        LambdaQueryWrapper<Group> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Group::getId, id);
        if (groupService.remove(wrapper)) {
            return ResponseResult.databaseDeleteSuccess();
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_DELETE_ERROR, "error", null);
        }
    }

    @Operation(summary = "修改用户组")
    @PutMapping
    @PreAuthorize("hasAuthority('system:group:modify')")
    public ResponseResult<Group> modifyGroup(@RequestBody Group group) {
        if (!StringUtils.hasText(group.getName())) {
            return ResponseResult.build(ResponseCode.DATABASE_UPDATE_ERROR, "Name cannot be empty", null);
        }
        if (groupService.modifyGroup(group)) {
            return ResponseResult.databaseUpdateSuccess(group);
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_UPDATE_ERROR, "error", null);
        }
    }
}
