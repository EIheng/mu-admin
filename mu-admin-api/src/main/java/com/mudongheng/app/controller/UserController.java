package com.mudongheng.app.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mudongheng.app.exception.ParamException;
import com.mudongheng.app.model.entity.BaseEntity;
import com.mudongheng.app.model.entity.SysRole;
import com.mudongheng.app.model.entity.SysUser;
import com.mudongheng.app.model.params.PageSysUserParam;
import com.mudongheng.app.model.params.UserIdParam;
import com.mudongheng.app.model.params.UserInsertParam;
import com.mudongheng.app.model.params.UserUpdateParam;
import com.mudongheng.app.model.vo.DataResult;
import com.mudongheng.app.model.vo.SysUserVO;
import com.mudongheng.app.service.SysRoleService;
import com.mudongheng.app.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
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
@RestController
@RequestMapping("/user")
public class UserController {

    private final SysUserService sysUserService;

    private final SysRoleService sysRoleService;

    public UserController(SysUserService sysUserService, SysRoleService sysRoleService) {
        this.sysUserService = sysUserService;
        this.sysRoleService = sysRoleService;
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
        Page<SysUserVO> sysUserVOPage = new Page<>(param.cur(), 10);
        sysUserVOPage.setRecords(resList);
        log.info("用户 {} 分页查询用户信息：{} ，共查询数据：{} 条", StpUtil.getLoginId(), param, resList.size());
        return DataResult.ok(sysUserVOPage);
    }

    @PostMapping("/insert")
    public DataResult<Object> insert(@Validated @RequestBody UserInsertParam param) {
        SysRole sysRole = sysRoleService.getByRoleNote(param.roleName());
        SysUser sysUser = new SysUser();
        sysUser.setSysRoleId(sysRole.getId());
        sysUser.setUsername(param.username());
        sysUserService.save(sysUser);
        return DataResult.ok();
    }

    @PostMapping("/update")
    public DataResult<Object> update(@Validated @RequestBody UserUpdateParam param) {
        if (param.id() == -1) {
            return DataResult.error();
        }

        sysUserService.isExist(param.id());

        SysRole sysRole = sysRoleService.getByRoleNote(param.roleName());

        UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", param.id()).set("username", param.username()).set("sys_role_id", sysRole.getId());

        sysUserService.update(updateWrapper);
        log.info("用户 {} 更新其他用户：{}", StpUtil.getLoginId(), param);
        return DataResult.ok();
    }

    @PostMapping("/delete")
    public DataResult<Object> delete(@Validated @RequestBody UserIdParam param) {
        Object loginId = StpUtil.getLoginId();
        if (!sysUserService.removeById(param.userId())) {
            log.error("用户 {} 视图删除不存在用户：{}", loginId, param);
            return DataResult.error("该用户不存在");
        } else {
            log.info("用户 {} 删除其他用户：{}", loginId, param);
            return DataResult.ok();
        }
    }
}
