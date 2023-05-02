package com.cfive.pinnacle.service;

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

    List<Notice> selectByTitle(String title);

    Boolean deleteById(Long nid);
}
