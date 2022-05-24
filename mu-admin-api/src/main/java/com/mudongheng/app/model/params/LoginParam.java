package com.mudongheng.app.model.params;

import javax.validation.constraints.NotBlank;

/**
 * @author 幕冬
 * @since 2022年04月18日
 */
public record LoginParam(
        @NotBlank(message = "用户名不能为空")
        String username,
        @NotBlank(message = "密码不能为空")
        String password
) {
}
