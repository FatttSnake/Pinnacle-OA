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
 * 页面元素
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("t_element")
public class Element implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    /**
     * 元素名
     */
    @TableField("name")
    private String name;

    /**
     *  权限ID
     */
    @TableField("power_id")
    private Long powerId;
}
