package com.cfive.pinnacle.service.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.cfive.pinnacle.entity.permission.Group;
import com.cfive.pinnacle.entity.permission.RoleGroup;
import com.cfive.pinnacle.mapper.permission.GroupMapper;
import com.cfive.pinnacle.mapper.permission.RoleGroupMapper;
import com.cfive.pinnacle.service.permission.IGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 用户组 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {
    private GroupMapper groupMapper;
    private RoleGroupMapper roleGroupMapper;

    @Autowired
    public void setGroupMapper(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }

    @Autowired
    public void setRoleGroupMapper(RoleGroupMapper roleGroupMapper) {
        this.roleGroupMapper = roleGroupMapper;
    }

    @Override
    public IPage<Group> getAllGroup(Long currentPage, Long pageSize, String searchName, List<Long> searchRole, Integer searchEnable) {
        Page<Group> groupIPage = PageDTO.of(currentPage, pageSize);
        searchName = searchName.trim();
        List<Long> groupList = groupMapper.filterGroupByRoleId(null, null, searchName, searchEnable);
        if (searchRole.size() > 0) {
            for (Long roleId : searchRole) {
                groupList = groupMapper.filterGroupByRoleId(groupList, roleId, null, null);
                if (groupList.size() == 0) {
                    break;
                }
            }
        }

        if (groupList.size() > 0) {
            LambdaQueryWrapper<Group> wrapper = new LambdaQueryWrapper<Group>().in(Group::getId, groupList);
            groupIPage = groupMapper.selectPage(groupIPage, wrapper);
            groupIPage.setRecords(groupMapper.getAll(groupIPage.getRecords()));
        }


        return groupIPage;
    }

    @Override
    public Group getGroup(Long id) {
        return groupMapper.getOneById(id);
    }

    @Override
    @Transactional
    public boolean addGroup(Group group) {
        if (groupMapper.insert(group) == 1) {
            group.getRoles().forEach(role -> {
                RoleGroup roleGroup = new RoleGroup();
                roleGroup.setGroupId(group.getId());
                roleGroup.setRoleId(role.getId());
                if (roleGroupMapper.insert(roleGroup) != 1) {
                    throw new RuntimeException("Add role_group failure");
                }
            });
            return true;
        } else {
            throw new RuntimeException("Add group failure");
        }
    }

    @Override
    @Transactional
    public boolean modifyGroup(Group group) {
        groupMapper.updateById(group);
        Group originalGroup = getGroup(group.getId());
        HashSet<Long> newRoleIds = new HashSet<>();
        group.getRoles().forEach(role -> newRoleIds.add(role.getId()));
        HashSet<Long> addRoleIds = new HashSet<>(newRoleIds);
        if (originalGroup != null) {
            HashSet<Long> originalRoleIds = new HashSet<>();
            originalGroup.getRoles().forEach(role -> originalRoleIds.add(role.getId()));
            HashSet<Long> deleteRoleIds = new HashSet<>(originalRoleIds);
            deleteRoleIds.removeAll(newRoleIds);
            addRoleIds.removeAll(originalRoleIds);
            deleteRoleIds.forEach(deleteRoleId -> {
                LambdaQueryWrapper<RoleGroup> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(RoleGroup::getGroupId, group.getId())
                        .eq(RoleGroup::getRoleId, deleteRoleId);
                roleGroupMapper.delete(wrapper);
            });
        }
        addRoleIds.forEach(addRoleId -> {
            RoleGroup roleGroup = new RoleGroup();
            roleGroup.setGroupId(group.getId());
            roleGroup.setRoleId(addRoleId);
            roleGroupMapper.insert(roleGroup);
        });

        return true;
    }
}
