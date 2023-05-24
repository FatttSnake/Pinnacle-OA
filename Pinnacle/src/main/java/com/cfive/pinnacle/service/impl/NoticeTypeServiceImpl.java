package com.cfive.pinnacle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
        lqw.orderByDesc(NoticeType::getId);
        return noticeTypeMapper.selectList(lqw);
    }

    @Override
    public List<NoticeType> selectEnableTypeList() {
        LambdaQueryWrapper<NoticeType> lqw = new LambdaQueryWrapper<>();
        lqw.eq(NoticeType::getEnable, 1);
        List<NoticeType> noticeTypes = noticeTypeMapper.selectList(lqw);
        return noticeTypes;
    }

    @Override
    public Boolean updateTypeEnableById(Long typeId, Integer enable) {
        if ((null==typeId)||(null==enable)){
            return false;
        }
        LambdaUpdateWrapper<NoticeType> luw = new LambdaUpdateWrapper<>();
        luw.eq(null!=typeId,NoticeType::getId, typeId).set(null!=enable,NoticeType::getEnable,enable);
        return noticeTypeMapper.update(null, luw)>0;
    }

    @Override
    public Boolean addNoticeType(NoticeType noticeType) {
        return noticeTypeMapper.insert(noticeType)>0;
    }

    @Override
    public Boolean updateNoticeType(NoticeType noticeType) {
        return noticeTypeMapper.updateById(noticeType)>0;
    }

    @Override
    public Boolean deleteNoticeTypeById(Long typeId) {
        return noticeTypeMapper.deleteById(typeId)>0;
    }
}
