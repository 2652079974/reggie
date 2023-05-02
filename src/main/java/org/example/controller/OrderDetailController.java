package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Maplerain
 * @date 2023/4/21 18:49
 **/
@Slf4j
@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {

    @Autowired
    OrderDetailService orderDetailService;

}
