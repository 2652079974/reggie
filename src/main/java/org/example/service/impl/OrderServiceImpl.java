package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.common.BaseContext;
import org.example.common.CustomException;
import org.example.mapper.OrderMapper;
import org.example.pojo.*;
import org.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Maplerain
 * @date 2023/4/21 18:46
 **/
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

    private final UserService userService;

    private final AddressBookService addressBookService;

    private final ShoppingCartService shoppingCartService;

    private final OrderDetailService orderDetailService;

    public OrderServiceImpl(UserService userService, AddressBookService addressBookService,
                            ShoppingCartService shoppingCartService, OrderDetailService orderDetailService) {
        this.userService = userService;
        this.addressBookService = addressBookService;
        this.shoppingCartService = shoppingCartService;
        this.orderDetailService = orderDetailService;
    }

    /**
     * 用户提交订单
     *
     * @param orders
     */
    @Transactional(rollbackFor = CustomException.class)
    @Override
    public void submit(Orders orders) {
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(queryWrapper);

        if (shoppingCarts == null || shoppingCarts.size() == 0) {
            throw new CustomException("购物车为空，无法提交订单！");
        }

        User user = userService.getById(userId);
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if (addressBook == null) {
            throw new CustomException("用户信息有误，无法提交订单！");
        }

        long orderId = IdWorker.getId();
        log.info("orderId:{}",orderId);


        AtomicInteger amount = new AtomicInteger(0);

        List<OrderDetail> orderDetails = shoppingCarts.stream().map(item -> {
            OrderDetail orderDetail = new OrderDetail();

//            orderDetail.setId(item.getId());
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply((new BigDecimal(item.getNumber()))).intValue());
            return orderDetail;
        }).collect(Collectors.toList());

        orders.setNumber(String.valueOf(orderId));
        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));
        orders.setUserId(userId);
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress(s(addressBook.getProvinceName()) +
                s(addressBook.getCityName()) +
                s(addressBook.getDistrictName()) +
                s(addressBook.getDetail()));
        orderDetailService.saveBatch(orderDetails);

        // 向订单表插入一条数据
        this.save(orders);

        // 向订单明细表插入多条数据
        orderDetailService.updateBatchById(orderDetails);

        // 清空购物车数据
        shoppingCartService.remove(queryWrapper);
    }

    /**
     * 判断字符串是否为 null，如果为 null 则返回空字符串，减少冗余代码
     *
     * @param str
     * @return
     */
    private static String s(String str) {
        return str != null ? str : "";
    }
}
