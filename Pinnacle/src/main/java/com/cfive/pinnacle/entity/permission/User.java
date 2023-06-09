package com.cfive.pinnacle.entity.permission;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.cfive.pinnacle.entity.Department;
import com.cfive.pinnacle.entity.Staff;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Data
@Accessors(chain = true)
@TableName("t_user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("passwd")
    private String passwd;

    /**
     * 部门
     */
    @TableField("department_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long departmentId;

    @TableField(exist = false)
    private Department department;

    /**
     * 启用
     */
    @TableField("enable")
    private Integer enable;

    @TableField(exist = false)
    private Staff staff;

    @TableField(exist = false)
    private List<Role> roles;

    @TableField(exist = false)
    private List<Group> groups;

    @TableField(exist = false)
    private List<Menu> menus;

    @TableField(exist = false)
    private List<Element> elements;

    @TableField(exist = false)
    private List<Operation> operations;

    @TableField("deleted")
    private Long deleted;

    @TableField("version")
    @Version
    private Integer version;
}
