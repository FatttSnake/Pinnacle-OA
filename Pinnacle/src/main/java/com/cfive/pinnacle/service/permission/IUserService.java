package com.cfive.pinnacle.service.permission;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cfive.pinnacle.entity.permission.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
public interface IUserService extends IService<User> {

    User getInfo();

    List<User> getAffairUser();

    List<User> getDepartmentUser();

    IPage<User> getAllUser(Long currentPage, Long pageSize);

    User getUser(long id);

    User getUserWithPower(String username);

    boolean addUser(User user);

    boolean modifyUser(User user);
}
