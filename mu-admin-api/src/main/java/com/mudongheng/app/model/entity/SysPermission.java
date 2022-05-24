package com.mudongheng.app.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 幕冬
 * @since 2022年05月22日
 */
@Data
@TableName(value ="sys_permission")
@EqualsAndHashCode(callSuper = true)
public class SysPermission extends BaseEntity {

    /**
     * 许可名称，用于鉴权，常见格式为 goods-add、goods-delete 和 goods-*
     */
    private String permissionName;

    /**
     * 许可名称（显示）
     */
    private String permissionNote;

}
