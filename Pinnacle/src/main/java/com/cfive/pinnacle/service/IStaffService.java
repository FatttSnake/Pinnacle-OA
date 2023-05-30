package com.cfive.pinnacle.service;

import com.cfive.pinnacle.entity.Staff;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cfive.pinnacle.entity.permission.User;

import java.util.List;

/**
 * <p>
 * 员工 服务类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
public interface IStaffService extends IService<Staff> {
    List<User> getAllStaff(Long departmentId);

    boolean modifyStaff(User user);
}
