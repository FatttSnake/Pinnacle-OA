package com.cfive.pinnacle.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.cfive.pinnacle.entity.permission.Element;
import com.cfive.pinnacle.entity.permission.Menu;
import com.cfive.pinnacle.entity.permission.Operation;
import com.cfive.pinnacle.entity.permission.Power;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Data
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
}
