package com.cfive.pinnacle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cfive.pinnacle.entity.Role;
import com.cfive.pinnacle.entity.permission.PowerRole;
import com.cfive.pinnacle.mapper.RoleMapper;
import com.cfive.pinnacle.mapper.permission.PowerRoleMapper;
import com.cfive.pinnacle.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
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
    public List<Role> getAllRole() {
        return roleMapper.getAll();
    }

    @Override
    public Role getRole(long id) {
        return roleMapper.getOneById(id);
    }

    @Override
    @Transactional
    public boolean addRole(Role role) {
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
        roleMapper.updateById(role);
        Role originalRole = getRole(role.getId());
        HashSet<Long> originalPowerIds = new HashSet<>();
        originalRole.getMenus().forEach(menu -> originalPowerIds.add(menu.getPowerId()));
        originalRole.getElements().forEach(element -> originalPowerIds.add(element.getPowerId()));
        originalRole.getOperations().forEach(operation -> originalPowerIds.add(operation.getPowerId()));
        HashSet<Long> newPowerIds = new HashSet<>();
        role.getPowers().forEach(power -> newPowerIds.add(power.getId()));
        HashSet<Long> deletePowerIds = new HashSet<>(originalPowerIds);
        deletePowerIds.removeAll(newPowerIds);
        HashSet<Long> addPowerIds = new HashSet<>(newPowerIds);
        addPowerIds.removeAll(originalPowerIds);
        deletePowerIds.forEach(deletePowerId -> {
            LambdaQueryWrapper<PowerRole> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(PowerRole::getRoleId, role.getId())
                    .eq(PowerRole::getPowerId, deletePowerId);
            powerRoleMapper.delete(wrapper);
        });
        addPowerIds.forEach(addPowerId -> {
            PowerRole powerRole = new PowerRole();
            powerRole.setRoleId(role.getId());
            powerRole.setPowerId(addPowerId);
            powerRoleMapper.insert(powerRole);
        });
        return true;
    }
}
