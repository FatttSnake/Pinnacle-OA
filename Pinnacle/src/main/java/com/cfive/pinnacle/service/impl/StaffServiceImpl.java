package com.cfive.pinnacle.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.cfive.pinnacle.entity.Staff;
import com.cfive.pinnacle.entity.permission.User;
import com.cfive.pinnacle.exception.DataValidationFailedException;
import com.cfive.pinnacle.mapper.StaffMapper;
import com.cfive.pinnacle.mapper.permission.UserMapper;
import com.cfive.pinnacle.service.IStaffService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cfive.pinnacle.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 员工 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements IStaffService {
    private StaffMapper staffMapper;
    private UserMapper userMapper;

    @Autowired
    public void setStaffMapper(StaffMapper staffMapper) {
        this.staffMapper = staffMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public IPage<User> getAllStaff(Long currentPage, Long pageSize, Integer searchType, String searchInput, Integer searchGender, String searchBirthFrom, String searchBirthTo) {
        Long departmentId = WebUtil.hasAuthority("staff:admin:get") ? null : WebUtil.getLoginUser().getUser().getDepartmentId();
        IPage<User> userIPage;
        if (currentPage == null || pageSize == null) {
            userIPage = PageDTO.of(0, -1);
        } else {
            userIPage = PageDTO.of(currentPage, pageSize);
        }
        searchInput = searchInput.trim();
        return staffMapper.getAllStaff(userIPage, departmentId, searchType, searchInput, searchGender, searchBirthFrom, searchBirthTo);
    }

    @Override
    public boolean modifyStaff(User user) {
        Long departmentId = user.getDepartmentId();
        Staff newStaff = user.getStaff();
        user = userMapper.getOneById(user.getId());
        Staff oldStaff = user.getStaff();
        if (!WebUtil.hasAuthority("staff:admin:modify")) {
            if (!Objects.equals(user.getDepartmentId(), WebUtil.getLoginUser().getUser().getDepartmentId())) {
                throw new DataValidationFailedException();
            }
        }
        if (oldStaff == null) {
            newStaff.setUserId(user.getId());
            staffMapper.insert(newStaff);
        } else {
            newStaff.setId(oldStaff.getId());
            staffMapper.updateById(newStaff);
        }
        user.setDepartmentId(departmentId);
        userMapper.updateById(user);
        return true;
    }
}
