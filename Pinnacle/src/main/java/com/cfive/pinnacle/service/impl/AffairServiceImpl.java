package com.cfive.pinnacle.service.impl;

import com.cfive.pinnacle.entity.Affair;
import com.cfive.pinnacle.mapper.AffairMapper;
import com.cfive.pinnacle.service.IAffairService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public int insertAffair(Affair affair) {
        return affairMapper.insertAffair(affair);
    }

    public int updateAffair_Yes(Affair affair) {
        return affairMapper.updateAffairs_Yes(affair);
    }

    public int updateAffair_No(Affair affair) {
        return affairMapper.updateAffairs_NO(affair);
    }

    @Override
    public int deleteAffair_ApprovedByID(Affair affair) {
        return affairMapper.deleteAffairs(affair);
    }


}
