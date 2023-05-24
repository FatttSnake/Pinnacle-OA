package com.cfive.pinnacle.controller;

import com.cfive.pinnacle.entity.NoticeType;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.INoticeTypeService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 公告类型 前端控制器
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@RestController
@RequestMapping("/notice_type")
@CrossOrigin
@Slf4j
public class NoticeTypeController {

    @Autowired
    INoticeTypeService noticeTypeService;

    //查询已启用的公告类型
    @GetMapping("/enable")
    public ResponseResult<List<NoticeType>> selectEnableTypeList(){
        List<NoticeType> selectTypeName = noticeTypeService.selectEnableTypeList();
        Integer code = selectTypeName != null ? ResponseCode.DATABASE_SELECT_OK : ResponseCode.DATABASE_SELECT_ERROR;
        String msg = selectTypeName != null ? "" : "数据查询失败，请重试！";
        return ResponseResult.build(code, msg, selectTypeName);
    }

    //查询所有公告类型
    @GetMapping
    public ResponseResult<List<NoticeType>> selectTypeList(){
        List<NoticeType> selectTypeList = noticeTypeService.selectTypeList();
        Integer code = selectTypeList != null ? ResponseCode.DATABASE_SELECT_OK : ResponseCode.DATABASE_SELECT_ERROR;
        String msg = selectTypeList != null ? "" : "数据查询失败，请重试！";
        return ResponseResult.build(code, msg, selectTypeList);
    }

    //修改公告类型启用或禁用
    @GetMapping("/update")
    public ResponseResult<?> updateTypeEnableById(String typeId,Integer enable){
        Long tid=null;
        if (StringUtils.hasText(typeId)){
            tid = Long.parseLong(typeId);
        }
        Boolean updateEnableById = noticeTypeService.updateTypeEnableById(tid, enable);
        String msg = updateEnableById ? "" : "修改失败，请重试！";
        return ResponseResult.build(updateEnableById ? ResponseCode.DATABASE_UPDATE_OK : ResponseCode.DATABASE_UPDATE_ERROR, msg, null);
    }

    //添加公告类型
    @PostMapping
    public ResponseResult addNoticeType(@RequestBody NoticeType noticeType){
        Boolean insertNotice = noticeTypeService.addNoticeType(noticeType);
        String msg = insertNotice ? "" : "数据添加失败，请重试！";
        return ResponseResult.build(insertNotice ? ResponseCode.DATABASE_SAVE_OK : ResponseCode.DATABASE_SAVE_ERROR, msg, insertNotice);
    }

    //修改公告类型
    @PutMapping
    public ResponseResult<?> updateNoticeType(@RequestBody NoticeType noticeType){
        boolean updateById =noticeTypeService.updateNoticeType(noticeType);
        String msg = updateById ? "" : "数据修改失败，请重试！";
        return ResponseResult.build(updateById ? ResponseCode.DATABASE_UPDATE_OK : ResponseCode.DATABASE_UPDATE_ERROR, msg, null);
    }

    //删除公告类型
    @DeleteMapping("/{typeId}")
    public ResponseResult<?> deleteNoticeTypeById(@PathVariable Long typeId) {
        boolean removeById = noticeTypeService.deleteNoticeTypeById(typeId);
        String msg = removeById ? "" : "数据删除失败，请重试！";
        return ResponseResult.build(removeById ? ResponseCode.DATABASE_DELETE_OK : ResponseCode.DATABASE_DELETE_ERROR, msg, null);
    }
}
