package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.ShoppingCartMapper;
import org.example.pojo.ShoppingCart;
import org.example.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @author Maplerain
 * @date 2023/4/21 16:56
 **/
@Service
public class ShoppingCartImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
