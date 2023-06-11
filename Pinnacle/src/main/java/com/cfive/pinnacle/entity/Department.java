package com.cfive.pinnacle.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.cfive.pinnacle.entity.permission.User;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 部门
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Data
@Accessors(chain = true)
@TableName("t_department")
public class Department implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 部门名
     */
    @TableField("name")
    private String name;

    /**
     * 部门电话
     */
    @TableField("tel")
    private String tel;

    /**
     * 部门地址
     */
    @TableField("address")
    private String address;

    @TableField("deleted")
    @TableLogic
    private Long deleted;

    @TableField("version")
    @Version
    private Integer version;

    /**
     * 部门成员
     */
    @TableField(exist = false)
    private List<User> userList;
}
