package com.cfive.pinnacle.service.impl;

import com.cfive.pinnacle.entity.OperationLog;
import com.cfive.pinnacle.mapper.OperationLogMapper;
import com.cfive.pinnacle.service.IOperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements IOperationLogService {

}
