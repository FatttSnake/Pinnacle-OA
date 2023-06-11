package com.cfive.pinnacle.controller;

import com.cfive.pinnacle.entity.Attendance;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.IAttendanceService;
import com.cfive.pinnacle.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 考勤 前端控制器
 * </p>
 *
 * @author gzw
 * @since 2023-04-30
 */
@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private IAttendanceService attendanceService;

    @Autowired
    public void setAttendanceService(IAttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    //查询所有考勤信息和用户名
    @GetMapping("findAllAttendance")
    @PreAuthorize("hasAuthority('attendance:manage:get')")
    public ResponseResult<List<Attendance>> findAllAttendanceAndUser() {
        List<Attendance> attendances = attendanceService.getAllAttendanceAndUser();
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", attendances);
    }

    //查询个人考勤
    @GetMapping("/selectAttendance")
    @PreAuthorize("hasAuthority('attendance:self:check')")
    public ResponseResult<List<Attendance>> findAttendanceAndUser() {
        Long userId = WebUtil.getLoginUser().getUser().getId();
        List<Attendance> attendances = attendanceService.getAttendanceAndUserById(userId);
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", attendances);
    }

    //模糊时间查询所有考勤信息
    @GetMapping("/findAttendanceByTime")
    @PreAuthorize("hasAuthority('attendance:manage:get')")
    public ResponseResult<List<Attendance>> findAttendanceAndUserByTime(String startTime, String endTime) {
        List<Attendance> attendances = attendanceService.selectByTime(startTime, endTime);
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", attendances);
    }

    //用户个人模糊时间查询
    @GetMapping("/findOneAttendanceByTime")
    @PreAuthorize("hasAuthority('attendance:self:check')")
    public ResponseResult<List<Attendance>> findOneAttendanceAndUserByTime(String startTime, String endTime) {
        Long userId = WebUtil.getLoginUser().getUser().getId();
        List<Attendance> attendances = attendanceService.selectOneByTime(startTime, endTime, userId);
        System.out.println(attendances);
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", attendances);
    }

    //添加或更新考勤信息
    @PostMapping("/saveAttendance")
    @PreAuthorize("hasAuthority('attendance:manage:modify') and hasAuthority('attendance:manage:get')")
    public ResponseResult<?> saveAttendance(@RequestBody Attendance attendance) {
        attendance.setModifyId(1652714496280469506L);
        return attendanceService.saveOrUpdate(attendance) ? ResponseResult.build(ResponseCode.DATABASE_SAVE_OK, "success", attendance) :
                ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "error", null);
    }

    //个人签到
    @PostMapping("/saveOneAttendance")
    @PreAuthorize("hasAuthority('attendance:self:check')")
    public ResponseResult<?> saveOneAttendance(@RequestBody Attendance attendance) {
        attendance.setModifyId(1652714496280469506L);
        attendance.setUserId(WebUtil.getLoginUser().getUser().getId());
        if (attendance.getAttTime().getHour() > 1 && attendance.getAttTime().getHour() < 10) {
//            迟到
            attendance.setStatus(3);
            return attendanceService.save(attendance) ? ResponseResult.build(ResponseCode.DATABASE_SAVE_OK, "success", attendance) :
                    ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "error", null);
        } else if (attendance.getAttTime().getHour() >= 10 && attendance.getAttTime().getHour() < 15) {
//            签退
            attendance.setStatus(2);
            return attendanceService.save(attendance) ? ResponseResult.build(ResponseCode.DATABASE_SAVE_OK, "success", attendance) :
                    ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "error", null);
        } else if (attendance.getAttTime().getHour() <= 1) {
//            签到
            attendance.setStatus(1);
            return attendanceService.save(attendance) ? ResponseResult.build(ResponseCode.DATABASE_SAVE_OK, "success", attendance) :
                    ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "error", null);
        } else {
//            考勤异常
            attendance.setStatus(0);
            return attendanceService.save(attendance) ? ResponseResult.build(ResponseCode.DATABASE_SAVE_OK, "success", attendance) :
                    ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "error", null);
        }
    }

    //删除考勤信息
    @DeleteMapping("/delAttendance/{id}")
    @PreAuthorize("hasAuthority('attendance:manage:delete') and hasAuthority('attendance:manage:get')")
    public ResponseResult<?> delAttendance(@PathVariable Long id) {
        return attendanceService.removeById(id) ? ResponseResult.build(ResponseCode.DATABASE_DELETE_OK, "success", null) :
                ResponseResult.build(ResponseCode.DATABASE_DELETE_ERROR, "error", null);
    }

    //批量删除考勤信息
    @PostMapping("/delBatchAttendance")
    @PreAuthorize("hasAuthority('attendance:manage:delete') and hasAuthority('attendance:manage:get')")
    public ResponseResult<?> delBatchAttendance(@RequestBody List<Long> ids) {
        return attendanceService.removeByIds(ids) ? ResponseResult.build(ResponseCode.DATABASE_DELETE_OK, "success", null) :
                ResponseResult.build(ResponseCode.DATABASE_DELETE_ERROR, "error", null);
    }
}
