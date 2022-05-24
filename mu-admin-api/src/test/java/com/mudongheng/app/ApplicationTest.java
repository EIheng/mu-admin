package com.mudongheng.app;

import com.mudongheng.app.model.entity.SysPermission;
import com.mudongheng.app.model.entity.SysRolePermission;
import com.mudongheng.app.model.entity.SysUser;
import com.mudongheng.app.service.SysPermissionService;
import com.mudongheng.app.service.SysRolePermissionService;
import com.mudongheng.app.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        for (int i = 5; i <= 8; i++) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setSysRoleId(5);
            sysRolePermission.setSysPermissionId(i);
            sysRolePermissionService.save(sysRolePermission);
        }
    }

}
