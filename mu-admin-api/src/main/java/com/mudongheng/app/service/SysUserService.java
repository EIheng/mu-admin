package com.mudongheng.app.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mudongheng.app.exception.ParamException;
import com.mudongheng.app.mapper.SysRoleMapper;
import com.mudongheng.app.mapper.SysUserMapper;
import com.mudongheng.app.model.entity.SysRole;
import com.mudongheng.app.model.entity.SysUser;
import com.mudongheng.app.model.vo.SysUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 幕冬
 * @since 2022年05月22日
 */
@Slf4j
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    private final SysRoleMapper sysRoleMapper;

    public SysUserService(SysRoleMapper sysRoleMapper) {
        this.sysRoleMapper = sysRoleMapper;
    }

    public void isExist(int id) {
        long count = count(new QueryWrapper<SysUser>().eq("id", id).select("id"));
        if (count < 1) {
            log.error("用户 {} 试图查询异常表单：{}", StpUtil.getLoginId(), id);
            throw new ParamException("用户不存在，请检查输入内容！");
        }
    }

    public SysUserVO getVO(SysUser sysUser) {
        SysRole sysRole = sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq("id", sysUser.getSysRoleId()).select("role_note"));
        return new SysUserVO(
                sysUser.getId(),
                sysUser.getUsername(),
                sysRole.getRoleNote()
        );
    }

    public SysRole getRoleByUserId(int id) {
        SysUser sysUser = getById(id);
        if (sysUser == null) {
            throw new ParamException("用户不存在！");
        }
        SysRole sysRole = sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq("id", sysUser.getSysRoleId()));
        if (sysRole == null) {
            throw new ParamException("用户所对应角色不存在！");
        }
        return sysRole;
    }

}