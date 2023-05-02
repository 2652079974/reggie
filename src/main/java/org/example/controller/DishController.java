package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.example.common.Result;
import org.example.dto.DishDto;
import org.example.dto.SetmealDto;
import org.example.mapper.DishFlavorMapper;
import org.example.pojo.Category;
import org.example.pojo.Dish;
import org.example.pojo.DishFlavor;
import org.example.service.CategoryService;
import org.example.service.DishFlavorService;
import org.example.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Maplerain
 * @date 2023/4/17 1:53
 **/
@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {

    @Autowired
    DishService dishService;

    @Autowired
    DishFlavorService dishFlavorService;

    @Autowired
    CategoryService categoryService;

    /**
     * 添加菜品信息
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public Result save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        return Result.success();
    }

    /**
     * 分页请求菜品信息
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result page(int page, int pageSize, String name) {
        // 构造分页构造器对象
        Page<Dish> pageInfo = new Page<>(page, pageSize);

        // 条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件和排序条件
        queryWrapper.like(!StringUtils.isNullOrEmpty(name), Dish::getName, name)
                .orderByDesc(Dish::getUpdateTime);

        // 执行分页查询
        dishService.page(pageInfo, queryWrapper);

        pageInfo.setRecords(pageInfo.getRecords().stream().map(item->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            dishDto.setCategoryName(categoryService.getById(item.getCategoryId()).getName());
            return dishDto;
        }).collect(Collectors.toList()));

        return Result.success(pageInfo);
    }

    /**
     * 根据 id 查询相应菜品
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getDishById(@PathVariable Long id){
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return Result.success(dishDto);
    }

    /**
     * 更新菜品信息
     *
     * @param dishDto
     * @return
     */

    @PutMapping
    public Result update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return Result.success();
    }

    /**
     * 根据条件查询对应的菜品数据
     *
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public Result list(Dish dish){
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.orderByDesc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);

        list = list.stream().map(item->{
            DishDto dishDto = new DishDto();
            LambdaQueryWrapper<DishFlavor> flavorQuery = new LambdaQueryWrapper<>();
            BeanUtils.copyProperties(item,dishDto);
            flavorQuery.eq(DishFlavor::getDishId,item.getId());
            dishDto.setFlavors(dishFlavorService.list(flavorQuery));

            return dishDto;
        }).collect(Collectors.toList());
        return Result.success(list);
    }

}
