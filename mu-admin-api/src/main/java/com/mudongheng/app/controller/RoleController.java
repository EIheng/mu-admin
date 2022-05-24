package com.mudongheng.app.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mudongheng.app.model.entity.SysRole;
import com.mudongheng.app.model.params.IdParam;
import com.mudongheng.app.model.params.PageParam;
import com.mudongheng.app.model.vo.DataResult;
import com.mudongheng.app.model.vo.PermissionVO;
import com.mudongheng.app.model.vo.RoleVO;
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
@RequestMapping("/role")
public class RoleController {

    private final SysRoleService sysRoleService;

    private final SysPermissionService sysPermissionService;

    public RoleController(SysRoleService sysRoleService, SysPermissionService sysPermissionService) {
        this.sysRoleService = sysRoleService;
        this.sysPermissionService = sysPermissionService;
    }

    @PostMapping("/page")
    public DataResult<Page<RoleVO>> page(@Validated @RequestBody PageParam param) {
        // 1. 获取分页查询的角色信息
        Page<SysRole> page = sysRoleService.page(new Page<>(param.cur(), 10), new QueryWrapper<SysRole>().select("id", "role_note"));
        List<RoleVO> voList = page.getRecords().stream().map(sysRoleService::getVO).toList();
        Page<RoleVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(voList);
        log.info("用户 {} 分页查询角色：{} ，共查询数据：{} 条", StpUtil.getLoginId(), param, voList.size());
        return DataResult.ok(voPage);
    }

    @PostMapping("/list-permission-by-id")
    public DataResult<List<PermissionVO>> listByRole(@Validated @RequestBody IdParam param) {
        log.info("用户 {} 查询 {} 的权限", StpUtil.getLoginId(), param.id());
        return DataResult.ok(sysPermissionService.listByRole(param.id()));
    }
}
