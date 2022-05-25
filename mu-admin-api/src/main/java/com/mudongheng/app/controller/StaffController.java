package com.mudongheng.app.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mudongheng.app.exception.ParamException;
import com.mudongheng.app.model.entity.StaffInfo;
import com.mudongheng.app.model.params.IdParam;
import com.mudongheng.app.model.params.StaffPageParam;
import com.mudongheng.app.model.vo.DataResult;
import com.mudongheng.app.service.StaffInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 幕冬
 * @since 2022年05月25日
 */
@Slf4j
@SaCheckLogin
@RestController
@RequestMapping("/staff")
public class StaffController {

    private final StaffInfoService staffInfoService;

    public StaffController(StaffInfoService staffInfoService) {
        this.staffInfoService = staffInfoService;
    }

    @PostMapping("/page")
    public DataResult<Page<StaffInfo>> page(@Validated @RequestBody StaffPageParam param) {
        QueryWrapper<StaffInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(param.id() != null, "id", param.id())
                .like(StrUtil.isNotEmpty(param.staffName()), "staff_name", param.staffName())
                .like(StrUtil.isNotEmpty(param.duty()), "duty", param.duty());
        Page<StaffInfo> staffInfoPage = staffInfoService.page(new Page<>(param.cur(), 10), queryWrapper);
        log.info("用户 {} 分页查询员工信息：{} ，共查询数据：{} 条", StpUtil.getLoginId(), param, staffInfoPage.getSize());
        return DataResult.ok(staffInfoPage);
    }

    @PostMapping("/insert")
    @SaCheckPermission(value = "staff-insert", orRole = "root")
    public DataResult<Object> insert(@Validated @RequestBody StaffInfo staffInfo) {
        staffInfoService.save(staffInfo);
        log.info("用户 {} 创建员工信息：{}", StpUtil.getLoginId(), staffInfo);
        return DataResult.ok();
    }

    @PostMapping("/update")
    @SaCheckPermission(value = "staff-update", orRole = "root")
    public DataResult<Object> update(@Validated @RequestBody StaffInfo staffInfo) {
        if (staffInfo.getId() == null || staffInfo.getId().equals(-1)) {
            throw new ParamException("请传入正确的id");
        }

        Object loginId = StpUtil.getLoginId();

        if (!staffInfoService.updateById(staffInfo)) {
            log.error("用户 {} 试图更新不存在员工信息：{}", loginId, staffInfo);
            throw new ParamException("该员工信息不存在！");
        }
        log.info("用户 {} 更新员工信息：{}", loginId, staffInfo);
        return DataResult.ok();
    }

    @PostMapping("/delete")
    @SaCheckPermission(value = "staff-delete", orRole = "root")
    public DataResult<Object> delete(@Validated @RequestBody IdParam param) {
        Object loginId = StpUtil.getLoginId();
        if (!staffInfoService.removeById(param.id())) {
            log.error("用户 {} 视图删除不存在员工信息：{}", loginId, param);
            throw new ParamException("该员工信息不存在！");
        }
        log.info("用户 {} 删除员工信息：{}", loginId, param);
        return DataResult.ok();
    }

}
