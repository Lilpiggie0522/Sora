package com.piggie.controller.user;

import com.piggie.dto.OrdersPageQueryDTO;
import com.piggie.dto.OrdersPaymentDTO;
import com.piggie.dto.OrdersSubmitDTO;
import com.piggie.result.PageResult;
import com.piggie.result.Result;
import com.piggie.service.OrderService;
import com.piggie.vo.OrderPaymentVO;
import com.piggie.vo.OrderSubmitVO;
import com.piggie.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: UserOrderController
 * Package: com.piggie.controller.user
 * Description:
 *
 * @Author Piggie
 * @Create 18/02/2024 1:33 am
 * @Version 1.0
 */
@RestController("userOrderController")
@RequestMapping("/user/order")
@Api(tags = "user order related interface")
@Slf4j
public class UserOrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/submit")
    @ApiOperation("user placing new order")

    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        log.info("submitting new order {}", ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    @PutMapping("/payment")
    @ApiOperation("start a payment")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) {
        log.info("payment order is {}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        return Result.success(orderPaymentVO);
    }

    @GetMapping("/historyOrders")
    @ApiOperation("page query for orders")
    public Result<PageResult> getOrderHistory(int page, int pageSize, Integer status) {
        log.info("page query for order histories");
        PageResult pageResult = orderService.pageQuery(page, pageSize, status);
        return Result.success(pageResult);
    }

    @GetMapping("orderDetail/{id}")
    @ApiOperation("find order detail by order id")
    public Result<OrderVO> getDetailByOrderId(@PathVariable(name = "id") Long orderId) {
        log.info("searching order detail for orderId: {}", orderId);
        OrderVO orderVO = orderService.getDetailByOrderId(orderId);
        return Result.success(orderVO);
    }

    @PutMapping("/cancel/{id}")
    @ApiOperation("cancel order")
    public Result cancel(@PathVariable(name = "id") Long orderId) {
        orderService.cancel(orderId);
        return Result.success();
    }

    @PostMapping("/repetition/{id}")
    @ApiOperation("get all items in this order and put em into shopping cart again")
    public Result repetition(@PathVariable(name = "id") Long orderId) {
        orderService.repetition(orderId);
        return Result.success();
    }
}
