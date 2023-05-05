package com.cfive.pinnacle.service;

import com.cfive.pinnacle.entity.Affair;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 事务 服务类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
public interface IAffairService extends IService<Affair> {

    int insertAffair(Affair affair);
    int updateAffair_Yes(Affair affair);
    int updateAffair_No(Affair affair);


    int deleteAffair_ApprovedByID(Affair affair);
}
