package com.cfive.pinnacle.mapper;

import com.cfive.pinnacle.entity.Work;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 工作事项 Mapper 接口
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Mapper
public interface WorkMapper extends BaseMapper<Work> {
    List<Work> getAll();

    List<Work> getTodo(Long userId);

    List<Work> getComplete(Long userId);

}
