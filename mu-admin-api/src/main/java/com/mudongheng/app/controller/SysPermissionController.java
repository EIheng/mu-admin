package com.mudongheng.app.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mudongheng.app.model.entity.SysPermission;
import com.mudongheng.app.model.entity.SysRole;
import com.mudongheng.app.model.params.PageParam;
import com.mudongheng.app.model.params.PermissionListParam;
import com.mudongheng.app.model.vo.DataResult;
import com.mudongheng.app.model.vo.PermissionVO;
import com.mudongheng.app.service.SysPermissionService;
import com.mudongheng.app.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 幕冬
 * @since 2022年05月23日
 */
@Slf4j
@RestController
@RequestMapping("/permission")
public class SysPermissionController {

    private final SysPermissionService sysPermissionService;

    private final SysRoleService sysRoleService;

    public SysPermissionController(SysPermissionService sysPermissionService,
                                   SysRoleService sysRoleService) {
        this.sysPermissionService = sysPermissionService;
        this.sysRoleService = sysRoleService;
    }

    @PostMapping("/page")
    public DataResult<Page<PermissionVO>> page(@Validated @RequestBody PageParam param) {
        Page<SysPermission> sysPermissionPage = sysPermissionService.page(new Page<>(param.cur(), 10));
        Page<PermissionVO> resPage = new Page<>(sysPermissionPage.getCurrent(), sysPermissionPage.getSize(), sysPermissionPage.getTotal());
        List<PermissionVO> resList = sysPermissionPage.getRecords().stream().map(sysPermissionService::getVO).toList();
        resPage.setRecords(resList);
        log.info("用户 {} 分页查询权限：{} ，共查询数据：{} 条", StpUtil.getLoginId(), param, resList.size());
        return DataResult.ok(resPage);
    }

    @PostMapping("/list-by-role")
    public DataResult<List<PermissionVO>> listByRole(@Validated @RequestBody PermissionListParam param) {
        // 1. 查角色
        SysRole sysRole = sysRoleService.getByRoleNote(param.roleName());
        // 2. 列
        return DataResult.ok(sysPermissionService.listByRole(sysRole.getId()));
    }
}
