package com.cfive.pinnacle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cfive.pinnacle.entity.Department;
import com.cfive.pinnacle.entity.User;
import com.cfive.pinnacle.mapper.DepartmentMapper;
import com.cfive.pinnacle.mapper.UserMapper;
import com.cfive.pinnacle.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<Department> getDepartAndUser() {
        List<Department> departments = departmentMapper.selectList(null);
        for (Department department:
             departments) {
            LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
            lqw.eq(User::getDepartmentId, department.getId());
            department.setUserList(userMapper.selectList(lqw));
        }
        return departments;
    }
}
