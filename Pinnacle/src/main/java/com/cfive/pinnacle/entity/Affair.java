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
 * 事务
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Data
@Accessors(chain = true)
@TableName("t_affair")
public class Affair implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    @JsonSerialize(using = ToStringSerializer.class)
    @TableId("id")
    private Long id;

    /**
     * 事务标题
     */
    @TableField("title")
    private String title;

    /**
     * 事务内容
     */
    @TableField("content")
    private String content;

    /**
     * 事务类型
     */
    @TableField("type_id")
    private Long typeId;

    /**
     * 事务状态
     */
    @TableField("status")
    private Integer status;

    /**
     * 申请者
     */
    @TableField("applicant_id")
    private Long applicantId;

    /**
     * 审核者
     */
    @TableField("inspector_id")
    private Long inspectorId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",timezone = "UTC")
    private LocalDateTime createTime;

    /**
     * 审核时间
     */
    @TableField("inspect_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",timezone = "UTC")
    private LocalDateTime inspectTime;

    /**
     * 优先级
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 修改时间
     */
    @TableField("modify_time")
    private LocalDateTime modifyTime;

    /**
     * 源ID
     */
    @TableField("origin_id")
    private Long originId;

    /**
     * 已修改
     */
    @TableField("old")
    private Integer old;

    @TableField("deleted")
    @TableLogic
    private Long deleted;

    @TableField("version")
    @Version
    private Integer version;
}
