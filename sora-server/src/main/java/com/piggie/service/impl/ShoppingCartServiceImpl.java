package com.piggie.service.impl;

import com.piggie.context.BaseContext;
import com.piggie.dto.ShoppingCartDTO;
import com.piggie.entity.Dish;
import com.piggie.entity.Setmeal;
import com.piggie.entity.ShoppingCart;
import com.piggie.mapper.ComboMapper;
import com.piggie.mapper.DishMapper;
import com.piggie.mapper.ShoppingCartMapper;
import com.piggie.service.ShoppingCartService;
import com.piggie.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName: ShoppingCartServiceImpl
 * Package: com.piggie.service.impl
 * Description:
 *
 * @Author Piggie
 * @Create 17/02/2024 2:38 pm
 * @Version 1.0
 */
@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    ShoppingCartMapper shoppingCartMapper;

    @Autowired
    DishMapper dishMapper;

    @Autowired
    ComboMapper comboMapper;
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        // check if item already exists in cart, if in then +1 (update)
        if (list != null && list.size() > 0) {
            //  update
            ShoppingCart existedItem = list.get(0);
            existedItem.setNumber(existedItem.getNumber() + 1);
            shoppingCartMapper.updateNumberById(existedItem);
            return;
        }
        // if not, insert
        //  determine whether combo or just a dish
        Long dishId = shoppingCartDTO.getDishId();
        //  a dish
        if (dishId != null) {
            Dish dish = dishMapper.getDishById(dishId);
            shoppingCart.setName(dish.getName());
            shoppingCart.setImage(dish.getImage());
            shoppingCart.setAmount(dish.getPrice());
        } else {
            // a combo
            Long setmealId = shoppingCartDTO.getSetmealId();
            Setmeal combo = comboMapper.getComboById(setmealId);
            shoppingCart.setName(combo.getName());
            shoppingCart.setAmount(combo.getPrice());
            shoppingCart.setImage(combo.getImage());
        }
        shoppingCart.setNumber(1);
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCartMapper.insert(shoppingCart);
    }
}
