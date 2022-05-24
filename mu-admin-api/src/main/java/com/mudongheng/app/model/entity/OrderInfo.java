package com.mudongheng.app.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 幕冬
 * @since 2022年05月23日
 */
@Data
@TableName(value ="order_info")
@EqualsAndHashCode(callSuper = true)
public class OrderInfo extends BaseEntity {

    private String orderName;

    private Integer productNum;

}
