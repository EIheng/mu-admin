package com.mudongheng.app.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mudongheng.app.exception.ParamException;
import com.mudongheng.app.mapper.SysPermissionMapper;
import com.mudongheng.app.mapper.SysRoleMapper;
import com.mudongheng.app.mapper.SysRolePermissionMapper;
import com.mudongheng.app.model.entity.SysPermission;
import com.mudongheng.app.model.entity.SysRole;
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

    private final SysRoleMapper sysRoleMapper;

    private final SysRolePermissionMapper sysRolePermissionMapper;

    public SysPermissionService(SysRoleMapper sysRoleMapper, SysRolePermissionMapper sysRolePermissionMapper) {
        this.sysRoleMapper = sysRoleMapper;
        this.sysRolePermissionMapper = sysRolePermissionMapper;
    }

    public PermissionVO getVO(SysPermission sysPermission) {
        return new PermissionVO(
                sysPermission.getId(),
                sysPermission.getPermissionName(),
                sysPermission.getPermissionNote()
        );
    }

    public List<PermissionVO> listByRoleId(int sysRoleId) {
        // 1. 查角色对应权限
        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sys_role_id", sysRoleId).select("sys_permission_id");
        List<SysRolePermission> sysRolePermissionList = sysRolePermissionMapper.selectList(queryWrapper);
        // 2. 将权限id映射为权限名
        if (sysRolePermissionList.isEmpty()) {
            return List.of();
        }
        List<Integer> sysRolePermissionIdList = sysRolePermissionList.stream().map(SysRolePermission::getSysPermissionId).toList();
        QueryWrapper<SysPermission> sysPermissionQueryWrapper = new QueryWrapper<>();
        sysPermissionQueryWrapper.in("id", sysRolePermissionIdList);
        List<SysPermission> sysPermissionList = list(sysPermissionQueryWrapper);

        return sysPermissionList.stream().map(this::getVO).toList();
    }

    /**
     * 在有些鉴权场景下只知道角色名称而不知道其对应id
     * @param roleName 角色名称
     * @return 列表
     */
    public List<PermissionVO> listByRoleName(String roleName) {
        SysRole sysRole = sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq("role_name", roleName).select("id"));
        if (sysRole == null) {
            throw new ParamException("角色 %s 不存在！".formatted(roleName));
        }
        return listByRoleId(sysRole.getId());
    }

    /**
     * 判断一个id列表是否存在于数据库中
     * 不存在则会抛出异常
     * @param list id列表
     */
    public void isIdListExist(List<Integer> list) {
        QueryWrapper<SysPermission> qw = new QueryWrapper<>();
        qw.in("id", list).select("id");
        // 传入参数所对应的权限
        if (count(qw) != list.size()) {
            throw new ParamException("传入非法权限列表！");
        }
    }

}