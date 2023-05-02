package com.cfive.pinnacle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cfive.pinnacle.entity.Notice;
import com.cfive.pinnacle.entity.NoticeReceive;
import com.cfive.pinnacle.mapper.NoticeMapper;
import com.cfive.pinnacle.mapper.NoticeReceiveMapper;
import com.cfive.pinnacle.mapper.NoticeTypeMapper;
import com.cfive.pinnacle.mapper.UserMapper;
import com.cfive.pinnacle.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 公告 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {
    @Autowired
    NoticeMapper noticeMapper;
    @Autowired
    NoticeTypeMapper noticeTypeMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    NoticeReceiveMapper noticeReceiveMapper;

    @Override
    public Notice selectByNoticeId(Long nid) {
        return noticeMapper.selectByNoticeId(nid);
    }

    @Override
    public List<Notice> selectAllNotice() {
        return noticeMapper.selectAllNotice();
    }

    @Override
    public List<Notice> selectByTitle(String title) {
        LambdaQueryWrapper<Notice> lqw = new LambdaQueryWrapper<>();
        lqw.like(Notice::getTitle, title);
        List<Notice> notices = noticeMapper.selectList(lqw);
        for (Notice n : notices
        ) {
            n.setSender(userMapper.selectById(n.getSenderId()));
            n.setNoticeType(noticeTypeMapper.selectById(n.getTypeId()));
        }
        return notices;
    }

    @Override
    public Boolean deleteById(Long nid) {
        LambdaQueryWrapper<NoticeReceive> lqw = new LambdaQueryWrapper<>();
        lqw.eq(NoticeReceive::getNoticeId, nid);
        List<NoticeReceive> noticeReceives = noticeReceiveMapper.selectList(lqw);
        for (NoticeReceive nrc :
                noticeReceives) {
            noticeReceiveMapper.deleteById(nrc.getId());
        }
        return noticeMapper.deleteById(nid) > 0;
    }

}
