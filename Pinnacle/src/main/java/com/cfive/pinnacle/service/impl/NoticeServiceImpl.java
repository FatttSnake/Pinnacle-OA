package com.cfive.pinnacle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cfive.pinnacle.entity.Notice;
import com.cfive.pinnacle.entity.NoticeReceive;
import com.cfive.pinnacle.entity.NoticeType;
import com.cfive.pinnacle.mapper.NoticeMapper;
import com.cfive.pinnacle.mapper.NoticeReceiveMapper;
import com.cfive.pinnacle.mapper.NoticeTypeMapper;
import com.cfive.pinnacle.mapper.UserMapper;
import com.cfive.pinnacle.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cfive.pinnacle.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        List<Notice> notices = noticeMapper.selectAllNotice();
        return notices;
    }

    @Override
    public List<Notice> selectByCond(String title, String type, String startTime,String endTime) {
        LocalDateTime start;
        LocalDateTime end;
        try {
            start = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            end = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            start = null;
            end = null;
        }
//        LambdaQueryWrapper<Notice> lqw_notice = new LambdaQueryWrapper<>();
//        LambdaQueryWrapper<NoticeType> lqw_type = new LambdaQueryWrapper<>();
//        lqw_type.like(null != type, NoticeType::getName, type);
//        List<NoticeType> noticeTypes = noticeTypeMapper.selectList(lqw_type);
//        for (NoticeType noticeType : noticeTypes
//        ) {
//            lqw_notice.clear();
//            lqw_notice.eq(!noticeTypes.isEmpty(), Notice::getTypeId, noticeType.getId()).like(null != title, Notice::getTitle, title);
//            lqw_notice.ge(StringUtils.hasText(startTime), Notice::getSendTime, start);
//            lqw_notice.le(StringUtils.hasText(endTime), Notice::getEndTime, end);
//            lqw_notice.eq(Notice::getOld, 0);
//            List<Notice> temp_notice = noticeMapper.selectList(lqw_notice);
//            notices.addAll(temp_notice);
//        }
//        for (Notice n : notices
//        ) {
//            n.setSender(userMapper.selectById(n.getSenderId()));
//            n.setNoticeType(noticeTypeMapper.selectById(n.getTypeId()));
//        }

        List<Notice> notices=noticeMapper.selectByCond(title, type, start, end);
        return notices;
    }


    @Override
    public Boolean deleteById(Long nid) {
        LambdaQueryWrapper<NoticeReceive> lqw = new LambdaQueryWrapper<>();
        lqw.eq(NoticeReceive::getNoticeId, nid);
        Boolean flag=noticeReceiveMapper.delete(lqw)>0;
        return flag&&noticeMapper.deleteById(nid) > 0;
    }

    @Override
    public Boolean updateNotice(Notice notice) {
        noticeMapper.update(null, new UpdateWrapper<Notice>().eq("id", notice.getId()).set("old", 1)); //修改原始数据
        notice.setOriginId(notice.getId());
        notice.setId(null); //清除id，使新插入的数据id重新生成
        notice.setModifyTime(null);
        notice.setOld(0);
        return noticeMapper.insert(notice) > 0;
    }

    @Override
    public Boolean addNotice(Notice notice) {
        Boolean noticeFlag,noticeRecFlag=false;
        notice.setSenderId(WebUtil.getLoginUser().getUser().getId());
//        notice.setSenderId(1652714496280469506L);
        noticeFlag = noticeMapper.insert(notice)>0;
        Long noticeId = notice.getId();
        if (notice.getReceivers().size()==0){
            //该公告仅发布者自己可见
            NoticeReceive noticeReceive = new NoticeReceive();
            noticeReceive.setNoticeId(noticeId);
            noticeReceive.setUserId(WebUtil.getLoginUser().getUser().getId());
            noticeRecFlag = noticeReceiveMapper.insert(noticeReceive)>0;
        }else {
            for (Long receiveId :
                    notice.getReceivers()) {
                NoticeReceive noticeReceive = new NoticeReceive();
                noticeReceive.setNoticeId(noticeId);
                noticeReceive.setUserId(receiveId);
                noticeRecFlag = noticeReceiveMapper.insert(noticeReceive)>0;
                if (!noticeRecFlag){
                    break;
                }
            }
        }
        return noticeFlag && noticeRecFlag;
    }

    @Override
    public IPage<Notice> selectPageAllNotice(IPage<?> page) {
        return noticeMapper.selectPageAllNotice(page);
    }

    @Override
    public IPage<Notice> selectPageByCond(IPage<?> page, String title, String type, String startTime, String endTime) {
        LocalDateTime start;
        LocalDateTime end;
        try {
            start = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            end = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            start = null;
            end = null;
        }
        return noticeMapper.selectPageByCond(page,title, type, start, end);
    }

}
