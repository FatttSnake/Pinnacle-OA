package com.cfive.pinnacle.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 中间表-权限-页面元素
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Data
@Accessors(chain = true)
@TableName("t_power_element")
public class PowerElement implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    /**
     * 权限
     */
    @TableField("power_id")
    private Long powerId;

    /**
     * 页面元素
     */
    @TableField("element_id")
    private Long elementId;

    @TableField("deleted")
    @TableLogic
    private Integer deleted;

    @TableField("version")
    @Version
    private Integer version;
}
