package com.cfive.pinnacle.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.cfive.pinnacle.entity.User;
import com.cfive.pinnacle.entity.UserWork;
import com.cfive.pinnacle.entity.Work;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.IWorkService;
import com.cfive.pinnacle.service.impl.UserWorkServiceImpl;
import com.cfive.pinnacle.service.impl.WorkServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 工作事项 前端控制器
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */

@RestController
@CrossOrigin
@RequestMapping("/work")
public class WorkController {
    @Autowired
    private WorkServiceImpl workService;
    @Autowired
    private UserWorkServiceImpl userWorkService;

    @GetMapping
    public ResponseResult getAll() {
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", workService.getAll());
    }

    @GetMapping("/todo/{userId}")
    public ResponseResult getTodo(@PathVariable Long userId) {
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", workService.getTodo(userId));
    }

    @GetMapping("/complete/{userId}")
    public ResponseResult getComplete(@PathVariable Long userId) {
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", workService.getComplete(userId));
    }

    @PostMapping
    public ResponseResult addWork(@RequestBody Work work) {
        System.out.println(work);
        return ResponseResult.build(ResponseCode.DATABASE_SAVE_OK, "success", workService.addWork(work));
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteById(@PathVariable long id) {
        System.out.println(id);
        return ResponseResult.build(ResponseCode.DATABASE_DELETE_OK, "success", workService.deleteByWorkId(id));
    }

    @PutMapping("/setComplete")
    public ResponseResult updateWork(@RequestBody UserWork userWork) {
        return ResponseResult.build(ResponseCode.DATABASE_DELETE_OK, "success", userWorkService.updateById(userWork));
    }
}
