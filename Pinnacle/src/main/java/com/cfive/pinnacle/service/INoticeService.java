package com.cfive.pinnacle.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cfive.pinnacle.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 公告 服务类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
public interface INoticeService extends IService<Notice> {
    Notice selectByNoticeId(Long nid);

    List<Notice> selectAllNotice();

    List<Notice> selectByCond(String title, String type, String startTime, String endTime);

    Boolean deleteById(Long nid);

    Boolean deleteBatchByIds(List<Long> noticeIds);

    Boolean updateNotice(Notice notice);

    Boolean updateNoticeTop(Notice notice);

    Boolean addNotice(Notice notice);

    IPage<Notice> selectPageAllNotice(IPage<Notice> page);

    IPage<Notice> selectPageByCond(IPage<Notice> page, String title, String type, String startTime, String endTime);
}
