package com.cfive.pinnacle.controller;

import com.cfive.pinnacle.entity.UserWork;
import com.cfive.pinnacle.entity.Work;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.IWorkService;
import com.cfive.pinnacle.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasAuthority('work:manage:get')")
    public ResponseResult<List<Work>> getAll(String content) {
        System.out.println(content);
        if (content != null && content.length() != 0) {
            System.out.println("test1");
            List<Work> workList = workService.getWorkByContent(content);
            return ResponseResult.databaseSelectSuccess(workList);
        } else {
            System.out.println("test2");
            return ResponseResult.databaseSelectSuccess(workService.getAll());
        }
    }

    @GetMapping("/todo")
    @PreAuthorize("hasAuthority('work:self:get')")
    public ResponseResult<List<Work>> getTodo() {
        Long userId = WebUtil.getLoginUser().getUser().getId();
        return ResponseResult.databaseSelectSuccess(workService.getTodo(userId));
    }

    @GetMapping("/card")
    public ResponseResult<List<Work>> getCard() {
        if (WebUtil.hasAuthority("work:self:home")) {
            Long userId = WebUtil.getLoginUser().getUser().getId();
            return ResponseResult.databaseSelectSuccess(workService.getCard(userId));
        }
        return ResponseResult.databaseSelectSuccess(List.of());
    }

    @GetMapping("/complete")
    @PreAuthorize("hasAuthority('work:self:get')")
    public ResponseResult<List<Work>> getComplete() {
        Long userId = WebUtil.getLoginUser().getUser().getId();
        return ResponseResult.databaseSelectSuccess(workService.getComplete(userId));
    }

    @GetMapping("/{workId}")
    @PreAuthorize("hasAuthority('work:self:detail') and hasAuthority('work:self:get')")
    public ResponseResult<Work> getOne(@PathVariable Long workId) {
        return ResponseResult.databaseSelectSuccess(workService.getOne(workId));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('work:manage:add')")
    public ResponseResult<Work> addWork(@RequestBody Work work) {
        work.setPublisherId(WebUtil.getLoginUser().getUser().getId());
        if (workService.addWork(work)) {
            return ResponseResult.databaseSaveSuccess(work);
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "Add failed", null);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('work:manage:delete')")
    public ResponseResult<?> deleteById(@PathVariable Long id) {
        if (workService.deleteByWorkId(id)) {
            return ResponseResult.databaseDeleteSuccess();
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_DELETE_ERROR, "Delete failed", null);
        }
    }

    @PutMapping("/set_status")
    @PreAuthorize("hasAuthority('work:self:status') and hasAuthority('work:self:get')")
    public ResponseResult<UserWork> updateStatus(@RequestBody UserWork userWork) {
        userWork.setUserId(WebUtil.getLoginUser().getUser().getId());
        if (workService.updateStatus(userWork)) {
            return ResponseResult.databaseUpdateSuccess(userWork);
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_UPDATE_ERROR, "Update failed", null);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('work:manage:modify')")
    public ResponseResult<Work> updateWork(@RequestBody Work work) {
        work.setPublisherId(WebUtil.getLoginUser().getUser().getId());
        if (workService.updateWork(work)) {
            return ResponseResult.databaseUpdateSuccess(work);
        } else {
            return ResponseResult.build(ResponseCode.DATABASE_UPDATE_ERROR, "Update failed", null);
        }
    }
}
