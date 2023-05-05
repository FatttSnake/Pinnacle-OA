package com.cfive.pinnacle.service.permission;

import com.cfive.pinnacle.entity.permission.Power;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cfive.pinnacle.entity.permission.PowerSet;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
public interface IPowerService extends IService<Power> {
    PowerSet getAllPower();
}
