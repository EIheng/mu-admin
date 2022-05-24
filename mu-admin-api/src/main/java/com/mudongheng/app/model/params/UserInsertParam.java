package com.mudongheng.app.model.params;

import javax.validation.constraints.NotBlank;

/**
 * @author 幕冬
 * @since 2022年05月24日
 */
public record UserInsertParam(
        @NotBlank(message = "不能为空")
        String username,
        @NotBlank(message = "不能为空")
        String roleName
) {
}
