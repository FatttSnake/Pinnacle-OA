package com.cfive.pinnacle.controller;

import com.cfive.pinnacle.entity.Notice;
import com.cfive.pinnacle.entity.NoticeReceive;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.INoticeReceiveService;
import com.cfive.pinnacle.service.INoticeService;
import com.cfive.pinnacle.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
public class NoticeController {
    @Autowired
    private INoticeService noticeService;
    @Autowired
    private INoticeReceiveService noticeReceiveService;

    //根据公告id查公告信息及发布人
    @GetMapping("/{nid}")
    public ResponseResult selectByNoticeId(@PathVariable Long nid) {
        Notice noticeById = noticeService.selectByNoticeId(nid);
        Integer code = noticeById != null ? ResponseCode.DATABASE_SELECT_OK : ResponseCode.DATABASE_SELECT_ERROR;
        String msg = noticeById != null ? "" : "数据查询失败，请尝试！";
        return ResponseResult.build(code, msg, noticeById);
    }

    //查询所有公告
    @GetMapping
    public ResponseResult selectAllNotice(String title, String type, String startTime, String endTime) {
        List<Notice> noticeList;
        if (!StringUtils.hasText(title) && !StringUtils.hasText(type) && !StringUtils.hasText(startTime) && !StringUtils.hasText(endTime)) {
            noticeList = noticeService.selectAllNotice();
        } else {
            noticeList = noticeService.selectByCond(title, type, startTime, endTime);
        }

        int code = noticeList != null ? ResponseCode.DATABASE_SELECT_OK : ResponseCode.DATABASE_SELECT_ERROR;
        String msg = noticeList != null ? "" : "数据查询失败，请尝试！";
        return ResponseResult.build(code, msg, noticeList);
    }

    //根据登录用户id查询所接收的公告
    @GetMapping("/ByUserId")
    public ResponseResult selectAllByUserId() {
        List<Notice> noticesByUserId = noticeReceiveService.selectAllByUserId();
        Integer code = noticesByUserId != null ? ResponseCode.DATABASE_SELECT_OK : ResponseCode.DATABASE_SELECT_ERROR;
        String msg = noticesByUserId != null ? "" : "数据查询失败，请尝试！";
        return ResponseResult.build(code, msg, noticesByUserId);
    }

    //更新公告
    @PutMapping
    public ResponseResult updateNotice(@RequestBody Notice notice) {
        boolean updateById = noticeService.updateNotice(notice);
        String msg = updateById ? "" : "数据修改失败，请尝试！";
        return ResponseResult.build(updateById ? ResponseCode.DATABASE_UPDATE_OK : ResponseCode.DATABASE_UPDATE_ERROR, msg, updateById);
    }

    //添加公告
    @PostMapping
    public ResponseResult addNotice(@RequestBody Notice notice) {
        Boolean insertNotice = noticeService.addNotice(notice);
        String msg = insertNotice ? "" : "数据添加失败，请尝试！";
        return ResponseResult.build(insertNotice ? ResponseCode.DATABASE_SAVE_OK : ResponseCode.DATABASE_SAVE_ERROR, msg, insertNotice);
    }

    //删除公告
    @DeleteMapping("/{nid}")
    public ResponseResult deleteByNoticeId(@PathVariable Long nid) {
        boolean removeById = noticeService.deleteById(nid);
        String msg = removeById ? "" : "数据删除失败，请尝试！";
        return ResponseResult.build(removeById ? ResponseCode.DATABASE_DELETE_OK : ResponseCode.DATABASE_DELETE_ERROR, msg, removeById);
    }

}
