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


    public List<Affair> getFuzzyQueriesByAffairTitle(String title ,Integer typeId,Integer status,Integer inspectorId,String startTime, String endTime) {
        LocalDateTime start=null,end=null;
        if (startTime!=""&&endTime!=""){
            start= LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            end = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        List<Affair> affairList = affairMapper.getFuzzyQueriesByAffairTitle(title,typeId,status,inspectorId,start,end);
        return  affairList;
    }
}
