package com.cfive.pinnacle.controller.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.entity.permission.Element;
import com.cfive.pinnacle.service.permission.IElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 页面元素 前端控制器
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@RestController
@RequestMapping("/element")
public class ElementController {
    private IElementService elementService;

    @Autowired
    public void setElementService(IElementService elementService) {
        this.elementService = elementService;
    }

    @GetMapping
    public ResponseResult getAllElement() {
        List<Element> elements = elementService.list();

        return ResponseResult.databaseSelectSuccess(elements);
    }

    @GetMapping("/{id}")
    public ResponseResult getElement(@PathVariable long id) {
        LambdaQueryWrapper<Element> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Element::getId, id);
        Element element = elementService.getOne(wrapper);

        return ResponseResult.databaseSelectSuccess(element);
    }
}
