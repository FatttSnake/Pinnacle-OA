package com.cfive.pinnacle.controller.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.entity.permission.Menu;
import com.cfive.pinnacle.service.permission.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 菜单 前端控制器
 * </p>
 *
 * @author FatttSnake
 * @since 2023-04-30
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    private IMenuService menuService;

    @Autowired
    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public ResponseResult getAllMenu() {
        List<Menu> menus = menuService.list();

        return ResponseResult.databaseSelectSuccess(menus);
    }

    @GetMapping("/{id}")
    public ResponseResult getMenu(@PathVariable int id) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getId, id);
        Menu menu = menuService.getOne(wrapper);

        return ResponseResult.databaseSelectSuccess(menu);
    }
}
