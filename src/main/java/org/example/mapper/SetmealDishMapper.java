package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.SetmealDish;
import org.springframework.stereotype.Component;

/**
 * @author Maplerain
 * @date 2023/4/20 2:19
 **/
@Mapper
@Component
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {
}
