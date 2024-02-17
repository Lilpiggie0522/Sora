package com.piggie.controller.user;

import com.piggie.dto.ShoppingCartDTO;
import com.piggie.entity.ShoppingCart;
import com.piggie.result.Result;
import com.piggie.service.ShoppingCartService;
import com.piggie.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list")
    @ApiOperation("display shopping cart")
    public Result<List<ShoppingCart>> list() {
        List<ShoppingCart> shoppingCart = shoppingCartService.showShoppingCart();
        return Result.success(shoppingCart);
    }

    @DeleteMapping("/clean")
    @ApiOperation("removes all items in shopping cart")
    public Result empty() {
        shoppingCartService.emptyShoppingCart();
        return Result.success();
    }

    @PostMapping("/sub")
    @ApiOperation("removes one particular item from shopping cart")
    public Result subItem(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        shoppingCartService.subItem(shoppingCartDTO);
        return Result.success();
    }
}
