package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Orders;
import org.springframework.stereotype.Component;

/**
 * @author Maplerain
 * @date 2023/4/21 18:42
 **/
@Mapper
@Component
public interface OrderMapper extends BaseMapper<Orders> {
}
