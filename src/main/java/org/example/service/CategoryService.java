package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.mapper.CategoryMapper;
import org.example.pojo.Category;
import org.springframework.stereotype.Service;

/**
 * @author Maplerain
 * @date 2023/4/16 12:37
 **/
public interface CategoryService extends IService<Category> {
    void remove(Long id);
}
