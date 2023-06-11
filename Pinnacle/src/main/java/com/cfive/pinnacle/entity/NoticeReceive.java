package com.cfive.pinnacle.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 公告接收
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Data
@Accessors(chain = true)
@TableName("t_notice_receive")
public class NoticeReceive implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户Id
     */
    @TableField("user_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;


    /**
     * 公告Id
     */
    @TableField("notice_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long noticeId;

    /**
     * 已读
     */
    @TableField("already_read")
    private Integer alreadyRead;

    @TableField("deleted")
    @TableLogic
    private Long deleted;

    @TableField("version")
    @Version
    private Integer version;
}
