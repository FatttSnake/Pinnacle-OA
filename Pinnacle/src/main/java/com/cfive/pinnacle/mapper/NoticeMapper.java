package com.cfive.pinnacle.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cfive.pinnacle.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 公告 Mapper 接口
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    Notice selectByNoticeId(Long nid);

    List<Notice> selectAllNotice();

    List<Notice> selectByCond(String title, String type, LocalDateTime startTime, LocalDateTime endTime);

    IPage<Notice> selectPageAllNotice(IPage<?> page);

    IPage<Notice> selectPageByCond(IPage<?> page, String title, String type, LocalDateTime startTime, LocalDateTime endTime);
}
