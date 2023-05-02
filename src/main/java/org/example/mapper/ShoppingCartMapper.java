package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.ShoppingCart;
import org.springframework.stereotype.Component;

/**
 * @author Maplerain
 * @date 2023/4/21 16:54
 **/
@Mapper
@Component
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}
