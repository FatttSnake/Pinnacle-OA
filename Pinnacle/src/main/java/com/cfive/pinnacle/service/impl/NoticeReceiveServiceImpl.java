package com.cfive.pinnacle.service.impl;

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
}
