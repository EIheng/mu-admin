package com.mudongheng.app.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.mudongheng.app.model.param.LoginParam;
import com.mudongheng.app.model.vo.LoginResult;
import com.mudongheng.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author 幕冬
 * @since 2021年10月26日
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResult login(@RequestBody LoginParam loginParam) {
        SaTokenInfo tokenInfo = userService.login(loginParam);
        if (tokenInfo != null) {
            return new LoginResult(true, "登录成功！", tokenInfo);
        } else {
            return new LoginResult(false, "登录失败！", null);
        }
    }

    @PostMapping("/login-out")
    public LoginResult loginOut() {
        StpUtil.logout();
        return new LoginResult(false, "已登出", null);
    }

    @SaCheckLogin
    @PostMapping("/get-token")
    public SaTokenInfo getToken() {
        return StpUtil.getTokenInfo();
    }

    @PostMapping("/is-login")
    public SaResult isLogin() {
        return SaResult.ok(StpUtil.isLogin() ? "已登录" : "未登录").setData(StpUtil.isLogin());
    }

}
