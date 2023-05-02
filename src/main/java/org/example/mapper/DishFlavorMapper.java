package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.DishFlavor;
import org.springframework.stereotype.Component;

/**
 * @author Maplerain
 * @date 2023/4/17 1:39
 **/
@Mapper
@Component
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
}
