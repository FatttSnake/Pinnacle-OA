package com.cfive.pinnacle.controller;

import com.cfive.pinnacle.entity.User;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        List<User> users = userService.getBasicInfo();
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", users);
    }

    @GetMapping("/{id}")
    public ResponseResult getUser(@PathVariable int id) {
        User user = userService.getBasicInfo(id);
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", user);
    }
}
