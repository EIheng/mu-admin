package com.mudongheng.app.model.params;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 幕冬
 * @since 2022年05月23日
 */
public record PageParam(
        @Min(value = 1, message = "页数不能小于1")
        @NotNull(message = "不能为空")
        Integer cur
) {
}
