package com.cfive.pinnacle.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.cfive.pinnacle.entity.User;
import com.cfive.pinnacle.entity.UserWork;
import com.cfive.pinnacle.entity.Work;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.IUserWorkService;
import com.cfive.pinnacle.service.IWorkService;
import com.cfive.pinnacle.service.impl.UserWorkServiceImpl;
import com.cfive.pinnacle.service.impl.WorkServiceImpl;
import com.cfive.pinnacle.utils.WebUtil;
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
    private IWorkService workService;
    @GetMapping
    public ResponseResult getAll(String content) {
        if (content != null) {
            List<Work> workList = workService.getWorkByContent(content);
            return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success",workList);
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", workService.getAll());
        }
    }

    @GetMapping("/todo")
    public ResponseResult getTodo() {
        Long userId = WebUtil.getLoginUser().getUser().getId();
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", workService.getTodo(userId));
    }

    @GetMapping("/card")
    public ResponseResult getCard() {
//        Long userId = WebUtil.getLoginUser().getUser().getId();
        long userId = 1;
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", workService.getCard(userId));
    }

    @GetMapping("/complete")
    public ResponseResult getComplete() {
        Long userId = WebUtil.getLoginUser().getUser().getId();
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", workService.getComplete(userId));
    }

    @GetMapping("/{workId}")
    public ResponseResult getOne(@PathVariable Long workId) {
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success",workService.getOne(workId));
    }

    @PostMapping
    public ResponseResult addWork(@RequestBody Work work) {
        work.setPublisherId(WebUtil.getLoginUser().getUser().getId());
        return ResponseResult.build(ResponseCode.DATABASE_SAVE_OK, "success", workService.addWork(work));
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteById(@PathVariable Long id) {
        return ResponseResult.build(ResponseCode.DATABASE_DELETE_OK, "success", workService.deleteByWorkId(id));
    }

    @PutMapping("/setStatus")
    public ResponseResult updateStatus(@RequestBody UserWork userWork) {
        userWork.setUserId(WebUtil.getLoginUser().getUser().getId());
        return ResponseResult.build(ResponseCode.DATABASE_UPDATE_OK, "success", workService.updateStatus(userWork));
    }

    @PutMapping
    public ResponseResult updateWork(@RequestBody Work work) {
        work.setPublisherId(WebUtil.getLoginUser().getUser().getId());
        return ResponseResult.build(ResponseCode.DATABASE_UPDATE_OK, "success", workService.updateWork(work));
    }
}
