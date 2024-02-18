package com.piggie.service;

import com.piggie.dto.OrdersPageQueryDTO;
import com.piggie.dto.OrdersPaymentDTO;
import com.piggie.dto.OrdersSubmitDTO;
import com.piggie.result.PageResult;
import com.piggie.vo.OrderPaymentVO;
import com.piggie.vo.OrderSubmitVO;
import com.piggie.vo.OrderVO;

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

    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO);

    PageResult pageQuery(int page, int pageSize, Integer status);

    OrderVO getDetailByOrderId(Long orderId);

    void cancel(Long orderId);

    void repetition(Long orderId);
}
