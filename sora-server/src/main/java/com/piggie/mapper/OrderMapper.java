package com.piggie.mapper;

import com.github.pagehelper.Page;
import com.piggie.dto.OrdersPageQueryDTO;
import com.piggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

/**
 * ClassName: OrderMapper
 * Package: com.piggie.mapper
 * Description:
 *
 * @Author Piggie
 * @Create 18/02/2024 1:41 am
 * @Version 1.0
 */
@Mapper
public interface OrderMapper {

    void insert(Orders newOrder);

    @Update("update orders set status=#{orderStatus},pay_status=#{paymentStatus},checkout_time=#{checkoutTime} where number=#{orderId}")
    void updateStatus(Integer orderStatus, Integer paymentStatus, LocalDateTime checkoutTime, String orderId);
    Page<Orders> orderPageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select * from orders where id=#{orderId}")
    Orders getOrderById(Long orderId);

    @Update("update orders set status=#{status},pay_status=#{payStatus},cancel_reason=#{cancelReason},cancel_time=#{cancelTime} where id=#{id}")
    void update(Orders order);
}
