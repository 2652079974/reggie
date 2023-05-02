package org.example.dto;

import lombok.Data;
import org.example.pojo.OrderDetail;
import org.example.pojo.Orders;

import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
