package com.cfive.pinnacle.controller.permission;

import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.entity.permission.PowerSet;
import com.cfive.pinnacle.service.permission.IPowerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Tag(name = "权限", description = "权限相关接口")
@RestController
@RequestMapping("/power")
public class PowerController {
    private IPowerService powerService;

    @Autowired
    public void setPowerService(IPowerService powerService) {
        this.powerService = powerService;
    }

    @Operation(summary = "获取所有权限")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('system:role:get','system:role:add', 'system:role:modify')")
    public ResponseResult<PowerSet> getAllPower() {
        PowerSet powerSet = powerService.getAllPower();

        return ResponseResult.databaseSelectSuccess(powerSet);
    }
}
