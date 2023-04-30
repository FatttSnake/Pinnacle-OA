package com.cfive.pinnacle.service.impl;

import com.cfive.pinnacle.entity.Department;
import com.cfive.pinnacle.mapper.DepartmentMapper;
import com.cfive.pinnacle.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 部门 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
