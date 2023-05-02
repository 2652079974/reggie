package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.OrderDetailMapper;
import org.example.pojo.OrderDetail;
import org.example.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @author Maplerain
 * @date 2023/4/21 18:47
 **/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
