package com.cfive.pinnacle.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cfive.pinnacle.controller.permission.UserController;
import com.cfive.pinnacle.entity.Affair;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.IAffairService;
import com.cfive.pinnacle.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('affair:self:add')")
    public ResponseResult<Boolean> addAffair(@RequestBody Affair affair) {
        return ResponseResult.build(ResponseCode.DATABASE_SAVE_OK, "success", affairService.save(affair));
    }

    @GetMapping("/personal_affairs")
    @PreAuthorize("hasAuthority('affair:self:get')")
    public ResponseResult<List<Affair>> getPersonalAffairs() {
        LambdaQueryWrapper<Affair> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Affair::getApplicantId, WebUtil.getLoginUser().getUser().getId());
        wrapper.orderByDesc(Affair::getCreateTime);
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", affairService.list(wrapper));
    }


    @GetMapping("/not_approved")
    @PreAuthorize("hasAuthority('affair:manage:get')")
    public ResponseResult<List<Affair>> selectNotApproved() {
        LambdaQueryWrapper<Affair> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Affair::getStatus, 0).eq(Affair::getInspectorId, WebUtil.getLoginUser().getUser().getId());
        wrapper.orderByDesc(Affair::getCreateTime);
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", affairService.list(wrapper));
    }


    @GetMapping("/approved")
    @PreAuthorize("hasAuthority('affair:manage:get')")
    public ResponseResult<List<Affair>> selectApproved() {
        LambdaQueryWrapper<Affair> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.ne(Affair::getStatus, 0).eq(Affair::getInspectorId, WebUtil.getLoginUser().getUser().getId());
        wrapper2.orderByDesc(Affair::getInspectTime);
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", affairService.list(wrapper2));
    }

    @PutMapping("/yes")
    @PreAuthorize("hasAuthority('affair:manage:modify')")
    public ResponseResult updateAffairYes(@RequestBody Affair affair) {
        System.out.println(affair);
        return ResponseResult.build(ResponseCode.DATABASE_UPDATE_OK, "success", affairService.updateAffairYes(affair));
        //审批同意
    }

    @PutMapping("/no")
    @PreAuthorize("hasAuthority('affair:manage:modify')")
    public ResponseResult updateAffairNo(@RequestBody Affair affair) {
        return ResponseResult.build(ResponseCode.DATABASE_UPDATE_OK, "success", affairService.updateAffairNo(affair));
        //审批驳回
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('affair:manage:delete')")
    public ResponseResult deleteAffairApproved(@PathVariable Long id) {
        System.out.println("affair");
        return ResponseResult.build(ResponseCode.DATABASE_DELETE_OK, "success", affairService.removeById(id));
        //删除已审批事务

    }


}
