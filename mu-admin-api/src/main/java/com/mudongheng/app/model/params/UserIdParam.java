package com.mudongheng.app.model.params;

import javax.validation.constraints.NotNull;

/**
 * @author 幕冬
 * @since 2022年05月23日
 */
public record UserIdParam(
        @NotNull
        Integer userId
) {
}
