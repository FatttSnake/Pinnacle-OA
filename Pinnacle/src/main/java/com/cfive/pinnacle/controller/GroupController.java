package com.cfive.pinnacle.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cfive.pinnacle.entity.Group;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GroupController {
    private IGroupService groupService;

    @Autowired
    public void setGroupService(IGroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseResult getAllGroup() {
        List<Group> groups = groupService.getAllGroup();
        return ResponseResult.databaseSelectSuccess(groups);
    }

    @PostMapping
    public ResponseResult addGroup(@RequestBody Group group) {
        if (!StringUtils.hasText(group.getName())) {
            return ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "Name cannot be empty", null);
        }
        if (groupService.addGroup(group)) {
            return ResponseResult.databaseSaveSuccess(group);
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "error", null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteGroup(@PathVariable Long id) {
        LambdaQueryWrapper<Group> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Group::getId, id);
        if (groupService.remove(wrapper)) {
            return ResponseResult.databaseDeleteSuccess();
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_DELETE_ERROR, "error", null);
        }
    }

    @PutMapping
    public ResponseResult modifyGroup(@RequestBody Group group) {
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
