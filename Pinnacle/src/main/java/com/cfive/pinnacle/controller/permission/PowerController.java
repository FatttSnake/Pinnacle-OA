package com.cfive.pinnacle.controller.permission;

import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.entity.permission.PowerSet;
import com.cfive.pinnacle.service.permission.IPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@RestController
@RequestMapping("/power")
public class PowerController {
    private IPowerService powerService;

    @Autowired
    public void setPowerService(IPowerService powerService) {
        this.powerService = powerService;
    }

    @GetMapping
    public ResponseResult getAllPower() {
        PowerSet powerSet = powerService.getAllPower();

        return ResponseResult.databaseSelectSuccess(powerSet);
    }
}
