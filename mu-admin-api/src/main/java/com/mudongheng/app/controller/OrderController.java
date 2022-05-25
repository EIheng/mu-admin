package com.mudongheng.app.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mudongheng.app.exception.ParamException;
import com.mudongheng.app.model.entity.OrderInfo;
import com.mudongheng.app.model.params.IdParam;
import com.mudongheng.app.model.params.OrderPageParam;
import com.mudongheng.app.model.vo.DataResult;
import com.mudongheng.app.service.OrderInfoService;
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
@RequestMapping("/order")
public class OrderController {

    private final OrderInfoService orderInfoService;

    public OrderController(OrderInfoService orderInfoService) {
        this.orderInfoService = orderInfoService;
    }

    @PostMapping("/page")
    public DataResult<Page<OrderInfo>> page(@Validated @RequestBody OrderPageParam param) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(param.id() != null, "id", param.id())
                .like(StrUtil.isNotEmpty(param.orderName()), "order_name", param.orderName());
        Page<OrderInfo> orderInfoPage = orderInfoService.page(new Page<>(param.cur(), 10), queryWrapper);
        log.info("用户 {} 分页查询订单：{} ，共查询数据：{} 条", StpUtil.getLoginId(), param, orderInfoPage.getSize());
        return DataResult.ok(orderInfoPage);
    }

    @PostMapping("/insert")
    @SaCheckPermission(value = "order-insert", orRole = "root")
    public DataResult<Object> insert(@Validated @RequestBody OrderInfo orderInfo) {
        orderInfoService.save(orderInfo);
        log.info("用户 {} 创建订单：{}", StpUtil.getLoginId(), orderInfo);
        return DataResult.ok();
    }

    @PostMapping("/update")
    @SaCheckPermission(value = "order-update", orRole = "root")
    public DataResult<Object> update(@Validated @RequestBody OrderInfo orderInfo) {
        if (orderInfo.getId() == null || orderInfo.getId().equals(-1)) {
            throw new ParamException("请传入正确的id");
        }

        Object loginId = StpUtil.getLoginId();

        if (!orderInfoService.updateById(orderInfo)) {
            log.error("用户 {} 试图更新不存在订单：{}", loginId, orderInfo);
            throw new ParamException("该订单不存在！");
        }
        log.info("用户 {} 更新订单：{}", loginId, orderInfo);
        return DataResult.ok();
    }

    @PostMapping("/delete")
    @SaCheckPermission(value = "order-delete", orRole = "root")
    public DataResult<Object> delete(@Validated @RequestBody IdParam param) {
        Object loginId = StpUtil.getLoginId();
        if (!orderInfoService.removeById(param.id())) {
            log.error("用户 {} 视图删除不存在订单：{}", loginId, param);
            throw new ParamException("该订单不存在！");
        }
        log.info("用户 {} 删除订单：{}", loginId, param);
        return DataResult.ok();
    }

}
