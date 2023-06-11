package com.cfive.pinnacle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cfive.pinnacle.entity.NoticeType;
import com.cfive.pinnacle.mapper.NoticeTypeMapper;
import com.cfive.pinnacle.service.INoticeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    NoticeTypeMapper noticeTypeMapper;

    @Autowired
    public void setNoticeTypeMapper(NoticeTypeMapper noticeTypeMapper) {
        this.noticeTypeMapper = noticeTypeMapper;
    }

    @Override
    public List<NoticeType> selectTypeList() {
        LambdaQueryWrapper<NoticeType> lqw = new LambdaQueryWrapper<>();
        lqw.orderByDesc(NoticeType::getId);
        return noticeTypeMapper.selectList(lqw);
    }

    @Override
    public IPage<NoticeType> selectPageTypeList(IPage<NoticeType> page, String name, Integer enable) {
        LambdaQueryWrapper<NoticeType> lqw = new LambdaQueryWrapper<>();
        lqw.like(null != name && !name.equals(""), NoticeType::getName, name);
        lqw.eq(null != enable && enable != -1, NoticeType::getEnable, enable);
        lqw.orderByDesc(NoticeType::getId);
        return noticeTypeMapper.selectPage(page, lqw);
    }

    @Override
    public List<NoticeType> selectEnableTypeList() {
        LambdaQueryWrapper<NoticeType> lqw = new LambdaQueryWrapper<>();
        lqw.eq(NoticeType::getEnable, 1);
        return noticeTypeMapper.selectList(lqw);
    }

    @Override
    public Boolean updateTypeEnableById(Long typeId, Integer enable) {
        if ((null == typeId) || (null == enable)) {
            return false;
        }
        LambdaUpdateWrapper<NoticeType> luw = new LambdaUpdateWrapper<>();
        luw.eq(NoticeType::getId, typeId).set(NoticeType::getEnable, enable);
        return noticeTypeMapper.update(null, luw) > 0;
    }

    @Override
    public Boolean addNoticeType(NoticeType noticeType) {
        return noticeTypeMapper.insert(noticeType) > 0;
    }

    @Override
    public Boolean updateNoticeType(NoticeType noticeType) {
        return noticeTypeMapper.updateById(noticeType) > 0;
    }

    @Override
    public Boolean deleteNoticeTypeById(Long typeId) {
        return noticeTypeMapper.deleteById(typeId) > 0;
    }

    @Override
    @Transactional
    public Boolean deleteBatchByTypeIds(List<Long> typeIds) {
        return noticeTypeMapper.deleteBatchIds(typeIds) > 0;
    }
}
