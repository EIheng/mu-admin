package com.mudongheng.app.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mudongheng.app.exception.ParamException;
import com.mudongheng.app.model.entity.SysUser;
import com.mudongheng.app.model.params.LoginParam;
import com.mudongheng.app.model.params.RegisterParam;
import com.mudongheng.app.model.vo.TokenInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author 幕冬
 * @since 2022年05月22日
 */
@Slf4j
@Service
public class AuthService {

    private final SysUserService sysUserService;

    private final PasswordEncoder passwordEncoder;

    public AuthService(SysUserService sysUserService, PasswordEncoder passwordEncoder) {
        this.sysUserService = sysUserService;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenInfo login(LoginParam param) {
        String username = param.username();
        String password = param.password();

        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("username", username));

        // 用户不存在、账号错误或密码错误
        if (sysUser == null ||
                !sysUser.getUsername().equals(username) ||
                !passwordEncoder.matches(password, sysUser.getPassword())) {
            log.info("登录失败，参数：{}", param);
            throw new ParamException("登录失败，账号密码错误!");
        }

        StpUtil.login(sysUser.getId());
        log.info("用户{} 登录成功！", sysUser.getId());

        return new TokenInfo(StpUtil.getTokenName(), StpUtil.getTokenValue());
    }

    public TokenInfo register(RegisterParam param) {
        SysUser sysUser = new SysUser();
        sysUser.setSysRoleId(1);
        sysUser.setUsername(param.username());
        sysUser.setPassword(passwordEncoder.encode(param.password()));

        log.info("注册用户：{}", sysUser);

        if (!sysUserService.save(sysUser)) {
            throw new RuntimeException("该用户名已存在！");
        }

        StpUtil.login(sysUser.getId());
        return new TokenInfo(StpUtil.getTokenName(), StpUtil.getTokenValue());
    }

    public boolean removeAccount(Integer id) {
        return sysUserService.removeById(id);
    }
}
