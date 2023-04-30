package com.cfive.pinnacle.service.impl;

import com.cfive.pinnacle.entity.User;
import com.cfive.pinnacle.mapper.UserMapper;
import com.cfive.pinnacle.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
