package com.cfive.pinnacle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cfive.pinnacle.entity.Notice;
import com.cfive.pinnacle.entity.NoticeReceive;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 公告接收 Mapper 接口
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Mapper
public interface NoticeReceiveMapper extends BaseMapper<NoticeReceive> {
    List<Notice> selectByUserId(Long userId, Integer readStatus, String title, String type, LocalDateTime startTime, LocalDateTime endTime);

    List<Notice> selectLimitByUserId(Long userId);

}
