package com.cfive.pinnacle.controller.permission;

import com.cfive.pinnacle.entity.User;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.permission.ILoginService;
import com.cfive.pinnacle.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
public class LoginController {

    private ILoginService loginService;

    @Autowired
    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {
        HashMap<String, String> hashMap = loginService.login(user);
        return ResponseResult.build(ResponseCode.LOGIN_SUCCESS, "Login Success", hashMap);
    }

    @RequestMapping("/logout")
    public ResponseResult logout() {
        boolean result = loginService.logout();
        if (result) {
            return ResponseResult.build(ResponseCode.LOGOUT_SUCCESS, "Logout Success", null);
        } else {
            return ResponseResult.build(ResponseCode.LOGOUT_FAILED, "Logout Failed", null);
        }
    }

    @GetMapping("/userInfo")
    public ResponseResult getUserInfo() {
        return ResponseResult.success(WebUtil.getLoginUser());
    }
}
