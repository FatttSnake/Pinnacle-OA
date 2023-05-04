package com.cfive.pinnacle.controller.permission;

import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.entity.permission.*;
import com.cfive.pinnacle.service.permission.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 权限类型 前端控制器
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@RestController
@RequestMapping("/powerType")
public class PowerTypeController {
    IPowerService powerTypeService;

    @Autowired
    public void setPowerTypeService(IPowerService powerTypeService) {
        this.powerTypeService = powerTypeService;
    }

    @GetMapping
    public ResponseResult getAllPowerType() {
        List<Power> powerTypes = powerTypeService.list();
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", powerTypes);
    }
}
