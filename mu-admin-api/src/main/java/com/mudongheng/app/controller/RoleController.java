package com.mudongheng.app.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mudongheng.app.component.PermissionComponent;
import com.mudongheng.app.exception.ParamException;
import com.mudongheng.app.model.entity.SysRole;
import com.mudongheng.app.model.entity.SysRolePermission;
import com.mudongheng.app.model.params.*;
import com.mudongheng.app.model.vo.DataResult;
import com.mudongheng.app.model.vo.PermissionVO;
import com.mudongheng.app.model.vo.RoleVO;
import com.mudongheng.app.service.SysPermissionService;
import com.mudongheng.app.service.SysRolePermissionService;
import com.mudongheng.app.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 幕冬
 * @since 2022年05月23日
 */
@Slf4j
@SaCheckLogin
@RestController
@RequestMapping("/role")
public class RoleController {

    private final SysRoleService sysRoleService;

    private final SysPermissionService sysPermissionService;

    private final SysRolePermissionService sysRolePermissionService;

    private final PermissionComponent permissionComponent;

    public RoleController(SysRoleService sysRoleService,
                          SysPermissionService sysPermissionService,
                          SysRolePermissionService sysRolePermissionService,
                          PermissionComponent permissionComponent) {
        this.sysRoleService = sysRoleService;
        this.sysPermissionService = sysPermissionService;
        this.sysRolePermissionService = sysRolePermissionService;
        this.permissionComponent = permissionComponent;
    }

    @PostMapping("/list")
    public DataResult<List<RoleVO>> list() {
        List<RoleVO> voList = sysRoleService.list().stream().map(sysRoleService::getVO).toList();
        return DataResult.ok(voList);
    }

    @PostMapping("/page")
    public DataResult<Page<RoleVO>> page(@Validated @RequestBody RolePageParam param) {
        // 1. 获取分页查询的角色信息
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(param.id() != null, "id", param.id())
                .like(StrUtil.isNotEmpty(param.roleName()), "role_name", param.roleName())
                .like(StrUtil.isNotEmpty(param.roleNote()), "role_note", param.roleNote());
        Page<SysRole> page = sysRoleService.page(new Page<>(param.cur(), 10), queryWrapper);
        List<RoleVO> voList = page.getRecords().stream().map(sysRoleService::getVO).toList();
        Page<RoleVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(voList);
        log.info("用户 {} 分页查询角色：{} ，共查询数据：{} 条", StpUtil.getLoginId(), param, voList.size());
        return DataResult.ok(voPage);
    }

    @PostMapping("/list-role-permission-by-id")
    @SaCheckPermission(value = "sys-operate", orRole = "root")
    public DataResult<List<PermissionVO>> listRolePermissionById(@Validated @RequestBody IdParam param) {
        List<PermissionVO> voList = sysPermissionService.listByRoleId(param.id());
        log.info("用户 {} 查询 {} 的权限，共有 {} 条权限", StpUtil.getLoginId(), param.id(), voList.size());
        return DataResult.ok(voList);
    }

    @PostMapping("/insert")
    @SaCheckPermission(value = "sys-operate", orRole = "root")
    public DataResult<Object> insert(@Validated @RequestBody RoleInsertParam param) {
        SysRole sysRole = new SysRole();
        sysRole.setRoleName(param.roleName());
        sysRole.setRoleNote(param.roleNote());
        sysRoleService.save(sysRole);
        log.info("用户 {} 创建角色：{}", StpUtil.getLoginId(), sysRole);
        return DataResult.ok();
    }

    @PostMapping("/update")
    @SaCheckPermission(value = "sys-operate", orRole = "root")
    public DataResult<Object> update(@Validated @RequestBody RoleUpdateParam param) {
        if (param.id() == -1) {
            return DataResult.error();
        }

        UpdateWrapper<SysRole> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", param.id()).set("role_name", param.roleName()).set("role_note", param.roleNote());

        Object loginId = StpUtil.getLoginId();

        if (!sysRoleService.update(updateWrapper)) {
            log.error("用户 {} 试图更新不存在角色：{}", loginId, param);
            throw new ParamException("该角色不存在！");
        }
        log.info("用户 {} 更新角色：{}", loginId, param);
        return DataResult.ok();
    }

    @PostMapping("/update-permission")
    @SaCheckPermission(value = "sys-operate", orRole = "root")
    public DataResult<Object> updatePermission(@Validated @RequestBody RoleUpdatePermissionParam param) {
        // 1. 参数校验
        SysRole sysRole = sysRoleService.getById(param.roleId());
        if (sysRole == null) {
            throw new ParamException("该角色不存在！");
        }
        // 传入空列表，删除所有权限
        if (param.permissionIdList().isEmpty()) {
            sysRolePermissionService.clearByRoleId(param.roleId());
            return DataResult.ok();
        }
        sysPermissionService.isIdListExist(param.permissionIdList());
        // 2. 根据实际情况添加或删除权限
        // 该角色已有的权限
        List<SysRolePermission> sysRolePermissionList = sysRolePermissionService.listByRoleId(param.roleId());
        // 如果原有权限为空，则直接添加权限
        if (sysRolePermissionList.isEmpty()) {
            sysRolePermissionService.addAllPermission(param.roleId(), param.permissionIdList());
        } else {
            // 进行存在判断的集合，源数据为传入的权限id列表
            Set<Integer> permissionIdSet = new HashSet<>(param.permissionIdList());
            // 需要删除的id列表
            List<Integer> permissionIdListNeedRemove = new ArrayList<>();
            // 遍历已有权限列表
            for (SysRolePermission sysRolePermission : sysRolePermissionList) {
                // 如果有交集，则碰撞删除
                if (!permissionIdSet.remove(sysRolePermission.getSysPermissionId())) {
                    // 如果没有删除，说明没有交集，该权限需要删除
                    permissionIdListNeedRemove.add(sysRolePermission.getSysPermissionId());
                }
            }
            // 最终set的剩余即为需要新增的权限
            sysRolePermissionService.addAllPermission(param.roleId(), permissionIdSet);
            // 删除列表不为空的话则进行删除
            if (!permissionIdListNeedRemove.isEmpty()) {
                QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("sys_role_id", param.roleId()).in("sys_permission_id", permissionIdListNeedRemove);
                sysRolePermissionService.remove(queryWrapper);
            }
        }
        permissionComponent.clearRolePermissionSession(sysRole.getRoleName());
        log.info("用户 {} 修改角色 {} 的权限为：{}", StpUtil.getLoginId(), param.roleId(), param.permissionIdList());
        return DataResult.ok();
    }

    @PostMapping("/delete")
    @SaCheckPermission(value = "sys-operate", orRole = "root")
    public DataResult<Object> delete(@Validated @RequestBody IdParam param) {
        Object loginId = StpUtil.getLoginId();
        if (!sysRoleService.removeById(param.id())) {
            log.error("用户 {} 视图删除不存在角色：{}", loginId, param);
            throw new ParamException("该角色不存在！");
        }
        log.info("用户 {} 删除其他用户：{}", loginId, param);
        return DataResult.ok();
    }
}
