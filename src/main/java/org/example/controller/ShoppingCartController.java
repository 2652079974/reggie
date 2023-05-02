package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import lombok.extern.slf4j.Slf4j;
import org.example.common.BaseContext;
import org.example.common.Result;
import org.example.pojo.ShoppingCart;
import org.example.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author Maplerain
 * @date 2023/4/21 16:57
 **/
@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    /**
     * 添加商品至购物车
     *
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody ShoppingCart shoppingCart) {
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        // 查询当前加入购物车的是菜品还是套餐
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();

        if (dishId != null) {
            // 加入购物车的是菜品
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            // 加入购物车的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        // 查询当前菜品或者套餐是否存在购物车当中
        ShoppingCart cartServiceOne = shoppingCartService.getOne(queryWrapper);

        if (cartServiceOne != null) {
            // 如果已经存在则在原来的基础上加一
            cartServiceOne.setNumber(cartServiceOne.getNumber() + 1);
            shoppingCartService.updateById(cartServiceOne);
        } else {
            // 如果不存在则添加至购物车
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }

        return Result.success(cartServiceOne);
    }

    /**
     * 减少购物车中商品商品数量，当数量
     *
     * @param cart
     * @return
     */
    @PostMapping("/sub")
    public Result sub(@RequestBody ShoppingCart cart) {

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        if (cart.getDishId() != null) {
            queryWrapper.eq(ShoppingCart::getDishId, cart.getDishId());
        } else {
            queryWrapper.eq(ShoppingCart::getSetmealId, cart.getSetmealId());
        }

        ShoppingCart shoppingCartOne = shoppingCartService.getOne(queryWrapper);
        int count = shoppingCartOne.getNumber() - 1;
        shoppingCartOne.setNumber(count);
        shoppingCartService.updateById(shoppingCartOne);

        if (count <= 0) {
            // 当数量小于等于 0 时就将商品从该购物车中移除
            shoppingCartService.removeById(shoppingCartOne);
        }

        return Result.success(shoppingCartOne);
    }

    /**
     * 查询购物车列表
     * @return
     */
    @GetMapping("/list")
    public Result list() {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        return Result.success(shoppingCartService.list(queryWrapper));
    }

    /**
     * 清空购物车
     *
     * @return
     */
    @DeleteMapping("/clean")
    public Result clean(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        shoppingCartService.remove(queryWrapper);
        return Result.success("删除成功！");
    }
}
