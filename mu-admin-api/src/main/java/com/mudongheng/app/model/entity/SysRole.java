package com.mudongheng.app.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 幕冬
 * @since 2022年05月23日
 */
@Data
@TableName(value = "sys_role")
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色名称（显示）
     */
    private String roleNote;

}
