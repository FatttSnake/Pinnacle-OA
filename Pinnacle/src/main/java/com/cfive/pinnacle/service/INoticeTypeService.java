package com.cfive.pinnacle.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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

    IPage<NoticeType> selectPageTypeList(IPage<NoticeType> page);
    List<NoticeType> selectEnableTypeList();

    Boolean updateTypeEnableById(Long typeId, Integer enable);

    Boolean addNoticeType(NoticeType noticeType);

    Boolean updateNoticeType(NoticeType noticeType);

    Boolean deleteNoticeTypeById(Long typeId);

    Boolean deleteBatchByTypeIds(List<Long> typeIds);


}
