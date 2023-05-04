package com.cfive.pinnacle.service.permission.impl;

import com.cfive.pinnacle.entity.permission.File;
import com.cfive.pinnacle.mapper.permission.FileMapper;
import com.cfive.pinnacle.service.permission.IFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文件 服务实现类
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

}
