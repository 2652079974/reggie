package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.dto.SetmealDto;
import org.example.pojo.Setmeal;
import org.example.pojo.SetmealDish;

import java.util.List;

/**
 * @author Maplerain
 * @date 2023/4/16 20:03
 **/
public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时保存套餐和菜品的关联关系
     * @param setmealDto
     */
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时删除套餐与菜品的关联关系
     * @param ids
     */
    void deleteWithDish(List<Long> ids);
}
