package com.cfive.pinnacle.controller;

import com.cfive.pinnacle.entity.Notice;
import com.cfive.pinnacle.entity.NoticeType;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.INoticeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/noticeType")
@CrossOrigin
public class NoticeTypeController {
    @Autowired
    INoticeTypeService noticeTypeService;

    @GetMapping
    public ResponseResult selectTypeList(){
        List<NoticeType> selectTypeName = noticeTypeService.selectTypeList();
        Integer code = selectTypeName != null ? ResponseCode.DATABASE_SELECT_OK : ResponseCode.DATABASE_SELECT_ERROR;
        String msg = selectTypeName != null ? "" : "数据查询失败，请尝试！";
        return ResponseResult.build(code, msg, selectTypeName);
    }

}
