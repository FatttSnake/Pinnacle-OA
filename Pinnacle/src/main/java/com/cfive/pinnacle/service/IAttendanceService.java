package com.cfive.pinnacle.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cfive.pinnacle.entity.Attendance;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 考勤 服务类
 * </p>
 *
 * @author gzw
 * @since 2023-04-30
 */
public interface IAttendanceService extends IService<Attendance> {
    List<Attendance> getAllAttendanceAndUser();
    List<Attendance> getAttendanceAndUserByid(Long userId);


    List<Attendance> selectByTime(String startTime,String endTime);
    List<Attendance> selectOneByTime(String startTime,String endTime,Long userId);


}
