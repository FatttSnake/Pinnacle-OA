package com.cfive.pinnacle.service.permission;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cfive.pinnacle.entity.permission.Group;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户组 服务类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
public interface IGroupService extends IService<Group> {
    IPage<Group> getAllGroup(Long currentPage, Long pageSize);

    Group getGroup(Long id);

    boolean addGroup(Group group);

    boolean modifyGroup(Group group);
}
