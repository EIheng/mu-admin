package com.mudongheng.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mudongheng.app.model.entity.SysPermission;
import com.mudongheng.app.model.entity.SysRolePermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 幕冬
 * @since 2022年05月22日
 */
@Mapper
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {
}