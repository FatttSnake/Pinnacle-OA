package com.cfive.pinnacle.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.cfive.pinnacle.entity.permission.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime createTime;

    /**
     * 发送时间
     */
    @TableField("send_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime sendTime;

    /**
     * 失效时间
     */
    @TableField("end_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
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

    @TableField(exist = false)
    private Integer isRead;

    @TableField("deleted")
    @TableLogic
    private Long deleted;

    @TableField("version")
    @Version
    private Integer version;

    /**
     * 公告接收者
     */
    @TableField(exist = false)
    @JsonSerialize(using = ToStringSerializer.class)
    private List<Long> receivers;

}
