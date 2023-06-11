package com.cfive.pinnacle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cfive.pinnacle.entity.Affair;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 事务 Mapper 接口
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Mapper
public interface AffairMapper extends BaseMapper<Affair> {

    @Update("update  t_affair set status=1,inspect_time=#{inspectTime} where id=#{id}")
    int updateAffairsYes(Affair affair);
    //管理员权限--->修改事务的状态(AffairsStatus)--->达到审批的效果
    //同意

    @Update("update t_affair set Status=2 where id=#{id}")
    int updateAffairsNO(Affair affair);
    //不同意

    List<Affair> getFuzzyQueriesByAffairTitle(String title, Long typeId, Integer status, Long applicantId);

    List<Affair> getNotApprovedByFuzzyQueries(String title, Long typeId, Integer status, Long inspectorId, LocalDateTime startTime, LocalDateTime endTime);

    List<Affair> getApprovedByFuzzyQueries(String title, Long typeId, Integer status, Long inspectorId, LocalDateTime startTime, LocalDateTime endTime);

}
