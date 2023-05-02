package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.pojo.Orders;

/**
 * @author Maplerain
 * @date 2023/4/21 18:43
 **/
public interface OrderService extends IService<Orders> {
    /**
     * 用户提交订单
     *
     * @param orders
     */
    void submit(Orders orders);
}
