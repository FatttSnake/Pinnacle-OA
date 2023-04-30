package com.cfive.pinnacle.service.impl;

import com.cfive.pinnacle.entity.Attendance;
import com.cfive.pinnacle.mapper.AttendanceMapper;
import com.cfive.pinnacle.service.IAttendanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 考勤 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance> implements IAttendanceService {

}
