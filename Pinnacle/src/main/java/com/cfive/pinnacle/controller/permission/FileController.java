package com.cfive.pinnacle.controller.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.entity.permission.File;
import com.cfive.pinnacle.service.permission.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 文件 前端控制器
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@RestController
@RequestMapping("/file")
public class FileController {
    private IFileService fileService;

    @Autowired
    public void setFileService(IFileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public ResponseResult getAllFile() {
        List<File> files = fileService.list();

        return ResponseResult.databaseSelectSuccess(files);
    }

    @GetMapping("/{id}")
    public ResponseResult getFile(@PathVariable int id) {
        LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(File::getId, id);
        File file = fileService.getOne(wrapper);

        return ResponseResult.databaseSelectSuccess(file);
    }
}
