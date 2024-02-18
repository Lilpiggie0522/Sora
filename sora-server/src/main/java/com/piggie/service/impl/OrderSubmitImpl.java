package com.piggie.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.piggie.constant.MessageConstant;
import com.piggie.context.BaseContext;
import com.piggie.dto.OrdersPageQueryDTO;
import com.piggie.dto.OrdersPaymentDTO;
import com.piggie.dto.OrdersSubmitDTO;
import com.piggie.entity.*;
import com.piggie.exception.AddressBookBusinessException;
import com.piggie.exception.OrderBusinessException;
import com.piggie.exception.ShoppingCartBusinessException;
import com.piggie.mapper.*;
import com.piggie.result.PageResult;
import com.piggie.service.OrderService;
import com.piggie.vo.OrderPaymentVO;
import com.piggie.vo.OrderSubmitVO;
import com.piggie.vo.OrderVO;
import com.piggie.vo.UserOrderHistoryVo;
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

    @Override
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) {
/*        Long userId = BaseContext.getCurrentId();
        User user = userMapper.getByUserId(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "ORDERPAID");
        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));*/
        OrderPaymentVO vo = new OrderPaymentVO();
        orderMapper.updateStatus(Orders.TO_BE_CONFIRMED, Orders.PAID, LocalDateTime.now(), ordersPaymentDTO.getOrderNumber());
        return vo;
    }

    @Override
    public PageResult pageQuery(int page, int pageSize, Integer status) {
        PageHelper.startPage(page, pageSize);
        OrdersPageQueryDTO ordersPageQueryDTO = new OrdersPageQueryDTO();
        ordersPageQueryDTO.setUserId(BaseContext.getCurrentId());
        ordersPageQueryDTO.setStatus(status);
        Page<Orders> result = orderMapper.orderPageQuery(ordersPageQueryDTO);
        ArrayList<OrderVO> vos = new ArrayList<>();
        if (result != null && result.size() > 0) {
            for (Orders orders : result) {
                List<OrderDetail> list = orderDetailMapper.getOrderDetailsByOrderId(orders.getId());
                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(orders, orderVO);
                orderVO.setOrderDetailList(list);
                vos.add(orderVO);
            }
        }
        return new PageResult(result.getTotal(), vos);
    }

    @Override
    public OrderVO getDetailByOrderId(Long orderId) {
        OrderVO orderVO = new OrderVO();
        Orders order = orderMapper.getOrderById(orderId);
        BeanUtils.copyProperties(order, orderVO);
        List<OrderDetail> list = orderDetailMapper.getOrderDetailsByOrderId(orderId);
        orderVO.setOrderDetailList(list);
        return orderVO;
    }

    @Override
    public void cancel(Long orderId) {
        Orders order = orderMapper.getOrderById(orderId);
        if (order == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        // paid and shop have taken order
        if (order.getStatus() > 2) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        // already paid
        if (order.getStatus().equals(Orders.TO_BE_CONFIRMED)) {
            order.setPayStatus(Orders.REFUND);
        }
        order.setStatus(Orders.CANCELLED);
        order.setCancelReason("user canceled");
        order.setCancelTime(LocalDateTime.now());
        orderMapper.update(order);
    }

    @Override
    public void repetition(Long orderId) {
        List<OrderDetail> list = orderDetailMapper.getOrderDetailsByOrderId(orderId);
        if (list != null && list.size() > 0) {
            for (OrderDetail od : list) {
                ShoppingCart shoppingCart = new ShoppingCart();
                BeanUtils.copyProperties(od, shoppingCart);
                shoppingCart.setUserId(BaseContext.getCurrentId());
                shoppingCart.setCreateTime(LocalDateTime.now());
                shoppingCartMapper.insert(shoppingCart);
            }
            return;
        }
        throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
    }
}
