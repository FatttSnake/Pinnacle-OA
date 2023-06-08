package com.cfive.pinnacle.service.impl;

import com.cfive.pinnacle.entity.Affair;
import com.cfive.pinnacle.entity.permission.User;
import com.cfive.pinnacle.mapper.AffairMapper;
import com.cfive.pinnacle.service.IAffairService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private AffairMapper affairMapper;

    public int updateAffairYes(Affair affair) {
        return affairMapper.updateAffairsYes(affair);
    }

    public int updateAffairNo(Affair affair) {
        return affairMapper.updateAffairsNO(affair);
    }

    public List<User> getSameDepartmentUser(long id) {
        List<User> user = affairMapper.getSameDepartmentUser(id);
        return user;
    }


    public List<Affair> getFuzzyQueriesByAffairTitle(String title ,Long typeId,Integer status,Long applicantId) {
        List<Affair> affairList = affairMapper.getFuzzyQueriesByAffairTitle(title,typeId,status,applicantId);
        return  affairList;
    }


    public List<Affair> getNotApprovedByFuzzyQueries(String title, Long typeId, Integer status, Long applicantId, LocalDateTime startTime ,LocalDateTime endTime) {
        List<Affair> affairList =affairMapper.getNotApprovedByFuzzyQueries(title,typeId,status,applicantId,startTime,endTime);
        return affairList;
    }
}
