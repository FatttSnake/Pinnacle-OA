package com.cfive.pinnacle.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 员工
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Data
@Accessors(chain = true)
@TableName("t_staff")
public class Staff implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户
     */
    @TableField("user_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 名
     */
    @TableField("first_name")
    private String firstName;

    /**
     * 姓
     */
    @TableField("last_name")
    private String lastName;

    /**
     * 性别
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 生日
     */
    @TableField(value = "birth", updateStrategy = FieldStrategy.IGNORED)
    private LocalDate birth;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 电话
     */
    @TableField("tel")
    private String tel;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    @TableField("deleted")
    @TableLogic
    private Long deleted;

    @TableField("version")
    @Version
    private Integer version;
}
