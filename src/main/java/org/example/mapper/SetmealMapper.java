package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Setmeal;
import org.example.pojo.SetmealDish;
import org.springframework.stereotype.Component;

/**
 * @author Maplerain
 * @date 2023/4/16 15:04
 **/
@Mapper
@Component
public interface SetmealMapper extends BaseMapper<Setmeal> {
}
