package com.mudongheng.app;

import cn.hutool.core.bean.BeanUtil;
import com.mudongheng.app.model.entity.SysPermission;
import com.mudongheng.app.model.entity.SysRolePermission;
import com.mudongheng.app.model.entity.SysUser;
import com.mudongheng.app.model.params.PermissionUpdateParam;
import com.mudongheng.app.service.SysPermissionService;
import com.mudongheng.app.service.SysRolePermissionService;
import com.mudongheng.app.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class ApplicationTest {

    @Autowired
    SysPermissionService sysPermissionService;

    @Autowired
    SysRolePermissionService sysRolePermissionService;

    record Node(String a, String b) {}

    @Test
    void test1() {

    }

}
