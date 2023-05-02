package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.common.Result;
import org.example.pojo.Orders;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Maplerain
 * @date 2023/4/21 18:51
 **/
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/submit")
    public Result submit(@RequestBody Orders orders){
        orderService.submit(orders);
        return Result.success("下单成功！");
    }
}
