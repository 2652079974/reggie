package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.dto.DishDto;
import org.example.pojo.Dish;

/**
 * @author Maplerain
 * @date 2023/4/16 20:02
 **/
public interface DishService extends IService<Dish> {

    /**
     * 保存菜品的同时，保存口味列表
     *
     * @param dishDto
     */
    void saveWithFlavor(DishDto dishDto);

    /**
     * 根据菜品 id 查询菜品和口味信息
     *
     * @param id
     * @return
     */
    DishDto getByIdWithFlavor(Long id);

    /**
     * 修改菜品信息
     *
     * @param dishDto
     */
    void updateWithFlavor(DishDto dishDto);

}

