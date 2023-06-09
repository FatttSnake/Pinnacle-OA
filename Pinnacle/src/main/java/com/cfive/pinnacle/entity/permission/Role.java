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
import java.util.List;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("t_role")
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 角色名
     */
    @TableField("name")
    private String name;

    /**
     * 启用
     */
    @TableField("enable")
    private Integer enable;

    @TableField(exist = false)
    private List<Menu> menus;

    @TableField(exist = false)
    private List<Element> elements;

    @TableField(exist = false)
    private List<Operation> operations;

    @TableField(exist = false)
    private List<Power> powers;

    @TableField("deleted")
    @TableLogic
    private Long deleted;

    @TableField("version")
    @Version
    private Integer version;

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
