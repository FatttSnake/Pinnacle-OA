package com.cfive.pinnacle.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 中间表-角色-用户组
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Data
@Accessors(chain = true)
@TableName("t_role_group")
public class RoleGroup implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 角色
     */
    @TableField("role_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

    /**
     * 群组
     */
    @TableField("group_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long groupId;

    @TableField("deleted")
    @TableLogic
    private Integer deleted;

    @TableField("version")
    @Version
    private Integer version;
}
