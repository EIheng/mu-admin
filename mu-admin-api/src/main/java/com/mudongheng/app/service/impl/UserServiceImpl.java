package com.mudongheng.app.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.mudongheng.app.model.entity.User;
import com.mudongheng.app.model.param.LoginParam;
import com.mudongheng.app.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author 幕冬
 * @since 2021年10月24日
 */
@Service
public class UserServiceImpl implements UserService {

    private final User user;

    public UserServiceImpl() {
        user = new User();

        user.setId(1);
        user.setUsername("root");
        user.setPassword("123456");
    }

    @Override
    public SaTokenInfo login(LoginParam loginParam) {

        String username = loginParam.getUsername();
        String password = loginParam.getPassword();

        // 表单验证
        if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
            StpUtil.login(user.getId());
            return StpUtil.getTokenInfo();
        } else {
            return null;
        }
    }
    
}




