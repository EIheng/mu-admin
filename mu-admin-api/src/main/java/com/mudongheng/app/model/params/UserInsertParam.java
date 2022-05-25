package com.mudongheng.app.model.params;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 幕冬
 * @since 2022年05月24日
 */
public record UserInsertParam(
        @Size(min = 3, max = 32, message = "账号只能为3~32位")
        @NotBlank(message = "不能为空")
        String username,
        @Size(min = 6, max = 32, message = "密码只能为6~32位")
        @NotBlank(message = "密码不能为空")
        String password,
        @NotNull
        Integer roleId
) {
}
