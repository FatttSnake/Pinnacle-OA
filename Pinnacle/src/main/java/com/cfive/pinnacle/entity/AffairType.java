package com.cfive.pinnacle.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 事务类型
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Data
@Accessors(chain = true)
@TableName("t_affair_type")
public class AffairType implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    /**
     * 事务类型名
     */
    @TableField("name")
    private String name;

    /**
     * 启用
     */
    @TableField("enable")
    private Integer enable;

    @TableField("deleted")
    @TableLogic
    private Long deleted;

    @TableField("version")
    @Version
    private Integer version;
}
