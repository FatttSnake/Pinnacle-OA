package com.cfive.pinnacle.service.impl;

import com.cfive.pinnacle.entity.Group;
import com.cfive.pinnacle.mapper.GroupMapper;
import com.cfive.pinnacle.service.IGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户组 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {

}
