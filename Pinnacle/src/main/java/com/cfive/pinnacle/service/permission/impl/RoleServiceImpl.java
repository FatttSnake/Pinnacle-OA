package com.cfive.pinnacle.service.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.cfive.pinnacle.entity.permission.*;
import com.cfive.pinnacle.exception.DataValidationFailedException;
import com.cfive.pinnacle.mapper.permission.RoleMapper;
import com.cfive.pinnacle.mapper.permission.PowerRoleMapper;
import com.cfive.pinnacle.service.permission.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    private RoleMapper roleMapper;
    private PowerRoleMapper powerRoleMapper;

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Autowired
    public void setPowerRoleMapper(PowerRoleMapper powerRoleMapper) {
        this.powerRoleMapper = powerRoleMapper;
    }

    @Override
    public IPage<Role> getAllRole(Long currentPage, Long pageSize, String searchName, List<Long> searchPower, Integer searchEnable, Integer searchRegex) {
        IPage<Role> roleIPage = PageDTO.of(currentPage, pageSize);
        searchName = searchName.trim();
        List<Long> roleList = roleMapper.filterRoleByPowerId(null, null, searchName, searchEnable, searchRegex);
        if (roleList.size() > 0) {
            for (Long powerId : searchPower) {
                roleList = roleMapper.filterRoleByPowerId(roleList, powerId, null, null, null);
                if (roleList.size() == 0) {
                    break;
                }
            }
        }

        if (roleList.size() > 0) {
            LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<Role>().in(Role::getId, roleList);
            roleIPage = roleMapper.selectPage(roleIPage, wrapper);
            roleIPage.setRecords(roleMapper.getAll(roleIPage.getRecords()));
        }

        return roleIPage;
    }

    @Override
    public Role getRole(long id) {
        return roleMapper.getOneById(id);
    }

    @Override
    @Transactional
    public boolean addRole(Role role) {
        handlePower(role);

        if (roleMapper.insert(role) == 1) {
            role.getPowers().forEach(power -> {
                PowerRole powerRole = new PowerRole();
                powerRole.setRoleId(role.getId());
                powerRole.setPowerId(power.getId());
                if (powerRoleMapper.insert(powerRole) != 1) {
                    throw new RuntimeException("Add power_role failure");
                }
            });
            return true;
        } else {
            throw new RuntimeException("Add role failure");
        }
    }

    @Override
    @Transactional
    public boolean modifyRole(Role role) {
        handlePower(role);
        roleMapper.updateById(role);
        Role originalRole = getRole(role.getId());
        HashSet<Long> newPowerIds = new HashSet<>();
        role.getPowers().forEach(power -> newPowerIds.add(power.getId()));
        HashSet<Long> addPowerIds = new HashSet<>(newPowerIds);
        if (originalRole != null) {
            HashSet<Long> originalPowerIds = new HashSet<>();
            originalRole.getMenus().forEach(menu -> originalPowerIds.add(menu.getPowerId()));
            originalRole.getElements().forEach(element -> originalPowerIds.add(element.getPowerId()));
            originalRole.getOperations().forEach(operation -> originalPowerIds.add(operation.getPowerId()));
            HashSet<Long> deletePowerIds = new HashSet<>(originalPowerIds);
            deletePowerIds.removeAll(newPowerIds);
            addPowerIds.removeAll(originalPowerIds);
            deletePowerIds.forEach(deletePowerId -> {
                LambdaQueryWrapper<PowerRole> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(PowerRole::getRoleId, role.getId())
                        .eq(PowerRole::getPowerId, deletePowerId);
                powerRoleMapper.delete(wrapper);
            });
        }
        addPowerIds.forEach(addPowerId -> {
            PowerRole powerRole = new PowerRole();
            powerRole.setRoleId(role.getId());
            powerRole.setPowerId(addPowerId);
            powerRoleMapper.insert(powerRole);
        });
        return true;
    }

    private void handlePower(Role role) {
        List<Power> powers = role.getPowers();
        Set<Long> elementIds = new HashSet<>();
        Set<Long> menuIds = new HashSet<>();
        powers.forEach(power -> {
            Long powerId = power.getId();
            if (powerId % 100 == 0) {
                throw new DataValidationFailedException("Illegal data");
            }
            elementIds.add(powerId - powerId % 100);
            menuIds.add(powerId - powerId % 10000);
        });
        elementIds.forEach(elementId -> powers.add(new Power(elementId, 2L)));
        menuIds.forEach(menuId -> powers.add(new Power(menuId, 1L)));
    }
}
