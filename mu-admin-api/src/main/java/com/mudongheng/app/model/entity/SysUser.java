package com.mudongheng.app.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 幕冬
 * @since 2022年05月22日
 */
@Data
@TableName(value ="sys_user")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {

    /**
     * 用户的角色为
     */
    private Integer sysRoleId;

    private String username;

    private String password;

}
