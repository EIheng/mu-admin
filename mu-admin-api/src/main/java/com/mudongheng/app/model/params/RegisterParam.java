package com.mudongheng.app.model.params;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author 幕冬
 * @since 2022年04月18日
 */
public record RegisterParam(

        @Size(min = 6, max = 32, message = "账号只能为6~32位")
        @NotBlank(message = "用户名不能为空")
        String username,

        @Size(min = 6, max = 32, message = "密码只能为6~32位")
        @NotBlank(message = "密码不能为空")
        String password
) {
}