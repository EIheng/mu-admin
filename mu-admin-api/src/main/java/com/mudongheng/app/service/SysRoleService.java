package com.mudongheng.app.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mudongheng.app.exception.ParamException;
import com.mudongheng.app.mapper.SysRoleMapper;
import com.mudongheng.app.model.entity.SysRole;
import com.mudongheng.app.model.vo.RoleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 幕冬
 * @since 2022年05月22日
 */
@Slf4j
@Service
public class SysRoleService extends ServiceImpl<SysRoleMapper, SysRole> {

    public SysRole getByRoleNote(String roleNote) {
        SysRole sysRole = getOne(
                new QueryWrapper<SysRole>().eq("role_note", roleNote).select("id"));
        if (sysRole == null) {
            log.error("用户 {} 试图查询异常表单：{}", StpUtil.getLoginId(), roleNote);
            throw new ParamException("此角色名不存在，请检查输入内容！");
        }
        return sysRole;
    }

    public RoleVO getVO(SysRole sysRole) {
        return new RoleVO(
                sysRole.getId(),
                sysRole.getRoleName(),
                sysRole.getRoleNote()
        );
    }

}