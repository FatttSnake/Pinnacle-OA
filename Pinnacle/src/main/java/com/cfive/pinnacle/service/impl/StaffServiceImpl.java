package com.cfive.pinnacle.service.impl;

import com.cfive.pinnacle.entity.Staff;
import com.cfive.pinnacle.mapper.StaffMapper;
import com.cfive.pinnacle.service.IStaffService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements IStaffService {

}
