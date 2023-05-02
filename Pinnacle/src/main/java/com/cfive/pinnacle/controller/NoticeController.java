package com.cfive.pinnacle.controller;

import com.cfive.pinnacle.entity.Notice;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.INoticeService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    INoticeService noticeService;

    //根据公告id查公告信息及发布人
    @GetMapping("/{nid}")
    public ResponseResult selectByNoticeId(@PathVariable Long nid) {
        Notice noticeById = noticeService.selectByNoticeId(nid);
        Integer code = noticeById != null ? ResponseCode.DATABASE_SELECT_OK : ResponseCode.DATABASE_SELECT_ERROR;
        String msg = noticeById != null ? "" : "数据查询失败，请尝试！";
        return ResponseResult.build(code, msg, noticeById);
    }

    ;

    //添加公告
    @GetMapping
    public ResponseResult selectAllNotice(String title) {
        List<Notice> noticeList;
        if (title == null) {
            noticeList = noticeService.selectAllNotice();
        } else {
            noticeList = noticeService.selectByTitle(title);
        }

        int code = noticeList != null ? ResponseCode.DATABASE_SELECT_OK : ResponseCode.DATABASE_SELECT_ERROR;
        String msg = noticeList != null ? "" : "数据查询失败，请尝试！";
        return ResponseResult.build(code, msg, noticeList);
    }


    @PostMapping
    public ResponseResult updateNotice(@RequestBody Notice notice) {
        notice.setId(null);  //清除id，使新插入的数据id自增
        notice.setOriginId(notice.getId());
        boolean updateById = noticeService.save(notice);
        String msg = updateById ? "" : "数据修改失败，请尝试！";
        return ResponseResult.build(updateById ? ResponseCode.DATABASE_UPDATE_OK : ResponseCode.DATABASE_UPDATE_ERROR, msg, updateById);
    }

    @PutMapping
    public ResponseResult addNotice(@RequestBody Notice notice) {
        boolean insertNotice = noticeService.save(notice);
        String msg = insertNotice ? "" : "数据添加失败，请尝试！";
        return ResponseResult.build(insertNotice ? ResponseCode.DATABASE_SAVE_OK : ResponseCode.DATABASE_SAVE_ERROR, msg, insertNotice);
    }

    @DeleteMapping("/{nid}")
    public ResponseResult deleteByNoticeId(@PathVariable Long nid) {
        boolean removeById = noticeService.deleteById(nid);
        String msg = removeById ? "" : "数据删除失败，请尝试！";
        return ResponseResult.build(removeById ? ResponseCode.DATABASE_DELETE_OK : ResponseCode.DATABASE_DELETE_ERROR, msg, removeById);

    }
}
