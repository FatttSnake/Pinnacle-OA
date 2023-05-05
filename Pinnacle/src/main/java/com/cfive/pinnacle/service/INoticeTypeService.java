package com.cfive.pinnacle.service;

import com.cfive.pinnacle.entity.NoticeType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 公告类型 服务类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
public interface INoticeTypeService extends IService<NoticeType> {
    List<NoticeType> selectTypeList();

}
