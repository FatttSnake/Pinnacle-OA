package com.cfive.pinnacle.service.permission;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
    IPage<Role> getAllRole(Long currentPage, Long pageSize, String searchName, List<Long> searchPower, Integer searchEnable, Integer searchRegex);

    Role getRole(long id);

    boolean addRole(Role role);

    boolean modifyRole(Role role);
}
