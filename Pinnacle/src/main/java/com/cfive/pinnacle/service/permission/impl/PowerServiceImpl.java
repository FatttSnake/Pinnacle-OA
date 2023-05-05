package com.cfive.pinnacle.service.permission.impl;

import com.cfive.pinnacle.entity.permission.*;
import com.cfive.pinnacle.mapper.permission.PowerMapper;
import com.cfive.pinnacle.service.permission.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    private IOperationService operationService;
    private IMenuService menuService;
    private IElementService elementService;
    private IFileService fileService;

    @Autowired
    public void setOperationService(IOperationService operationService) {
        this.operationService = operationService;
    }

    @Autowired
    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }

    @Autowired
    public void setElementService(IElementService elementService) {
        this.elementService = elementService;
    }

    @Autowired
    public void setFileService(IFileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public PowerSet getAllPower() {
        List<Operation> operationList = operationService.list();
        List<Menu> menuList = menuService.list();
        List<Element> elementList = elementService.list();
        List<File> fileList = fileService.list();

        return new PowerSet(operationList, menuList, elementList, fileList);
    }
}
