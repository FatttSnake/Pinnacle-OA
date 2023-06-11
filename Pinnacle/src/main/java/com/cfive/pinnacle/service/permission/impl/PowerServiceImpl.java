package com.cfive.pinnacle.service.permission.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cfive.pinnacle.entity.permission.*;
import com.cfive.pinnacle.mapper.permission.*;
import com.cfive.pinnacle.service.permission.IPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Service
public class PowerServiceImpl extends ServiceImpl<PowerMapper, Power> implements IPowerService {
    private MenuMapper menuMapper;
    private ElementMapper elementMapper;
    private OperationMapper operationMapper;
    private FileMapper fileMapper;

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
    public void setFileMapper(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @Override
    public PowerSet getAllPower() {
        List<Menu> menuList = menuMapper.selectList(Wrappers.emptyWrapper());
        List<Element> elementList = elementMapper.selectList(Wrappers.emptyWrapper());
        List<Operation> operationList = operationMapper.selectList(Wrappers.emptyWrapper());
        List<File> fileList = fileMapper.selectList(Wrappers.emptyWrapper());

        return new PowerSet(menuList, elementList, operationList, fileList);
    }
}
