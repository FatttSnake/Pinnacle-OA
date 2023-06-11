package com.cfive.pinnacle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cfive.pinnacle.entity.Affair;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 事务 服务类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
public interface IAffairService extends IService<Affair> {

    int updateAffairYes(Affair affair);

    int updateAffairNo(Affair affair);

    List<Affair> getFuzzyQueriesByAffairTitle(String title, Long typeId, Integer status, Long applicantId);

    List<Affair> getNotApprovedByFuzzyQueries(String title, Long typeId, Integer status, Long inspectorId, LocalDateTime startTime, LocalDateTime endTime);

    List<Affair> getApprovedByFuzzyQueries(String title, Long typeId, Integer status, Long inspectorId, LocalDateTime startTime, LocalDateTime endTime);

}
