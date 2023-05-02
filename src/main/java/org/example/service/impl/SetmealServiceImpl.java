package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.common.CustomException;
import org.example.dto.SetmealDto;
import org.example.mapper.SetmealMapper;
import org.example.pojo.Setmeal;
import org.example.pojo.SetmealDish;
import org.example.service.SetmealDishService;
import org.example.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Maplerain
 * @date 2023/4/16 20:05
 **/
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐，同时保存套餐和菜品的关联关系
     * @param setmealDto
     */
    @Transactional
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        // 保存套餐的基本信息，操作 setmeal，执行 insert 操作
        this.save(setmealDto);
        System.out.println(setmealDto.getId());

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().forEach(item->{
            item.setSetmealId(setmealDto.getId());
        });

        // 保存套餐和菜品的关联信息，操作 setmeal_dish，执行 insert 操作
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除套餐，同时删除套餐与菜品的关联关系
     * @param ids
     */
    @Transactional
    @Override
    public void deleteWithDish(List<Long> ids) {
        // 查询 id 是否在表中有对应数据
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids).eq(Setmeal::getStatus,1);
        if (this.count(queryWrapper)>0) {
            throw new CustomException("无法删除启售中的套餐");
        }

        this.removeBatchByIds(ids);

        LambdaQueryWrapper<SetmealDish> dishQueryWrapper = new LambdaQueryWrapper<>();
        dishQueryWrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(dishQueryWrapper);
    }

}
