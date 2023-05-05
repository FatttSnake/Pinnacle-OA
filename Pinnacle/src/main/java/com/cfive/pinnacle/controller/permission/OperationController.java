package com.cfive.pinnacle.controller.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.entity.permission.Operation;
import com.cfive.pinnacle.service.permission.IOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 功能 前端控制器
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@RestController
@RequestMapping("/operation")
public class OperationController {
    private IOperationService operationService;

    @Autowired
    public void setOperationService(IOperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping
    public ResponseResult getAllOperation() {
        List<Operation> operations = operationService.list();

        return ResponseResult.databaseSelectSuccess(operations);
    }

    @GetMapping("/{id}")
    public ResponseResult getOperation(@PathVariable int id) {
        LambdaQueryWrapper<Operation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Operation::getId, id);
        Operation operation = operationService.getOne(wrapper);

        return ResponseResult.databaseSelectSuccess(operation);
    }
}
