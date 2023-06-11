package com.cfive.pinnacle.entity.permission;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 中间表-权限-角色
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("t_power_role")
public class PowerRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 权限
     */
    @TableField("power_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long powerId;

    /**
     * 角色
     */
    @TableField("role_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

    @TableField("deleted")
    @TableLogic
    private Long deleted;

    @TableField("version")
    @Version
    private Integer version;
}
