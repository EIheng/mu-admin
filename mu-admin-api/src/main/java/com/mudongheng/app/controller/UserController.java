package com.mudongheng.app.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mudongheng.app.exception.ParamException;
import com.mudongheng.app.model.entity.BaseEntity;
import com.mudongheng.app.model.entity.SysRole;
import com.mudongheng.app.model.entity.SysUser;
import com.mudongheng.app.model.params.IdParam;
import com.mudongheng.app.model.params.PageSysUserParam;
import com.mudongheng.app.model.params.UserInsertParam;
import com.mudongheng.app.model.params.UserUpdateParam;
import com.mudongheng.app.model.vo.DataResult;
import com.mudongheng.app.model.vo.SysUserVO;
import com.mudongheng.app.service.SysRoleService;
import com.mudongheng.app.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统用户的相关信息
 *
 * @author 幕冬
 * @since 2022年05月23日
 */
@Slf4j
@SaCheckLogin
@RestController
@RequestMapping("/user")
public class UserController {

    private final SysUserService sysUserService;

    private final SysRoleService sysRoleService;

    private final PasswordEncoder passwordEncoder;

    public UserController(SysUserService sysUserService, SysRoleService sysRoleService, PasswordEncoder passwordEncoder) {
        this.sysUserService = sysUserService;
        this.sysRoleService = sysRoleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/get-my-user-info")
    public DataResult<SysUserVO> getMyUserInfo() {
        SysUser sysUser = sysUserService.getById(StpUtil.getLoginIdAsInt());
        SysRole sysRole = sysRoleService.getById(sysUser.getSysRoleId());
        SysUserVO sysUserVO = new SysUserVO(
                sysUser.getId(),
                sysUser.getUsername(),
                sysRole.getRoleNote()
        );
        return DataResult.ok(sysUserVO);
    }

    @PostMapping("/page")
    public DataResult<Page<SysUserVO>> page(@Validated @RequestBody PageSysUserParam param) {
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.eq(param.id() != null, "id", param.id());
        qw.like(StrUtil.isNotEmpty(param.username()), "username", param.username());
        if (StrUtil.isNotEmpty(param.roleName())) {
            List<SysRole> sysRoleList = sysRoleService.list(new QueryWrapper<SysRole>().like("role_note", param.roleName()).select("id"));
            if (sysRoleList.isEmpty()) {
                throw new ParamException("没有名为：%s 的角色".formatted(param.roleName()));
            }
            qw.in("sys_role_id", sysRoleList.stream().map(BaseEntity::getId).toList());
        }
        Page<SysUser> page = sysUserService.page(new Page<>(param.cur(), 10), qw);
        List<SysUserVO> resList = page.getRecords().stream().map(sysUserService::getVO).toList();
        Page<SysUserVO> sysUserVOPage = new Page<>(param.cur(), 10, page.getTotal());
        sysUserVOPage.setRecords(resList);
        log.info("用户 {} 分页查询用户信息：{} ，共查询数据：{} 条", StpUtil.getLoginId(), param, resList.size());
        return DataResult.ok(sysUserVOPage);
    }

    @PostMapping("/insert")
    @SaCheckPermission(value = "sys-operate", orRole = "root")
    public DataResult<Object> insert(@Validated @RequestBody UserInsertParam param) {
        SysRole sysRole = sysRoleService.getById(param.roleId());
        if (sysRole ==  null) {
            log.error("用户 {} 查询不存在角色：{}", StpUtil.getLoginId(), param);
            throw new ParamException("此角色名不存在，请检查输入内容！");
        }
        SysUser sysUser = new SysUser();
        sysUser.setSysRoleId(sysRole.getId());
        sysUser.setUsername(param.username());
        sysUser.setPassword(passwordEncoder.encode(param.password()));
        sysUserService.save(sysUser);
        log.info("用户 {} 创建用户：{}", StpUtil.getLoginId(), sysUser);
        return DataResult.ok();
    }

    @PostMapping("/update")
    @SaCheckPermission(value = "sys-operate", orRole = "root")
    public DataResult<Object> update(@Validated @RequestBody UserUpdateParam param) {
        if (param.id() == -1) {
            return DataResult.error();
        }

        sysUserService.isExist(param.id());

        SysRole sysRole = sysRoleService.getById(param.roleId());
        if (sysRole ==  null) {
            log.error("用户 {} 查询不存在角色：{}", StpUtil.getLoginId(), param);
            throw new ParamException("此角色名不存在，请检查输入内容！");
        }

        UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", param.id()).set("username", param.username()).set("sys_role_id", sysRole.getId());

        sysUserService.update(updateWrapper);
        log.info("用户 {} 更新其他用户：{}", StpUtil.getLoginId(), param);
        return DataResult.ok();
    }

    @PostMapping("/delete")
    @SaCheckPermission(value = "sys-operate", orRole = "root")
    public DataResult<Object> delete(@Validated @RequestBody IdParam param) {
        Object loginId = StpUtil.getLoginId();
        if (!sysUserService.removeById(param.id())) {
            log.error("用户 {} 视图删除不存在用户：{}", loginId, param);
            return DataResult.error("该用户不存在");
        } else {
            log.info("用户 {} 删除其他用户：{}", loginId, param);
            return DataResult.ok();
        }
    }
}
