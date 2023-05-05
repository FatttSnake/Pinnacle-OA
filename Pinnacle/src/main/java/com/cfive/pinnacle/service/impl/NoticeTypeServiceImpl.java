package com.cfive.pinnacle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cfive.pinnacle.entity.NoticeType;
import com.cfive.pinnacle.mapper.NoticeTypeMapper;
import com.cfive.pinnacle.service.INoticeTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 公告类型 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Service
public class NoticeTypeServiceImpl extends ServiceImpl<NoticeTypeMapper, NoticeType> implements INoticeTypeService {
    @Autowired
    NoticeTypeMapper noticeTypeMapper;
    @Override
    public List<NoticeType> selectTypeList() {
        LambdaQueryWrapper<NoticeType> lqw = new LambdaQueryWrapper<>();
        lqw.eq(NoticeType::getEnable, 1);
        List<NoticeType> noticeTypes = noticeTypeMapper.selectList(lqw);
        return noticeTypes;
    }
}
