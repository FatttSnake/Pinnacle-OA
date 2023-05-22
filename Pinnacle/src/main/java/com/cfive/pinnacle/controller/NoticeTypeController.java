package com.cfive.pinnacle.controller;

import com.cfive.pinnacle.entity.NoticeType;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.INoticeTypeService;
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
public class NoticeTypeController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    INoticeTypeService noticeTypeService;

    //查询已启用的公告类型
    @GetMapping("/enable")
    public ResponseResult selectEnableTypeList(){
        List<NoticeType> selectTypeName = noticeTypeService.selectEnableTypeList();
        Integer code = selectTypeName != null ? ResponseCode.DATABASE_SELECT_OK : ResponseCode.DATABASE_SELECT_ERROR;
        String msg = selectTypeName != null ? "" : "数据查询失败，请重试！";
        return ResponseResult.build(code, msg, selectTypeName);
    }

    //查询所有公告类型
    @GetMapping
    public ResponseResult selectTypeList(){
        List<NoticeType> selectTypeList = noticeTypeService.selectTypeList();
        Integer code = selectTypeList != null ? ResponseCode.DATABASE_SELECT_OK : ResponseCode.DATABASE_SELECT_ERROR;
        String msg = selectTypeList != null ? "" : "数据查询失败，请重试！";
        return ResponseResult.build(code, msg, selectTypeList);
    }

    //修改公告类型启用或禁用
    @GetMapping("/update")
    public ResponseResult updateTypeEnableById(String typeId,Boolean enable){
        Long tid=null;
        Integer isEnable = null;
        if (StringUtils.hasText(typeId)&&null!=enable){
            tid = Long.parseLong(typeId);
            isEnable = (enable == true ? 1 : 0);
        }
        Boolean updateEnableById = noticeTypeService.updateTypeEnableById(tid, isEnable);
        String msg = updateEnableById ? "" : "修改失败，请重试！";
        return ResponseResult.build(updateEnableById ? ResponseCode.DATABASE_UPDATE_OK : ResponseCode.DATABASE_UPDATE_ERROR, msg, updateEnableById);
    }

    //添加公告类型
    @PostMapping
    public ResponseResult addNoticeType(NoticeType noticeType){
        Boolean insertNotice = noticeTypeService.addNoticeType(noticeType);
        String msg = insertNotice ? "" : "数据添加失败，请重试！";
        return ResponseResult.build(insertNotice ? ResponseCode.DATABASE_SAVE_OK : ResponseCode.DATABASE_SAVE_ERROR, msg, insertNotice);
    }
}
