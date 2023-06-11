package com.cfive.pinnacle.controller.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.entity.permission.OperationLog;
import com.cfive.pinnacle.service.permission.IOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 操作日志 前端控制器
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@RestController
@RequestMapping("/operationLog")
public class OperationLogController {
    private IOperationLogService operationLogService;

    @Autowired
    public void setOperationLogService(IOperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    @GetMapping
    public ResponseResult<List<OperationLog>> getAllOperationLog() {
        List<OperationLog> operationLogs = operationLogService.list();

        return ResponseResult.databaseSelectSuccess(operationLogs);
    }

    @GetMapping("/{id}")
    public ResponseResult<OperationLog> getOperationLog(@PathVariable int id) {
        LambdaQueryWrapper<OperationLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OperationLog::getId, id);
        OperationLog operationLog = operationLogService.getOne(wrapper);

        return ResponseResult.databaseSelectSuccess(operationLog);
    }
}
