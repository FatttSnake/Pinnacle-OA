package com.cfive.pinnacle.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cfive.pinnacle.entity.Notice;
import com.cfive.pinnacle.entity.NoticeReceive;
import com.cfive.pinnacle.mapper.NoticeReceiveMapper;
import com.cfive.pinnacle.service.INoticeReceiveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cfive.pinnacle.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 公告接收 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Service
public class NoticeReceiveServiceImpl extends ServiceImpl<NoticeReceiveMapper, NoticeReceive> implements INoticeReceiveService {
    @Autowired
    private NoticeReceiveMapper noticeReceiveMapper;
    @Override
    public List<Notice> selectByUserId(Integer readStatus) {
        Long userId = WebUtil.getLoginUser().getUser().getId();
        return noticeReceiveMapper.selectByUserId(userId,readStatus);
    }

    @Override
    public Boolean modifyNoticeIsRead(Notice notice) {
        Integer readStatus = null;
        if (null!=notice.getIsRead()){
            readStatus=notice.getIsRead()==0?1:0;
        }
        LambdaUpdateWrapper<NoticeReceive> luw = new LambdaUpdateWrapper<>();
        Long userId = WebUtil.getLoginUser().getUser().getId();
        luw.eq(NoticeReceive::getNoticeId, notice.getId()).eq(NoticeReceive::getUserId, userId).set(null!=readStatus,NoticeReceive::getAlreadyRead, readStatus);
        return noticeReceiveMapper.update(null,luw)>0;
    }
}
