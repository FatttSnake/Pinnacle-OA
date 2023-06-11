package com.cfive.pinnacle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cfive.pinnacle.entity.Affair;
import com.cfive.pinnacle.mapper.AffairMapper;
import com.cfive.pinnacle.service.IAffairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 事务 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Service
public class AffairServiceImpl extends ServiceImpl<AffairMapper, Affair> implements IAffairService {

    private AffairMapper affairMapper;

    @Autowired
    public void setAffairMapper(AffairMapper affairMapper) {
        this.affairMapper = affairMapper;
    }

    public int updateAffairYes(Affair affair) {
        return affairMapper.updateAffairsYes(affair);
    }

    public int updateAffairNo(Affair affair) {
        return affairMapper.updateAffairsNO(affair);
    }

    public List<Affair> getFuzzyQueriesByAffairTitle(String title, Long typeId, Integer status, Long applicantId) {
        return affairMapper.getFuzzyQueriesByAffairTitle(title, typeId, status, applicantId);
    }

    public List<Affair> getNotApprovedByFuzzyQueries(String title, Long typeId, Integer status, Long inspectorId, LocalDateTime startTime, LocalDateTime endTime) {
        return affairMapper.getNotApprovedByFuzzyQueries(title, typeId, status, inspectorId, startTime, endTime);
    }

    public List<Affair> getApprovedByFuzzyQueries(String title, Long typeId, Integer status, Long inspectorId, LocalDateTime startTime, LocalDateTime endTime) {
        return affairMapper.getApprovedByFuzzyQueries(title, typeId, status, inspectorId, startTime, endTime);
    }
}
