package com.cfive.pinnacle.notice;

import com.cfive.pinnacle.controller.NoticeController;
import com.cfive.pinnacle.controller.Result;
import com.cfive.pinnacle.entity.Department;
import com.cfive.pinnacle.entity.Notice;
import com.cfive.pinnacle.entity.NoticeType;
import com.cfive.pinnacle.entity.User;
import com.cfive.pinnacle.service.IDepartmentService;
import com.cfive.pinnacle.service.INoticeReceiveService;
import com.cfive.pinnacle.service.INoticeTypeService;
import com.cfive.pinnacle.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class NoticeTest {
    @Autowired
    private NoticeController noticeController;
    @Autowired
    private INoticeTypeService iNoticeTypeService;
    @Autowired
    private IDepartmentService iDepartmentService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private INoticeReceiveService iNoticeReceiveService;


    @Test
    void selectByIdTest() {
        Result notice = noticeController.selectByNoticeId(21L);
        System.out.println(notice.getData());
    }

    @Test
    void selectAllTest() {
        Result noticeList = noticeController.selectAllNoticeId();
        System.out.println(noticeList.getData());
    }

    @Test
    void updateTest() {
        Result notice =noticeController.selectByNoticeId(23L);
        Result updateNotice = noticeController.updateNotice((Notice)notice.getData());
        System.out.println(updateNotice.getData());
    }

    @Test
    void insertNoticeTest() {
        Notice notice = new Notice();
        notice.setTitle("title1");
        notice.setTypeId(1652684907554496514L);
        notice.setSenderId(1652714496280469506L);
        LocalDateTime startDate = LocalDateTime.parse("2023-03-21 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDate = LocalDateTime.parse("2023-09-01 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        notice.setEffectiveDate(startDate);
//        notice.setEndDate(endDate);
//        notice.setSenderID(2);
        notice.setContent("Content1");
        Result updateNotice = noticeController.addNotice(notice);
        System.out.println(updateNotice.getData());
    }
    @Test
    void insertNoticeTypeTest(){
        NoticeType noticeType = new NoticeType();
        noticeType.setName("通知公告");
        iNoticeTypeService.save(noticeType);
    }

    @Test
    void insertNoticeRecTest(){
        User user = new User();
        user.setUsername("cyb");
        user.setPasswd("123");
        user.setDepartmentId(1652713919467151362L);
        iUserService.save(user);
//        iNoticeTypeService.save(noticeType);
    }
}
