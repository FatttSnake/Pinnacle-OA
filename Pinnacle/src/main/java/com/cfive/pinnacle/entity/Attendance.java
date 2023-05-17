package com.cfive.pinnacle.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 考勤
 * </p>
 *
 * @author gzw
 * @since 2023-04-30
 */
@Data
@Accessors(chain = true)
@TableName("t_attendance")
public class Attendance implements Serializable {

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
     * 考勤时间
     */
    @TableField("att_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",timezone = "UTC")
    private LocalDateTime attTime;

    /**
     * 考勤状态
     */
    @TableField("status")
    private Integer status;

    /**
     * 修改人
     */
    @TableField("modify_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long modifyId;

    /**
     * 修改时间
     */
    @TableField("modify_time")
    private LocalDateTime modifyTime;

    @TableField("deleted")
    @TableLogic(value = "0",delval = "1")
    private Long deleted;

    @TableField("version")
    @Version
    private Integer version;

    @TableField(exist = false)
    private User user;
}
