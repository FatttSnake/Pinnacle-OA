package com.cfive.pinnacle.entity.permission;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限类型
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("t_power_type")
public class PowerType implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 权限类型名
     */
    @TableField("name")
    private String name;
}
