package com.cfive.pinnacle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cfive.pinnacle.entity.Attendance;
import com.cfive.pinnacle.mapper.AttendanceMapper;
import com.cfive.pinnacle.mapper.UserMapper;
import com.cfive.pinnacle.service.IAttendanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 考勤 服务实现类
 * </p>
 *
 * @author gzw
 * @since 2023-04-30
 */
@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance> implements IAttendanceService {
    @Autowired
    private AttendanceMapper attendanceMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Attendance> getAllAttendanceAndUser() {
        return attendanceMapper.getAllAttendanceAndUser();
    }

    @Override
    public List<Attendance> getAttendanceAndUserByid(Long userId) {
        List<Attendance> attendances = attendanceMapper.getAttendanceAndUserByid(userId);
        return attendances;
    }

    @Override
    public List<Attendance> selectByTime(String startTime, String endTime) {
            LocalDateTime start = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime end = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            System.out.println(start);
            System.out.println(end);
            LambdaQueryWrapper<Attendance> lqw = new LambdaQueryWrapper<>();
            lqw.ge(null != start, Attendance::getAttTime, start).le(null != end, Attendance::getAttTime, end);
            List<Attendance> attendancesByTime = attendanceMapper.selectList(lqw);
        for (Attendance attendance:
             attendancesByTime) {
            attendance.setUser(userMapper.selectById(attendance.getUserId()));
        }
            return attendancesByTime;

    }
    @Override
    public List<Attendance> selectOneByTime(String startTime, String endTime,Long userId) {
        LocalDateTime start = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(start);
        System.out.println(end);
        LambdaQueryWrapper<Attendance> lqw = new LambdaQueryWrapper<>();
        lqw.ge(null != start, Attendance::getAttTime, start).le(null != end, Attendance::getAttTime, end).eq(Attendance::getDeleted,0).eq(Attendance::getUserId,userId);
        List<Attendance> oneAttendancesByTime = attendanceMapper.selectList(lqw);
        for (Attendance attendance:
                oneAttendancesByTime) {
            attendance.setUser(userMapper.selectById(userId));
        }
        return oneAttendancesByTime;

    }
}
