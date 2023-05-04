package com.cfive.pinnacle.controller.permission;

import com.cfive.pinnacle.entity.User;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.permission.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class LoginController {

    private ILoginService loginService;

    @Autowired
    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {
        HashMap<String, String> hashMap = loginService.login(user);
        return ResponseResult.build(ResponseCode.LOGIN_SUCCESS, "success", hashMap);
    }
}
