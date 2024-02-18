package com.piggie.service.impl;

import com.piggie.constant.MessageConstant;
import com.piggie.context.BaseContext;
import com.piggie.dto.OrdersSubmitDTO;
import com.piggie.entity.*;
import com.piggie.exception.AddressBookBusinessException;
import com.piggie.exception.ShoppingCartBusinessException;
import com.piggie.mapper.*;
import com.piggie.service.OrderService;
import com.piggie.vo.OrderSubmitVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: OrderSubmitImpl
 * Package: com.piggie.service.impl
 * Description:
 *
 * @Author Piggie
 * @Create 18/02/2024 1:39 am
 * @Version 1.0
 */
@Service
public class OrderSubmitImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Autowired
    AddressBookMapper addressBookMapper;

    @Autowired
    ShoppingCartMapper shoppingCartMapper;

    @Autowired
    UserMapper userMapper;
    /**
     * user order placing method
     * @param ordersSubmitDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {
        // Exception handling
        // address is empty, cannot place order
        AddressBook address = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
        if (address == null) {
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }
        // shopping cart is empty, cannot place order
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        if (list == null || list.size() == 0) {
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }
        // insert one row into order table,
        Orders newOrder = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO, newOrder);
        newOrder.setOrderTime(LocalDateTime.now()); // done
        newOrder.setPayStatus(Orders.UN_PAID); // done
        newOrder.setStatus(Orders.PENDING_PAYMENT); // done
        newOrder.setNumber(String.valueOf(System.currentTimeMillis())); // done
        newOrder.setPhone(address.getPhone()); // done
        newOrder.setConsignee(address.getConsignee()); // done
        newOrder.setUserId(BaseContext.getCurrentId());// done
        User user = userMapper.getByUserId(BaseContext.getCurrentId());
        newOrder.setUserName(user.getName()); // done
        newOrder.setAddress(address.getProvinceName() + address.getCityName() + address.getDistrictName() + address.getDetail()); // done
        orderMapper.insert(newOrder);
        // insert N rows into order details table (since user can order many dishes, combos in an order, etc)
        List<OrderDetail> orderDetailList = new ArrayList<>();

        for (ShoppingCart cart : list) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart, orderDetail);
            orderDetail.setOrderId(newOrder.getId());
            orderDetailList.add(orderDetail);
        }
        orderDetailMapper.insertByBatch(orderDetailList);
        // empty users shopping cart
        shoppingCartMapper.emptyShoppingCart(BaseContext.getCurrentId());
        // encapsulate vo and return to front-end
        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .id(newOrder.getId())
                .orderTime(newOrder.getOrderTime())
                .orderNumber(newOrder.getNumber())
                .orderAmount(newOrder.getAmount()).build();
        return orderSubmitVO;
    }
}
