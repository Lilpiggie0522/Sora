package com.piggie.mapper;

import com.piggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

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
}
