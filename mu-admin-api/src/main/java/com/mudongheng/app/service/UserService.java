package com.mudongheng.app.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.mudongheng.app.model.param.LoginParam;

/**
 * @author 幕冬
 * @since 2021年10月24日
 */
public interface UserService {

    SaTokenInfo login(LoginParam loginParam);


}