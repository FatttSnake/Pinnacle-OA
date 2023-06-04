package com.cfive.pinnacle.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cfive.pinnacle.entity.Staff;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cfive.pinnacle.entity.permission.User;

/**
 * <p>
 * 员工 服务类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
public interface IStaffService extends IService<Staff> {
    IPage<User> getAllStaff(Long currentPage, Long pageSize, Integer searchType, String searchInput, Integer searchGender, String searchBirthFrom, String searchBirthTo, Integer searchRegex);

    boolean modifyStaff(User user);

    boolean modifySelf(Staff staff);
}
