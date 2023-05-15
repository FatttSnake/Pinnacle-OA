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
 * 功能
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("t_operation")
public class Operation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 功能名
     */
    @TableField("name")
    private String name;

    /**
     * 功能编码
     */
    @TableField("code")
    private String code;

    /**
     *  权限ID
     */
    @TableField("power_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long powerId;

    /**
     *  页面元素ID
     */
    @TableField("element_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long elementId;

    /**
     * 父ID
     */
    @TableField("parent_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;
}
