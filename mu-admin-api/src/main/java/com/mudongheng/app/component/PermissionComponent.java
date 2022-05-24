package com.mudongheng.app.component;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.SaSessionCustomUtil;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mudongheng.app.model.entity.SysPermission;
import com.mudongheng.app.model.entity.SysRole;
import com.mudongheng.app.model.entity.SysRolePermission;
import com.mudongheng.app.model.entity.SysUser;
import com.mudongheng.app.service.SysPermissionService;
import com.mudongheng.app.service.SysRolePermissionService;
import com.mudongheng.app.service.SysRoleService;
import com.mudongheng.app.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 幕冬
 * @since 2022年05月23日
 */
@Slf4j
@Component
public class PermissionComponent implements StpInterface {

    private final SysUserService sysUserService;

    private final SysRoleService sysRoleService;

    private final SysPermissionService sysPermissionService;

    private final SysRolePermissionService sysRolePermissionService;

    public PermissionComponent(SysUserService sysUserService,
                               SysRoleService sysRoleService,
                               SysPermissionService sysPermissionService,
                               SysRolePermissionService sysRolePermissionService) {
        log.info("启用sa-token权限认证");
        this.sysUserService = sysUserService;
        this.sysRoleService = sysRoleService;
        this.sysPermissionService = sysPermissionService;
        this.sysRolePermissionService = sysRolePermissionService;
    }

    /**
     * 在用户角色发生变化时调用
     * @param loginId 用户id
     */
    public void clearUserRoleSession(int loginId) {
        SaSession sessionByLoginId = StpUtil.getSessionByLoginId(loginId);
        sessionByLoginId.delete(SaSession.ROLE_LIST);
    }

    /**
     * 在角色所包含权限发生变化时调用
     * @param roleName 角色名称
     */
    public void clearRoleSession(String roleName) {
        SaSession roleSession = SaSessionCustomUtil.getSessionById("role-" + roleName);
        roleSession.clear();
    }

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 1. 声明权限码集合
        List<String> permissionList = new ArrayList<>();

        // 2. 遍历角色列表，查询拥有的权限码
        for (String roleName : getRoleList(loginId, loginType)) {
            SaSession roleSession = SaSessionCustomUtil.getSessionById("role-" + roleName);
            List<String> list = roleSession.get(SaSession.PERMISSION_LIST, () -> {
                SysRole sysRole = sysRoleService.getOne(new QueryWrapper<SysRole>().eq("role_name", roleName).select("id"));
                List<Integer> sysRoleIdList = sysRolePermissionService
                        .list(new QueryWrapper<SysRolePermission>().eq("sys_role_id", sysRole.getId()))
                        .stream().map(SysRolePermission::getSysPermissionId).toList();
                return sysPermissionService
                        .list(new QueryWrapper<SysPermission>().in("id", sysRoleIdList).select("permission_name"))
                        .stream().map(SysPermission::getPermissionName);
            });
            permissionList.addAll(list);
        }

        // 3. 返回权限码集合
        return permissionList;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        SaSession session = StpUtil.getSessionByLoginId(loginId);
        return session.get(SaSession.ROLE_LIST, () -> {
            Integer sysRoleId = sysUserService.getById(new QueryWrapper<SysUser>().eq("id", loginId).select("sys_role_id")).getSysRoleId();
            SysRole sysRole = sysRoleService.getOne(new QueryWrapper<SysRole>().eq("id", sysRoleId).select("role_name"));
            return List.of(sysRole.getRoleName());
        });
    }

}
