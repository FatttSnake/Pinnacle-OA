package com.cfive.pinnacle.service.impl;

import com.cfive.pinnacle.entity.Work;
import com.cfive.pinnacle.mapper.WorkMapper;
import com.cfive.pinnacle.service.IWorkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 工作事项 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Service
public class WorkServiceImpl extends ServiceImpl<WorkMapper, Work> implements IWorkService {

}
