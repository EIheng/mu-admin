package com.mudongheng.app.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mudongheng.app.mapper.SysRolePermissionMapper;
import com.mudongheng.app.model.entity.SysRolePermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author 幕冬
 * @since 2022年05月22日
 */
@Slf4j
@Service
public class SysRolePermissionService extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> {

    /**
     * 该角色已有的权限，注意只包含记录id与权限id
     *
     * @param roleId 权限id
     * @return 权限列表
     */
    public List<SysRolePermission> listByRoleId(int roleId) {
        QueryWrapper<SysRolePermission> qw = new QueryWrapper<>();
        qw.eq("sys_role_id", roleId).select("id", "sys_permission_id");
        return list(qw);
    }

    /**
     * 将权限id列表转换为角色权限存入数据库
     *
     * @param roleId 角色id
     * @param list   列表
     */
    public void addAllPermission(int roleId, Collection<Integer> list) {
        if (list.isEmpty()) {
            return;
        }
        List<SysRolePermission> sysRolePermissionList = list.stream().map(v -> {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setSysRoleId(roleId);
            sysRolePermission.setSysPermissionId(v);
            return sysRolePermission;
        }).toList();
        saveBatch(sysRolePermissionList);
    }

    public void clearByRoleId(int roleId) {
        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sys_role_id", roleId);
        remove(queryWrapper);
    }

}