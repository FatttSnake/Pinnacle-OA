package com.cfive.pinnacle.service.impl;

import com.cfive.pinnacle.entity.UserRole;
import com.cfive.pinnacle.mapper.UserRoleMapper;
import com.cfive.pinnacle.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 中间表-用户-角色 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
