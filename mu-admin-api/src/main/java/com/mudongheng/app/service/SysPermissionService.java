package com.mudongheng.app.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mudongheng.app.mapper.SysPermissionMapper;
import com.mudongheng.app.mapper.SysRolePermissionMapper;
import com.mudongheng.app.model.entity.BaseEntity;
import com.mudongheng.app.model.entity.SysPermission;
import com.mudongheng.app.model.entity.SysRolePermission;
import com.mudongheng.app.model.vo.PermissionVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 幕冬
 * @since 2022年05月22日
 */
@Service
public class SysPermissionService extends ServiceImpl<SysPermissionMapper, SysPermission> {

    private final SysRolePermissionMapper sysRolePermissionMapper;

    public SysPermissionService(SysRolePermissionMapper sysRolePermissionMapper) {
        this.sysRolePermissionMapper = sysRolePermissionMapper;
    }

    public PermissionVO getVO(SysPermission sysPermission) {
        return new PermissionVO(
                sysPermission.getId(),
                sysPermission.getPermissionName(),
                sysPermission.getPermissionNote()
        );
    }

    public List<PermissionVO> listByRole(int sysRoleId) {
        // 1. 查角色对应权限
        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sys_role_id", sysRoleId).select("sys_permission_id");
        List<SysRolePermission> sysRolePermissionList = sysRolePermissionMapper.selectList(queryWrapper);
        // 2. 将权限id映射为权限名
        List<Integer> sysRolePermissionIdList = sysRolePermissionList.stream().map(BaseEntity::getId).toList();
        QueryWrapper<SysPermission> sysPermissionQueryWrapper = new QueryWrapper<>();
        sysPermissionQueryWrapper.in("id", sysRolePermissionIdList);
        List<SysPermission> sysPermissionList = list(sysPermissionQueryWrapper);
        return sysPermissionList.stream().map(this::getVO).toList();
    }

}