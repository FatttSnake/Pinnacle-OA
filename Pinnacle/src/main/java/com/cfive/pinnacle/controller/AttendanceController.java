package com.cfive.pinnacle.controller;

import com.cfive.pinnacle.entity.Attendance;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.IAttendanceService;
import com.cfive.pinnacle.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin
@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private IAttendanceService attendanceService;


    //查询所有考勤信息和用户名
    @GetMapping("findAllAttendance")
    public ResponseResult findAllAttendanceAndUser() {
        List<Attendance> attendances = attendanceService.getAllAttendanceAndUser();
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", attendances);
    }
    //查询个人考勤
    @GetMapping("/selectAttendance")
    public ResponseResult findAttendanceAndUser() {
        Long userId = WebUtil.getLoginUser().getUser().getId();
        List<Attendance> attendances = attendanceService.getAttendanceAndUserByid(userId);
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", attendances);
    }
    //模糊时间查询所有考勤信息
    @GetMapping("/findAttendanceByTime")
    public ResponseResult findAttendanceAndUserByTime(String startTime,String endTime) {
        List<Attendance> attendances = attendanceService.selectByTime(startTime, endTime);
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", attendances);
    }
    //用户个人模糊时间查询
    @GetMapping("/findOneAttendanceByTime")
    public ResponseResult findOneAttendanceAndUserByTime(String startTime,String endTime) {
        Long userId = WebUtil.getLoginUser().getUser().getId();
        List<Attendance> attendances = attendanceService.selectOneByTime(startTime, endTime,userId);
        System.out.println(attendances);
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", attendances);
    }
    //添加或更新考勤信息
    @PostMapping("/saveAttendance")
    public ResponseResult saveAttendance(@RequestBody Attendance attendance) {
        attendance.setModifyId(1652714496280469506L);
        return attendanceService.saveOrUpdate(attendance) ? ResponseResult.build(ResponseCode.DATABASE_SAVE_OK, "success", attendance) :
                ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "error", null);

    }

    //个人签到
    @PostMapping("/saveOneAttendance")
    public ResponseResult saveOneAttendance(@RequestBody Attendance attendance) {
        attendance.setModifyId(1652714496280469506L);
        attendance.setUserId(WebUtil.getLoginUser().getUser().getId());
        if (attendance.getAttTime().getHour() > 1 && attendance.getAttTime().getHour() < 10) {
//            迟到
            attendance.setStatus(3);
            return attendanceService.save(attendance) ? ResponseResult.build(ResponseCode.DATABASE_SAVE_OK, "success", attendance) :
                    ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "error", null);
        } else if (attendance.getAttTime().getHour() >= 10&& attendance.getAttTime().getHour() < 15) {
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
    public ResponseResult delAttendance(@PathVariable Long id) {
        return attendanceService.removeById(id) ? ResponseResult.build(ResponseCode.DATABASE_DELETE_OK, "success", null) :
                ResponseResult.build(ResponseCode.DATABASE_DELETE_ERROR, "error", null);
    }

    //批量删除考勤信息
    @PostMapping("/delBatchAttendance")
    public ResponseResult delBatchAttendance(@RequestBody List<Long> ids) {
        return attendanceService.removeByIds(ids) ? ResponseResult.build(ResponseCode.DATABASE_DELETE_OK, "success", null) :
                ResponseResult.build(ResponseCode.DATABASE_DELETE_ERROR, "error", null);
    }



}
