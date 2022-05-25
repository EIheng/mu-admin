package com.mudongheng.app.model.params;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 幕冬
 * @since 2022年05月24日
 */
public record RoleUpdatePermissionParam(
        @NotNull
        Integer roleId,
        @NotNull
        List<Integer> permissionIdList
) {
}
