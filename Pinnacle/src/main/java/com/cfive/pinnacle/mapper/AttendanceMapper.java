package com.cfive.pinnacle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cfive.pinnacle.entity.Attendance;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 考勤 Mapper 接口
 * </p>
 *
 * @author gzw
 * @since 2023-04-30
 */
@Mapper
public interface AttendanceMapper extends BaseMapper<Attendance> {

    List<Attendance> getAllAttendanceAndUser();

    List<Attendance> getAttendanceAndUserById(Long userId);

}
