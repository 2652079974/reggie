package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Dish;
import org.springframework.stereotype.Component;

/**
 * @author Maplerain
 * @date 2023/4/16 14:51
 **/
@Mapper
@Component
public interface DishMapper extends BaseMapper<Dish> {
}
