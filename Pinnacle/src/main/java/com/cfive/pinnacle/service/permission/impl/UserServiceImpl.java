package com.cfive.pinnacle.service.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cfive.pinnacle.entity.permission.*;
import com.cfive.pinnacle.exception.OldPasswordNotMatchException;
import com.cfive.pinnacle.mapper.permission.*;
import com.cfive.pinnacle.service.permission.IUserService;
import com.cfive.pinnacle.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private UserMapper userMapper;
    private MenuMapper menuMapper;
    private ElementMapper elementMapper;
    private OperationMapper operationMapper;
    private UserRoleMapper userRoleMapper;
    private UserGroupMapper userGroupMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setMenuMapper(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Autowired
    public void setElementMapper(ElementMapper elementMapper) {
        this.elementMapper = elementMapper;
    }

    @Autowired
    public void setOperationMapper(OperationMapper operationMapper) {
        this.operationMapper = operationMapper;
    }

    @Autowired
    public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Autowired
    public void setUserGroupMapper(UserGroupMapper userGroupMapper) {
        this.userGroupMapper = userGroupMapper;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getInfo() {
        return getUserWithPower(WebUtil.getLoginUser().getUser().getUsername());
    }

    @Override
    @Transactional
    public boolean modifyPasswd(String oldPasswd, String newPasswd) {
        if (!passwordEncoder.matches(oldPasswd, userMapper.getOneWithPowerByUsername(WebUtil.getLoginUser().getUsername()).getPasswd())) {
            throw new OldPasswordNotMatchException();
        }

        String encryptedPassword = passwordEncoder.encode(newPasswd);
        User user = new User().setId(WebUtil.getLoginUser().getUser().getId()).setPasswd(encryptedPassword);
        return userMapper.updateById(user) == 1;
    }

    @Override
    public List<User> getAffairUser() {
        return userMapper.getAllAffairUser();
    }

    @Override
    public List<User> getDepartmentUser() {
        Long departmentId = WebUtil.getLoginUser().getUser().getDepartmentId();
        if (departmentId == null) {
            return List.of();
        }
        return userMapper.getAllDepartmentUser(departmentId);
    }

    @Override
    public List<User> getNoticeUser() {
        return userMapper.getAllNoticeUser();
    }


    @Override
    public IPage<User> getAllUser(Long currentPage, Long pageSize, String searchName, List<Long> searchRole, List<Long> searchGroup, Integer searchEnable, Integer searchRegex) {
        IPage<User> userIPage = PageDTO.of(currentPage, pageSize);
        searchName = searchName.trim();
        List<Long> userList = userMapper.filterUserByRoleIdAndGroupId(null, null, null, searchName, searchEnable, searchRegex);
        if (userList.size() > 0) {
            for (Long roleId : searchRole) {
                userList = userMapper.filterUserByRoleIdAndGroupId(userList, roleId, null, null, null, null);
                if (userList.size() == 0) {
                    break;
                }
            }
        }
        if (userList.size() > 0) {
            for (Long groupId : searchGroup) {
                userList = userMapper.filterUserByRoleIdAndGroupId(userList, null, groupId, null, null, null);
                if (userList.size() == 0) {
                    break;
                }
            }
        }

        if (userList.size() > 0) {
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>().in(User::getId, userList);
            userIPage = userMapper.selectPage(userIPage, wrapper);
            userIPage.setRecords(userMapper.getAllWithRoleAndGroup(userIPage.getRecords()));
            userIPage.getRecords().forEach(user -> {
                if (user.getId() == 1L) {
                    user.setRoles(List.of(new Role(0L, "超级管理员")));
                    user.setGroups(List.of(new Group(0L, "超级管理员")));
                }
            });
        }

        return userIPage;
    }

    @Override
    public User getUser(long id) {
        User user = userMapper.getOneById(id);
        if (user.getId() == 1L) {
            user.setRoles(List.of(new Role(0L, "超级管理员")));
            user.setGroups(List.of(new Group(0L, "超级管理员")));
        }
        return user;
    }

    @Override
    public User getUserWithPower(String username) {
        User user = userMapper.getOneWithPowerByUsername(username);
        if (user == null) {
            return null;
        }
        if (user.getId() == 1L) {
            List<Menu> menus = menuMapper.selectList(Wrappers.emptyWrapper());
            List<Element> elements = elementMapper.selectList(Wrappers.emptyWrapper());
            List<Operation> operations = operationMapper.selectList(Wrappers.emptyWrapper());
            user.setMenus(menus);
            user.setElements(elements);
            user.setOperations(operations);
        }
        return user;
    }

    @Override
    @Transactional
    public boolean addUser(User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPasswd());
        user.setPasswd(encryptedPassword);
        if (userMapper.insert(user) == 1) {
            user.getRoles().forEach(role -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(role.getId());
                if (userRoleMapper.insert(userRole) != 1) {
                    throw new RuntimeException("Add user_role failure");
                }
            });
            user.getGroups().forEach(group -> {
                UserGroup userGroup = new UserGroup();
                userGroup.setUserId(user.getId());
                userGroup.setGroupId(group.getId());
                if (userGroupMapper.insert(userGroup) != 1) {
                    throw new RuntimeException("Add user_group failure");
                }
            });
            return true;
        } else {
            throw new RuntimeException("Add group failure");
        }
    }

    @Override
    @Transactional
    public boolean modifyUser(User user) {
        if (StringUtils.hasText(user.getPasswd())) {
            String encryptedPassword = passwordEncoder.encode(user.getPasswd());
            user.setPasswd(encryptedPassword);
        }

        // Protect administrator
        if (user.getId() == 1L) {
            user.setDepartmentId(null);
            user.setEnable(1);
            user.setDeleted(0L);
            userMapper.updateById(user);
            return true;
        }

        userMapper.updateById(user);
        User originalUser = getUser(user.getId());
        HashSet<Long> newRoleIds = new HashSet<>();
        HashSet<Long> newGroupIds = new HashSet<>();
        user.getRoles().forEach(role -> newRoleIds.add(role.getId()));
        user.getGroups().forEach(group -> newGroupIds.add(group.getId()));
        HashSet<Long> addRoleIds = new HashSet<>(newRoleIds);
        HashSet<Long> addGroupIds = new HashSet<>(newGroupIds);
        if (originalUser != null) {
            HashSet<Long> originalRoleIds = new HashSet<>();
            HashSet<Long> originalGroupIds = new HashSet<>();
            originalUser.getRoles().forEach(role -> originalRoleIds.add(role.getId()));
            originalUser.getGroups().forEach(group -> originalGroupIds.add(group.getId()));
            HashSet<Long> deleteRoleIds = new HashSet<>(originalRoleIds);
            HashSet<Long> deleteGroupIds = new HashSet<>(originalGroupIds);
            deleteRoleIds.removeAll(newRoleIds);
            deleteGroupIds.removeAll(newGroupIds);
            addRoleIds.removeAll(originalRoleIds);
            addGroupIds.removeAll(originalGroupIds);
            deleteRoleIds.forEach(deleteRoleId -> {
                LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(UserRole::getUserId, user.getId())
                        .eq(UserRole::getRoleId, deleteRoleId);
                userRoleMapper.delete(wrapper);
            });
            deleteGroupIds.forEach(deleteGroupId -> {
                LambdaQueryWrapper<UserGroup> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(UserGroup::getUserId, user.getId())
                        .eq(UserGroup::getGroupId, deleteGroupId);
                userGroupMapper.delete(wrapper);
            });
        }
        addRoleIds.forEach(addRoleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(addRoleId);
            userRoleMapper.insert(userRole);
        });
        addGroupIds.forEach(addGroupId -> {
            UserGroup userGroup = new UserGroup();
            userGroup.setUserId(user.getId());
            userGroup.setGroupId(addGroupId);
            userGroupMapper.insert(userGroup);
        });

        return true;
    }
}
