package com.cfive.pinnacle.notice;

import com.cfive.pinnacle.controller.NoticeController;
import com.cfive.pinnacle.entity.Notice;
import com.cfive.pinnacle.entity.NoticeReceive;
import com.cfive.pinnacle.entity.NoticeType;
import com.cfive.pinnacle.entity.User;
import com.cfive.pinnacle.entity.common.ResponseResult;
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
        ResponseResult selectByNoticeId = noticeController.selectByNoticeId(21L);
        System.out.println(selectByNoticeId.getData());
    }

    @Test
    void selectAllTest() {
        ResponseResult noticeList = noticeController.selectAllNotice();
        System.out.println(noticeList.getData());
    }

    @Test
    void updateTest() {
        ResponseResult notice = noticeController.selectByNoticeId(23L);
        ResponseResult updateNotice = noticeController.updateNotice((Notice) notice.getData());
        System.out.println(updateNotice.getData());
    }

    @Test
    void insertNoticeTest() {
        Notice notice = new Notice();
        notice.setTitle("title1");
        notice.setTypeId(1652684907554496514L);
        notice.setSenderId(1652714496280469506L);
        LocalDateTime sendTime = LocalDateTime.parse("2023-05-11 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse("2023-09-01 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        notice.setPriority(2);
        notice.setSendTime(sendTime);
        notice.setEndTime(endTime);
        notice.setContent("Content1");
        noticeController.addNotice(notice);
    }
    @Test
    void insertNoticeTypeTest(){
        NoticeType noticeType = new NoticeType();
        noticeType.setName("通知公告");
        iNoticeTypeService.save(noticeType);
    }

    @Test
    void insertNoticeRecTest(){
        NoticeReceive receive = new NoticeReceive();
        receive.setNoticeId(1652734384348790786L);
        receive.setUserId(1652714496280469506L);
        iNoticeReceiveService.save(receive);
    }


}
