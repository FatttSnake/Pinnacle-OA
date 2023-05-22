package com.cfive.pinnacle.service.permission;

import com.cfive.pinnacle.entity.permission.Group;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户组 服务类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
public interface IGroupService extends IService<Group> {
    List<Group> getAllGroup();

    Group getGroup(Long id);

    boolean addGroup(Group group);

    boolean modifyGroup(Group group);
}
