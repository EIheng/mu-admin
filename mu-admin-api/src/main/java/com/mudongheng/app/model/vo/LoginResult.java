package com.mudongheng.app.model.vo;

import cn.dev33.satoken.stp.SaTokenInfo;
import lombok.Data;

/**
 * @author 幕冬
 * @since 2022年01月07日
 */
@Data
public class LoginResult {

    public LoginResult(boolean loginState, String info, SaTokenInfo tokenInfo) {
        this.loginState = loginState;
        this.info = info;
        this.tokenInfo = tokenInfo;
    }

    private boolean loginState;

    private String info;

    private SaTokenInfo tokenInfo;

}
