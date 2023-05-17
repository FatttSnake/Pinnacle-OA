package com.cfive.pinnacle.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cfive.pinnacle.entity.User;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserController {
    private IUserService userService;

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseResult getAllUser() {
        List<User> users = userService.getAllUser();
        return ResponseResult.databaseSelectSuccess(users);
    }

    @GetMapping("/{id}")
    public ResponseResult getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return ResponseResult.databaseSelectSuccess(user);
    }

    @PostMapping
    public ResponseResult addUser(@RequestBody User user) {
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
    public ResponseResult deleteRole(@PathVariable Long id) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, id);
        if (userService.remove(wrapper)) {
            return ResponseResult.databaseDeleteSuccess();
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_DELETE_ERROR, "error", null);
        }
    }

    @PutMapping()
    public ResponseResult modifyRole(@RequestBody User user) {
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
