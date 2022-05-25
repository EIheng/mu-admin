package com.mudongheng.app.model.params;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 幕冬
 * @since 2022年05月24日
 */
public record UserUpdateParam(
        @NotNull(message = "不能为空")
        Integer id,
        @NotBlank(message = "不能为空")
        String username,
        @NotNull
        Integer roleId
) {
}
