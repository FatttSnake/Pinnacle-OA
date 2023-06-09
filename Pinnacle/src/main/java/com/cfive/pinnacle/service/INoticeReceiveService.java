package com.cfive.pinnacle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cfive.pinnacle.entity.Notice;
import com.cfive.pinnacle.entity.NoticeReceive;

import java.util.List;

/**
 * <p>
 * 公告接收 服务类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
public interface INoticeReceiveService extends IService<NoticeReceive> {
    List<Notice> selectByUserId(Integer readStatus, String title, String type, String startTime, String endTime);

    List<Notice> selectLimitByUserId();

    Boolean modifyNoticeIsRead(Notice notice);

}
