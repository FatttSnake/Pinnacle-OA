package com.cfive.pinnacle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cfive.pinnacle.entity.*;
import com.cfive.pinnacle.mapper.UserGroupMapper;
import com.cfive.pinnacle.mapper.UserMapper;
import com.cfive.pinnacle.mapper.UserRoleMapper;
import com.cfive.pinnacle.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    private UserRoleMapper userRoleMapper;
    private UserGroupMapper userGroupMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
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
    public List<User> getAllUser() {
        return userMapper.getAll();
    }

    @Override
    public User getUser(long id) {
        return userMapper.getOneById(id);
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
