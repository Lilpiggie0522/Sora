package com.piggie.mapper;

import com.piggie.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: OrderDetailMapper
 * Package: com.piggie.mapper
 * Description:
 *
 * @Author Piggie
 * @Create 18/02/2024 2:39 am
 * @Version 1.0
 */
@Mapper
public interface OrderDetailMapper {

    void insertByBatch  (List<OrderDetail> orderDetailList);

    @Select("select * from order_detail where order_id=#{orderId}")
    List<OrderDetail> getOrderDetailsByOrderId(Long orderId);
}
