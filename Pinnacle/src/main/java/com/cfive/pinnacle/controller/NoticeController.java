package com.cfive.pinnacle.controller;

import com.cfive.pinnacle.entity.Notice;
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
    public Result selectByNoticeId(@PathVariable Long nid) {
        Notice noticeById = noticeService.selectByNoticeId(nid);
        Integer code = noticeById != null ? Code.SELECT_NOTICE_OK : Code.SELECT_NOTICE_ERR;
        String msg = noticeById != null ? "" : "数据查询失败，请尝试！";
        return new Result(code, noticeById, msg);
    }

    ;

    //添加公告
    @GetMapping
    public Result selectAllNoticeId() {
        List<Notice> noticeList = noticeService.selectAllNoticeId();
        Integer code = noticeList != null ? Code.SELECT_ALL_OK : Code.SELECT_ALL_ERR;
        String msg = noticeList != null ? "" : "数据查询失败，请尝试！";
        return new Result(code, noticeList, msg);
    }

    @PostMapping
    public Result updateNotice(@RequestBody Notice notice) {
        notice.setId(null);  //清除id，使新插入的数据id自增
        notice.setModifyTime(LocalDateTime.now());
        notice.setOriginId(notice.getId());
        boolean updateById = noticeService.save(notice);
        Result result = new Result(updateById ? Code.UPDATE_OK : Code.UPDATE_ERR, updateById);
        return result;
    }

    @PutMapping
    public Result addNotice(@RequestBody Notice notice){
        boolean insertNotice = noticeService.save(notice);
        Result result = new Result(insertNotice ? Code.SAVE_OK : Code.SAVE_ERR, insertNotice);
        return result;
    }

    @DeleteMapping("/{nid}")
    public Result deleteByNoticeId(@PathVariable Long nid){
        boolean  removeById= noticeService.removeById(nid);
        Result result = new Result(removeById ? Code.DELETE_OK : Code.DELETE_ERR, removeById);
        return result;
    }
}
