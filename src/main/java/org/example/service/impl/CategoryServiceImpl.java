package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.common.CustomException;
import org.example.common.Result;
import org.example.mapper.CategoryMapper;
import org.example.pojo.Category;
import org.example.pojo.Dish;
import org.example.pojo.Setmeal;
import org.example.pojo.SetmealDish;
import org.example.service.CategoryService;
import org.example.service.DishService;
import org.example.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Maplerain
 * @date 2023/4/16 12:39
 **/
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;


    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();

        // 添加查询条件
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);

        // 查询，统计若总数超过 0 则代表该数据已被关联，无法删除
        long dishCount = dishService.count(dishLambdaQueryWrapper);
        long setmealCount = setmealService.count(setmealLambdaQueryWrapper);

        if (dishCount > 0) {
            throw new CustomException("当前分类下关联了菜品，不能删除！");
        } else if (setmealCount > 0) {
            throw new CustomException("当前分类下关联了套餐，不能删除！");
        } else {
            removeById(id);
        }
    }
}
