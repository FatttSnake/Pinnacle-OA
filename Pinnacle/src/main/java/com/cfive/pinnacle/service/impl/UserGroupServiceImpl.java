package com.cfive.pinnacle.service.impl;

import com.cfive.pinnacle.entity.UserGroup;
import com.cfive.pinnacle.mapper.UserGroupMapper;
import com.cfive.pinnacle.service.IUserGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 中间表-用户-用户组 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements IUserGroupService {

}
