package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.DishFlavorMapper;
import org.example.mapper.DishMapper;
import org.example.pojo.DishFlavor;
import org.example.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * @author Maplerain
 * @date 2023/4/17 1:50
 **/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
