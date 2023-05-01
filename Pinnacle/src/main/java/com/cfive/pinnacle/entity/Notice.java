package com.cfive.pinnacle.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 公告
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Data
@Accessors(chain = true)
@TableName("t_notice")
public class Notice implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 公告标题
     */
    @TableField("title")
    private String title;

    /**
     * 公告内容
     */
    @TableField("content")
    private String content;

    /**
     * 公告类型Id
     */
    @TableField("type_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long typeId;

    /**
     * 公告类型
     */
    @TableField(exist = false)
    private NoticeType noticeType;

    /**
     * 发布者id
     */
    @TableField("sender_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long senderId;

    /**
     * 发布者
     */
    @TableField(exist = false)
    private User sender;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 发送时间
     */
    @TableField("send_time")
    private LocalDateTime sendTime;

    /**
     * 失效时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;

    /**
     * 优先级
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 置顶
     */
    @TableField("top")
    private Integer top;

    /**
     * 修改时间
     */
    @TableField("modify_time")
    private LocalDateTime modifyTime;

    /**
     * 源ID
     */
    @TableField("origin_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long originId;

    /**
     * 已修改
     */
    @TableField("old")
    private Integer old;

    @TableField("deleted")
    @TableLogic
    private Integer deleted;

    @TableField("version")
    @Version
    private Integer version;


}
