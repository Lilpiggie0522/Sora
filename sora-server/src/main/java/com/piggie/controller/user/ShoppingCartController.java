package com.piggie.controller.user;

import com.piggie.dto.ShoppingCartDTO;
import com.piggie.result.Result;
import com.piggie.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: ShoppingCartController
 * Package: com.piggie.controller.user
 * Description:
 *
 * @Author Piggie
 * @Create 17/02/2024 2:33 pm
 * @Version 1.0
 */
@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "user shopping cart interface")
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    @ApiOperation("add item to cart")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("adding item to cart now {}", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }
}
