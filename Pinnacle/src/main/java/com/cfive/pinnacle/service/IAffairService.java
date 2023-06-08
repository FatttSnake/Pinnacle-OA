package com.cfive.pinnacle.service;

import com.cfive.pinnacle.entity.Affair;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cfive.pinnacle.entity.permission.User;

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

//    int insertAffair(Affair affair);
    int updateAffairYes(Affair affair);
    int updateAffairNo(Affair affair);


//    int deleteAffair_ApprovedByID(Affair affair);
    List<User> getSameDepartmentUser (long id);
    List<Affair> getFuzzyQueriesByAffairTitle(String title,Long typeId,Integer status,Long applicantId);

    List<Affair> getNotApprovedByFuzzyQueries(String title,Long typeId,Integer status,Long applicantId,LocalDateTime startTime,LocalDateTime endTime);


}
