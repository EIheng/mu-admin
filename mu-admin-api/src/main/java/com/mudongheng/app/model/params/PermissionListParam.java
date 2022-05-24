package com.mudongheng.app.model.params;

import javax.validation.constraints.NotNull;

/**
 * @author 幕冬
 * @since 2022年05月24日
 */
public record PermissionListParam(
        @NotNull(message = "不能为空")
        String roleName
) {
}
