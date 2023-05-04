package com.cfive.pinnacle.service.permission.impl;

import com.cfive.pinnacle.entity.permission.Operation;
import com.cfive.pinnacle.mapper.permission.OperationMapper;
import com.cfive.pinnacle.service.permission.IOperationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 功能 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Service
public class OperationServiceImpl extends ServiceImpl<OperationMapper, Operation> implements IOperationService {

}
