package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Category;
import org.springframework.stereotype.Component;

/**
 * @author Maplerain
 * @date 2023/4/16 12:35
 **/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
