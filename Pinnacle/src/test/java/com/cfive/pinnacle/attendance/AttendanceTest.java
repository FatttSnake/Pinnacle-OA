package com.cfive.pinnacle.attendance;

import com.cfive.pinnacle.controller.AttendanceController;
import com.cfive.pinnacle.entity.Attendance;
import com.cfive.pinnacle.service.IAttendanceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AttendanceTest {
    @Autowired
    IAttendanceService attendanceService;
    @Test
    void addTest() {
        Attendance attendance = new Attendance();
        attendance.setUserId(1L);
        attendance.setModifyId(1L);
        attendanceService.save(attendance);
    }

}
