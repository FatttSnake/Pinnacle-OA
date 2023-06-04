package com.cfive.pinnacle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cfive.pinnacle.entity.Notice;
import com.cfive.pinnacle.entity.NoticeReceive;
import com.cfive.pinnacle.mapper.NoticeMapper;
import com.cfive.pinnacle.mapper.NoticeReceiveMapper;
import com.cfive.pinnacle.mapper.NoticeTypeMapper;
import com.cfive.pinnacle.mapper.permission.UserMapper;
import com.cfive.pinnacle.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cfive.pinnacle.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public IPage<Notice> selectPageNotice(IPage<Notice> page, String title, String type, String startTime, String endTime,List<Long> userIdList) {
        LocalDateTime start=null,end=null;
        if (startTime!=""&&endTime!=""){
            start= LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            end = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return noticeMapper.selectPageNotice(page, title, type, start, end,userIdList);
    }

    @Override
    public Boolean deleteById(Long nid) {
        LambdaQueryWrapper<NoticeReceive> lqw = new LambdaQueryWrapper<>();
        lqw.eq(NoticeReceive::getNoticeId, nid);
        boolean flag = noticeReceiveMapper.delete(lqw) > 0;
        return flag && noticeMapper.deleteById(nid) > 0;
    }

    @Override
    public Boolean deleteBatchByIds(List<Long> noticeIds) {
        boolean flag = false;
        for (Long nid :
                noticeIds) {
            LambdaQueryWrapper<NoticeReceive> lqw = new LambdaQueryWrapper<>();
            lqw.eq(NoticeReceive::getNoticeId, nid);
            flag = noticeReceiveMapper.delete(lqw) > 0;
            if (!flag) {
                break;
            }
        }
        return flag && noticeMapper.deleteBatchIds(noticeIds) > 0;
    }

    @Override
    public Boolean updateNotice(Notice notice) {
        noticeMapper.update(null, new UpdateWrapper<Notice>().eq("id", notice.getId()).set("old", 1)); //修改原始数据
        LambdaQueryWrapper<NoticeReceive> lqw = new LambdaQueryWrapper<>();
        lqw.eq(NoticeReceive::getNoticeId, notice.getId());
        noticeReceiveMapper.delete(lqw);
        notice.setOriginId(notice.getId());
        notice.setId(null); //清除id，使新插入的数据id重新生成
        notice.setModifyTime(null);
        notice.setOld(0);
        return this.addNotice(notice);
    }

    @Override
    public Boolean updateNoticeTop(Notice notice) {
        notice.setTop(notice.getTop() == 1 ? 0 : 1);
        return noticeMapper.updateById(notice) > 0;
    }

    @Override
    public Boolean addNotice(Notice notice) {
        boolean noticeFlag, noticeRecFlag = false;
        notice.setSenderId(WebUtil.getLoginUser().getUser().getId());
//        notice.setSenderId(1652714496280469506L);
        noticeFlag = noticeMapper.insert(notice) > 0;
        Long noticeId = notice.getId();
        if (notice.getReceivers().size() != 0) {
            for (Long receiveId :
                    notice.getReceivers()) {
                NoticeReceive noticeReceive = new NoticeReceive();
                noticeReceive.setNoticeId(noticeId);
                noticeReceive.setUserId(receiveId);
                noticeRecFlag = noticeReceiveMapper.insert(noticeReceive) > 0;
                if (!noticeRecFlag) {
                    break;
                }
            }
        } else {
            noticeFlag = false;
        }
        return noticeFlag && noticeRecFlag;
    }


}
