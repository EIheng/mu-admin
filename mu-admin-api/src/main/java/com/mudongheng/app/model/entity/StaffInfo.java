package com.mudongheng.app.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 幕冬
 * @since 2022年05月23日
 */
@Data
@TableName(value ="staff_info")
@EqualsAndHashCode(callSuper = true)
public class StaffInfo extends BaseEntity {

    private String staffName;

    private String duty;

}
