package com.cfive.pinnacle.service.permission;

import com.cfive.pinnacle.entity.permission.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
public interface IRoleService extends IService<Role> {
    List<Role> getAllRole();

    Role getRole(long id);

    boolean addRole(Role role);

    boolean modifyRole(Role role);
}
