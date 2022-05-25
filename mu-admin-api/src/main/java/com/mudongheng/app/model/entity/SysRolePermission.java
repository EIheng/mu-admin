package com.mudongheng.app.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色所拥有的权限
 *
 * @author 幕冬
 * @since 2022年05月23日
 */
@Data
@TableName(value = "sys_role_permission")
@EqualsAndHashCode(callSuper = true)
public class SysRolePermission extends BaseEntity {

    /**
     * 角色id
     */
    private Integer sysRoleId;

    /**
     * 权限id
     */
    private Integer sysPermissionId;

}
