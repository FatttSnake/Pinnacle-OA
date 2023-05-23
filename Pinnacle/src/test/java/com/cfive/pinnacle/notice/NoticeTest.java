package com.cfive.pinnacle.notice;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cfive.pinnacle.controller.NoticeController;
import com.cfive.pinnacle.entity.*;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.entity.permission.User;
import com.cfive.pinnacle.mapper.NoticeMapper;
import com.cfive.pinnacle.service.*;
import com.cfive.pinnacle.service.permission.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class NoticeTest {
    @Autowired
    private NoticeController noticeController;
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private INoticeService iNoticeService;
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
    }

    @Test
    void updateTest() {
        ResponseResult notice = noticeController.selectByNoticeId(23L);
        ResponseResult updateNotice = noticeController.updateNotice((Notice) notice.getData());
    }

    @Test
    void insertNoticeTest() {
        for (int i = 11; i < 40; i++) {
            Notice notice = new Notice();
            notice.setTitle("title" + i);
            notice.setTypeId(1654069011361476609L);
            notice.setSenderId(1652714496280469506L);
            LocalDateTime sendTime = LocalDateTime.parse("2023-05-11 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime endTime = LocalDateTime.parse("2023-09-01 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            notice.setPriority(2);
            notice.setSendTime(sendTime);
            notice.setEndTime(endTime);
            notice.setContent("Content" + i);
            noticeMapper.insert(notice);
        }
    }

    @Test
    void insertNoticeTypeTest() {
//        NoticeType noticeType = new NoticeType();
//        noticeType.setName("调休公告");
//        iNoticeTypeService.save(noticeType);

//        Department department=new Department();
//        department.setName("前端开发部");
//        department.setTel("123");
//        department.setAddress("潮州");
//        iDepartmentService.save(department);

        User user = new User();
        user.setUsername("yrm");
        user.setPasswd("123");
        user.setDepartmentId(1654150127317569537L);
        user.setEnable(1);
        iUserService.save(user);
    }

    @Test
    void insertNoticeRecTest() {
        NoticeReceive receive = new NoticeReceive();
        receive.setNoticeId(1655408487006437377L);
        receive.setUserId(1652714496280469506L);
        iNoticeReceiveService.save(receive);
    }

    @Test
    void selectByConde() {
        List<Notice> notices = new ArrayList<>();
        LambdaQueryWrapper<Notice> lqw_notice = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<NoticeType> lqw_type = new LambdaQueryWrapper<>();
        lqw_type.like(NoticeType::getName, "活动");
        List<NoticeType> noticeTypes = iNoticeTypeService.list(lqw_type);
        if (noticeTypes.isEmpty()) {
        } else {
            for (NoticeType type : noticeTypes
            ) {
                lqw_notice.clear();
                lqw_notice.eq(Notice::getTypeId, type.getId()).like(Notice::getTitle, "2");
                List<Notice> temp_notice = iNoticeService.list(lqw_notice);
                notices.addAll(temp_notice);
            }
            for (Notice n:notices
                 ) {
                n.setSender(iUserService.getById(n.getSenderId()));
                n.setNoticeType(iNoticeTypeService.getById(n.getTypeId()));
            }
        }
    }


}
