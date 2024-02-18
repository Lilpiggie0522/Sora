package com.piggie.controller.user;

import com.piggie.dto.OrdersPaymentDTO;
import com.piggie.dto.OrdersSubmitDTO;
import com.piggie.result.Result;
import com.piggie.service.OrderService;
import com.piggie.vo.OrderPaymentVO;
import com.piggie.vo.OrderSubmitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: OrderController
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
public class OrderController {
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
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) {

    }
}
