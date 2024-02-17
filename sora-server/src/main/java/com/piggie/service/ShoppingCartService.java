package com.piggie.service;

import com.piggie.dto.ShoppingCartDTO;
import com.piggie.entity.ShoppingCart;

import java.util.List;

/**
 * ClassName: ShoppingCartService
 * Package: com.piggie.service
 * Description:
 *
 * @Author Piggie
 * @Create 17/02/2024 2:36 pm
 * @Version 1.0
 */
public interface ShoppingCartService {
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> showShoppingCart();

    void emptyShoppingCart();

    void subItem(ShoppingCartDTO shoppingCartDTO);
}
