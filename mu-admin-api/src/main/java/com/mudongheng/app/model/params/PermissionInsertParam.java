package com.mudongheng.app.model.params;

import javax.validation.constraints.NotBlank;

/**
 * @author 幕冬
 * @since 2022年05月24日
 */
public record PermissionInsertParam(
        @NotBlank(message = "不能为空")
        String permissionName,
        @NotBlank(message = "不能为空")
        String permissionNote
) {
}
