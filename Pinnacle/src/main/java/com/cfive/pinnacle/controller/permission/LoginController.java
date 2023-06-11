package com.cfive.pinnacle.controller.permission;

import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.entity.permission.User;
import com.cfive.pinnacle.service.permission.ILoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Tag(name = "登录", description = "登录相关接口")
@RestController
public class LoginController {

    private ILoginService loginService;

    @Autowired
    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    public ResponseResult<HashMap<String, String>> login(@RequestBody User user) {
        HashMap<String, String> hashMap = loginService.login(user);
        return ResponseResult.build(ResponseCode.LOGIN_SUCCESS, "Login success", hashMap);
    }

    @Operation(summary = "登出")
    @RequestMapping("/logout")
    public ResponseResult<?> logout(HttpServletRequest request) {
        boolean result = loginService.logout(request.getHeader("token"));
        if (result) {
            return ResponseResult.build(ResponseCode.LOGOUT_SUCCESS, "Logout success", null);
        } else {
            return ResponseResult.build(ResponseCode.LOGOUT_FAILED, "Logout failed", null);
        }
    }

    @Operation(summary = "更新 Token")
    @GetMapping("/token")
    public ResponseResult<HashMap<String, String>> renewToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        HashMap<String, String> hashMap = loginService.renewToken(token);
        return ResponseResult.build(ResponseCode.TOKEN_RENEW_SUCCESS, "Token renew success", hashMap);
    }
}
