package com.piggie.service;

import com.piggie.dto.OrdersSubmitDTO;
import com.piggie.vo.OrderSubmitVO;

/**
 * ClassName: OrderService
 * Package: com.piggie.service
 * Description:
 *
 * @Author Piggie
 * @Create 18/02/2024 1:37 am
 * @Version 1.0
 */
public interface OrderService {
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
}
