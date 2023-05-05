package com.cfive.pinnacle.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cfive.pinnacle.entity.Affair;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.service.IAffairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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



    @PostMapping("/add")
    public ResponseResult addAffair(@RequestBody Affair affair){
        return ResponseResult.build(ResponseCode.DATABASE_SAVE_OK,"success",affairService.save(affair));
    }

    @GetMapping("/NotApproved")
    public ResponseResult select_NotApproved(){
        LambdaQueryWrapper<Affair> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Affair::getStatus, 0);
        return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK, "success", affairService.list(wrapper));
    }



    @GetMapping("/Approved")
    public ResponseResult select_Approved(){
       LambdaQueryWrapper<Affair> wrapper2 =new LambdaQueryWrapper<>();
       wrapper2.ne(Affair::getStatus,0);
       return ResponseResult.build(ResponseCode.DATABASE_SELECT_OK,"success",affairService.list(wrapper2)) ;
    }

    @PutMapping("/yes")
    public ResponseResult updateAffair_yes(@RequestBody Affair affair){
        System.out.println(affair);
        return ResponseResult.build(ResponseCode.DATABASE_UPDATE_OK,"success",affairService.updateAffair_Yes(affair));
        //审批同意
    }

    @PutMapping("/no")
    public  ResponseResult updateAffair_No(@RequestBody Affair affair){
        return  ResponseResult.build(ResponseCode.DATABASE_UPDATE_OK,"success",affairService.updateAffair_No(affair));
        //审批驳回
    }


    @DeleteMapping("/{id}")
    public  ResponseResult deleteAffair_Apprioved(@PathVariable Long id){
        System.out.println("affair");
        return  ResponseResult.build(ResponseCode.DATABASE_DELETE_OK,"success",affairService.removeById(id));
        //删除已审批事务

    }


}
