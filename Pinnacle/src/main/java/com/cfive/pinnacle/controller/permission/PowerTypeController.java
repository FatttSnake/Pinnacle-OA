package com.cfive.pinnacle.controller.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.entity.permission.*;
import com.cfive.pinnacle.service.permission.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    IPowerTypeService powerTypeService;

    public void setPowerTypeService(IPowerTypeService powerTypeService) {
        this.powerTypeService = powerTypeService;
    }

    @GetMapping
    public ResponseResult getAllPowerType() {
        List<PowerType> powerTypes = powerTypeService.list();

        return ResponseResult.databaseSelectSuccess(powerTypes);
    }

    @GetMapping("/{id}")
    public ResponseResult getPowerType(@PathVariable int id) {
        LambdaQueryWrapper<PowerType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PowerType::getId, id);
        PowerType powerType = powerTypeService.getOne(wrapper);

        return ResponseResult.databaseSelectSuccess(powerType);
    }
}
