package com.cfive.pinnacle.entity.permission;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 功能
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("t_operation")
public class Operation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    /**
     * 功能名
     */
    @TableField("name")
    private String name;

    /**
     * 功能编码
     */
    @TableField("code")
    private String code;

    /**
     * URL 前缀
     */
    @TableField("url_prefix")
    private String urlPrefix;

    /**
     *  权限ID
     */
    @TableField("power_id")
    private Long powerId;

    /**
     * 父ID
     */
    @TableField("parent_id")
    private Long parentId;
}