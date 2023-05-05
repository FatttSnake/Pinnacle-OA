package com.cfive.pinnacle.controller;

import com.cfive.pinnacle.entity.Department;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 部门 前端控制器
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@RestController
@RequestMapping("/department")
@CrossOrigin
public class DepartmentController {
    @Autowired
    IDepartmentService departmentService;

    @GetMapping
    public ResponseResult getDepartAndUser(){
        List<Department> getDepartAndUser = departmentService.getDepartAndUser();
        Integer code = getDepartAndUser != null ? ResponseCode.DATABASE_SELECT_OK : ResponseCode.DATABASE_SELECT_ERROR;
        String msg = getDepartAndUser != null ? "" : "数据查询失败，请尝试！";
        return ResponseResult.build(code, msg, getDepartAndUser);
    }

}
