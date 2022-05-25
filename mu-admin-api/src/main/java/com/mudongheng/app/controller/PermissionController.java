package com.mudongheng.app.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mudongheng.app.exception.ParamException;
import com.mudongheng.app.model.entity.SysPermission;
import com.mudongheng.app.model.params.IdParam;
import com.mudongheng.app.model.params.PermissionInsertParam;
import com.mudongheng.app.model.params.PermissionPageParam;
import com.mudongheng.app.model.params.PermissionUpdateParam;
import com.mudongheng.app.model.vo.DataResult;
import com.mudongheng.app.model.vo.PermissionVO;
import com.mudongheng.app.service.SysPermissionService;
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
@SaCheckLogin
@RestController
@RequestMapping("/permission")
public class PermissionController {

    private final SysPermissionService sysPermissionService;

    public PermissionController(SysPermissionService sysPermissionService) {
        this.sysPermissionService = sysPermissionService;
    }

    @PostMapping("/list")
    public DataResult<List<PermissionVO>> list() {
        List<PermissionVO> voList = sysPermissionService.list().stream().map(sysPermissionService::getVO).toList();
        log.info("用户 {} 列出所有权限", StpUtil.getLoginId());
        return DataResult.ok(voList);
    }

    @PostMapping("/page")
    public DataResult<Page<PermissionVO>> page(@Validated @RequestBody PermissionPageParam param) {
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(param.id() != null, "id", param.id())
                .like(StrUtil.isNotEmpty(param.permissionName()), "permission_name", param.permissionName())
                .like(StrUtil.isNotEmpty(param.permissionNote()), "permission_note", param.permissionNote());
        Page<SysPermission> sysPermissionPage = sysPermissionService.page(new Page<>(param.cur(), 10), queryWrapper);
        Page<PermissionVO> resPage = new Page<>(sysPermissionPage.getCurrent(), sysPermissionPage.getSize(), sysPermissionPage.getTotal());
        List<PermissionVO> resList = sysPermissionPage.getRecords().stream().map(sysPermissionService::getVO).toList();
        resPage.setRecords(resList);
        log.info("用户 {} 分页查询权限：{} ，共查询数据：{} 条", StpUtil.getLoginId(), param, resList.size());
        return DataResult.ok(resPage);
    }

    @PostMapping("/insert")
    @SaCheckPermission(value = "sys-operate", orRole = "root")
    public DataResult<Object> insert(@Validated @RequestBody PermissionInsertParam param) {
        SysPermission sysPermission = new SysPermission();
        sysPermission.setPermissionName(param.permissionName());
        sysPermission.setPermissionNote(param.permissionNote());
        sysPermissionService.save(sysPermission);
        log.info("用户 {} 创建权限：{}", StpUtil.getLoginId(), sysPermission);
        return DataResult.ok();
    }

    @PostMapping("/update")
    @SaCheckPermission(value = "sys-operate", orRole = "root")
    public DataResult<Object> update(@Validated @RequestBody PermissionUpdateParam param) {
        if (param.id() == -1) {
            return DataResult.error();
        }
        SysPermission sysPermission = new SysPermission();
        sysPermission.setId(param.id());
        sysPermission.setPermissionName(param.permissionName());
        sysPermission.setPermissionNote(param.permissionNote());

        Object loginId = StpUtil.getLoginId();

        if (!sysPermissionService.updateById(sysPermission)) {
            log.error("用户 {} 试图更新不存在权限：{}", loginId, param);
            throw new ParamException("该权限不存在！");
        }
        log.info("用户 {} 更新权限：{}", loginId, param);
        return DataResult.ok();
    }

    @PostMapping("/delete")
    @SaCheckPermission(value = "sys-operate", orRole = "root")
    public DataResult<Object> delete(@Validated @RequestBody IdParam param) {
        Object loginId = StpUtil.getLoginId();
        if (!sysPermissionService.removeById(param.id())) {
            log.error("用户 {} 视图删除不存在权限：{}", loginId, param);
            throw new ParamException("该权限不存在！");
        }
        log.info("用户 {} 删除权限：{}", loginId, param);
        return DataResult.ok();
    }
}
