package com.cfive.pinnacle.service;

import com.cfive.pinnacle.entity.Notice;
import com.cfive.pinnacle.entity.NoticeReceive;
import com.baomidou.mybatisplus.extension.service.IService;

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
    List<Notice> selectByUserId(Integer readStatus);

    Boolean modifyNoticeIsRead(Notice notice);
}
