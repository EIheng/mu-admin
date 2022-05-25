package com.mudongheng.app.component;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.SaSessionCustomUtil;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.mudongheng.app.model.entity.SysRole;
import com.mudongheng.app.model.vo.PermissionVO;
import com.mudongheng.app.service.SysPermissionService;
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

    private final SysPermissionService sysPermissionService;

    public PermissionComponent(SysUserService sysUserService,
                               SysPermissionService sysPermissionService) {
        log.info("启用sa-token权限认证");
        this.sysUserService = sysUserService;
        this.sysPermissionService = sysPermissionService;
    }

    /**
     * 在用户角色发生变化时调用
     *
     * @param loginId 用户id
     */
    public void clearUserRoleSession(int loginId) {
        SaSession sessionByLoginId = StpUtil.getSessionByLoginId(loginId);
        sessionByLoginId.delete(SaSession.ROLE_LIST);
    }

    /**
     * 在角色所包含权限发生变化时调用
     *
     * @param roleName 角色名称
     */
    public void clearRolePermissionSession(String roleName) {

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
            List<String> list = roleSession.get(SaSession.PERMISSION_LIST, () -> sysPermissionService.listByRoleName(roleName).stream().map(PermissionVO::permissionName).toList());
            permissionList.addAll(list);
        }

        // 3. 返回权限码集合
        return permissionList;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        SaSession session = StpUtil.getSessionByLoginId(loginId);
        return session.get(SaSession.ROLE_LIST, () -> {
            SysRole sysRole = sysUserService.getRoleByUserId(Integer.parseInt(loginId.toString()));
            return List.of(sysRole.getRoleName());
        });
    }

}
