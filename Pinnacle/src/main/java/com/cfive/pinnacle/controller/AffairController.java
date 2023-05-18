package com.cfive.pinnacle.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cfive.pinnacle.entity.Affair;
import com.cfive.pinnacle.entity.Attendance;
import com.cfive.pinnacle.entity.User;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.IAffairService;
import com.cfive.pinnacle.service.IUserService;
import com.cfive.pinnacle.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 事务 前端控制器
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@CrossOrigin
@RestController
@RequestMapping("/affair")
public class AffairController {
    @Autowired
    IAffairService affairService;
//    IUserService userService;
//    不用userService的方法了，userController中已经写好了直接拿来用
    @Autowired
    UserController userController;


    @PostMapping("/add")
    public ResponseResult addAffair(@RequestBody Affair affair) {
        return ResponseResult.build(ResponseCode.DATABASE_SAVE_OK, "success", affairService.save(affair));
    }

    @GetMapping("/add/getUser")
    public  ResponseResult getUser() {
        List<User> userList = (List<User>)userController.getAllUser().getData();
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK,"success",userList);
    }//获取数据库中所有用户

    @GetMapping("/add/getCurrentUser")
    public  ResponseResult getCurrentUser() {
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK,"success",WebUtil.getLoginUser().getUser());
    }//获取当前用户




    @GetMapping("/NotApproved")
    public ResponseResult select_NotApproved() {
        LambdaQueryWrapper<Affair> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Affair::getStatus, 0).eq(Affair::getInspectorId,WebUtil.getLoginUser().getUser().getId());
        wrapper.orderByDesc(Affair::getCreateTime);
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", affairService.list(wrapper));
    }


    @GetMapping("/Approved")
    public ResponseResult select_Approved() {
        LambdaQueryWrapper<Affair> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.ne(Affair::getStatus, 0).eq(Affair::getInspectorId,WebUtil.getLoginUser().getUser().getId());
        wrapper2.orderByDesc(Affair::getInspectTime);
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", affairService.list(wrapper2));
    }

    @PutMapping("/yes")
    public ResponseResult updateAffair_yes(@RequestBody Affair affair) {
        System.out.println(affair);
        return ResponseResult.build(ResponseCode.DATABASE_UPDATE_OK, "success", affairService.updateAffair_Yes(affair));
        //审批同意
    }

    @PutMapping("/no")
    public ResponseResult updateAffair_No(@RequestBody Affair affair) {
        return ResponseResult.build(ResponseCode.DATABASE_UPDATE_OK, "success", affairService.updateAffair_No(affair));
        //审批驳回
    }


    @DeleteMapping("/{id}")
    public ResponseResult deleteAffair_Approved(@PathVariable Long id) {
        System.out.println("affair");
        return ResponseResult.build(ResponseCode.DATABASE_DELETE_OK, "success", affairService.removeById(id));
        //删除已审批事务

    }


}
