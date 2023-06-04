package com.cfive.pinnacle.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.cfive.pinnacle.entity.Notice;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.INoticeReceiveService;
import com.cfive.pinnacle.service.INoticeService;
import com.cfive.pinnacle.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 公告 前端控制器
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */

@RestController
@RequestMapping("/notice")
@CrossOrigin
@Slf4j
public class NoticeController {
    @Autowired
    private INoticeService noticeService;
    @Autowired
    private INoticeReceiveService noticeReceiveService;

    //分页查询所有公告或分页模糊查询
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('notice:manage:get')")
    public ResponseResult<List<Notice>> selectPageNotice(Integer currentPage, Integer pageSize, String title, String type, String startTime, String endTime,String userIdList) {
        List<Long> userIds = WebUtil.convertStringToList(userIdList, Long.class);
        Page<Notice> noticePage;
        if (null != currentPage && null != pageSize) {
            noticePage = PageDTO.of(currentPage, pageSize);
        } else {
            // 不进行分页
            noticePage = PageDTO.of(1, -1);
        }
        IPage<Notice> noticeIPage = noticeService.selectPageNotice(noticePage, title.trim(), type.trim(), startTime.trim(), endTime.trim(),userIds);
        int code = noticeIPage.getRecords() != null ? ResponseCode.DATABASE_SELECT_OK : ResponseCode.DATABASE_SELECT_ERROR;
        String msg = noticeIPage.getRecords() != null ? String.valueOf(noticeIPage.getTotal()) : "数据查询失败，请重试！";
        return ResponseResult.build(code, msg, noticeIPage.getRecords());
    }

    //根据公告id查公告信息及发布人
    @GetMapping("/{nid}")
    @PreAuthorize("hasAuthority('notice:manage:get')")
    public ResponseResult<Notice> selectByNoticeId(@PathVariable Long nid) {
        Notice noticeById = noticeService.selectByNoticeId(nid);
        int code = noticeById != null ? ResponseCode.DATABASE_SELECT_OK : ResponseCode.DATABASE_SELECT_ERROR;
        String msg = noticeById != null ? "" : "数据查询失败，请重试！";
        return ResponseResult.build(code, msg, noticeById);
    }

    //根据登录用户id查询所接收的公告
    @GetMapping("/self")
    @PreAuthorize("hasAuthority('notice:self:get')")
    public ResponseResult<List<Notice>> selectByUserId(Integer readStatus,String title, String type, String startTime, String endTime) {
        List<Notice> noticesByUserId = noticeReceiveService.selectByUserId(readStatus, title.trim(), type.trim(), startTime.trim(), endTime.trim());
        Integer code = noticesByUserId != null ? ResponseCode.DATABASE_SELECT_OK : ResponseCode.DATABASE_SELECT_ERROR;
        String msg = noticesByUserId != null ? "" : "数据查询失败，请重试！";
        return ResponseResult.build(code, msg, noticesByUserId);
    }

    //修改登录用户所接收公告的阅读状态
    @PutMapping("/modify_notice_read")
    @PreAuthorize("hasAuthority('notice:self:get')")
    public ResponseResult<?> modifyNoticeIsRead(@RequestBody Notice notice) {
        Boolean updateById = false;
        if (null != notice) {
            updateById = noticeReceiveService.modifyNoticeIsRead(notice);
        }
        String msg = updateById ? "" : "服务器出错，请重试！";
        return ResponseResult.build(updateById ? ResponseCode.DATABASE_UPDATE_OK : ResponseCode.DATABASE_UPDATE_ERROR, msg, null);

    }
    //修改公告置顶状态
    @PutMapping("/update_notice_top")
    @PreAuthorize("hasAuthority('notice:self:get')")
    public ResponseResult<?> updateNoticeTop(@RequestBody Notice notice) {
        String operationMessage = notice.getTop() == 1 ? "取消置顶" : "置顶";
        Boolean updateResult = noticeService.updateNoticeTop(notice);
        String msg = updateResult ? "已成功" + operationMessage : operationMessage + "失败，请重试！";
        return ResponseResult.build(updateResult ? ResponseCode.DATABASE_UPDATE_OK : ResponseCode.DATABASE_UPDATE_ERROR, msg, null);
    }

    //更新公告
    @PutMapping
    @PreAuthorize("hasAuthority('notice:manage:modify')")
    public ResponseResult<?> updateNotice(@RequestBody Notice notice) {
        Boolean updateById = noticeService.updateNotice(notice);
        String msg = updateById ? "" : "数据修改失败，请重试！";
        return ResponseResult.build(updateById ? ResponseCode.DATABASE_UPDATE_OK : ResponseCode.DATABASE_UPDATE_ERROR, msg, null);
    }

    //添加公告
    @PostMapping
    @PreAuthorize("hasAuthority('notice:manage:add')")
    public ResponseResult<?> addNotice(@RequestBody Notice notice) {
        Boolean insertNotice = noticeService.addNotice(notice);
        String msg = insertNotice ? "" : "数据添加失败，请重试！";
        return ResponseResult.build(insertNotice ? ResponseCode.DATABASE_SAVE_OK : ResponseCode.DATABASE_SAVE_ERROR, msg, null);
    }

    //删除公告
    @DeleteMapping("/{nid}")
    @PreAuthorize("hasAuthority('notice:manage:delete')")
    public ResponseResult<?> deleteByNoticeId(@PathVariable Long nid) {
        Boolean removeById = noticeService.deleteById(nid);
        String msg = removeById ? "" : "数据删除失败，请重试！";
        return ResponseResult.build(removeById ? ResponseCode.DATABASE_DELETE_OK : ResponseCode.DATABASE_DELETE_ERROR, msg, null);
    }

    //批量删除公告
    @PostMapping("/batch")
    @PreAuthorize("hasAuthority('notice:manage:delete')")
    public ResponseResult<?> deleteBatchByIds(@RequestBody List<String> noticeIds) {
        //	List<String>转List<Long>
        List<Long> nIds = noticeIds.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        Boolean deleteBatchByIds = noticeService.deleteBatchByIds(nIds);
        String msg = deleteBatchByIds ? "" : "数据删除失败，请重试！";
        return ResponseResult.build(deleteBatchByIds ? ResponseCode.DATABASE_DELETE_OK : ResponseCode.DATABASE_DELETE_ERROR, msg, null);
    }

    //首页展示最近接收的公告
    @GetMapping("/limit")
    public ResponseResult<List<Notice>> selectLimitByUserId() {
        List<Notice> selectLimitByUserId = noticeReceiveService.selectLimitByUserId();
        String msg = (null != selectLimitByUserId) ? "" : "数据查询失败，请重试！";
        return ResponseResult.build((null != selectLimitByUserId) ? ResponseCode.DATABASE_SELECT_OK : ResponseCode.DATABASE_SELECT_ERROR, msg, selectLimitByUserId);
    }

}
